package com.hyecheon.kotlinspringboot.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyecheon.kotlinspringboot.domain.posts.Posts
import com.hyecheon.kotlinspringboot.domain.posts.PostsRepository
import com.hyecheon.kotlinspringboot.web.dto.PostsSaveRequestDto
import com.hyecheon.kotlinspringboot.web.dto.PostsUpdateRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PostsApiControllerTest {

    @LocalServerPort
    lateinit var port: String

    @Autowired
    lateinit var postsRepository: PostsRepository

    @Autowired
    lateinit var context: WebApplicationContext
    lateinit var mvc: MockMvc

    @BeforeEach
    internal fun setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply<DefaultMockMvcBuilder>(springSecurity()).build()
    }

    @AfterEach
    internal fun tearDown() {
        postsRepository.deleteAll()
    }

    @Test
    @WithMockUser(roles = ["USER"])
    internal fun `Posts 등록된다`() {
        val title = "title"
        val content = "content"
        val requestDto = PostsSaveRequestDto(title = title, content = content, author = "author")

        val url = "http://localhost:${port}/api/v1/posts"

        //when
        mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk)

        val all = postsRepository.findAll()
        assertThat(all[0].title).isEqualTo(title)
        assertThat(all[0].content).isEqualTo(content)
    }

    @Test
    @WithMockUser(roles = ["USER"])
    internal fun `Posts 수정된다`() {
        val savedPosts = postsRepository.save(Posts(title = "title", content = "content", author = "author"))
        val updateId = savedPosts.id
        val expectedTitle = "title2"
        val expectedContent = "content2"

        val requestDto = PostsUpdateRequestDto(title = expectedTitle, content = expectedContent)
        val url = "http://localhost:${port}/api/v1/posts/${updateId}"

        //when
        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk)

        //then
        val all = postsRepository.findAll()
        assertThat(all[0].title).isEqualTo(expectedTitle)
        assertThat(all[0].content).isEqualTo(expectedContent)
    }
}