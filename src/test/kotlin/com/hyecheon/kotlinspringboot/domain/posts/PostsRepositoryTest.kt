package com.hyecheon.kotlinspringboot.domain.posts

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
internal class PostsRepositoryTest {
    @Autowired
    lateinit var postsRepository: PostsRepository

    @AfterEach
    internal fun cleanup() {
        postsRepository.deleteAll()
    }

    @Test
    internal fun `게시글저장 불러오기`() {
        val title = "테스트 게시글"
        val content = "테스트 본문"

        postsRepository.save(Posts(title = title, content = content, author = "hclee@gmail.com"))

        //when
        val postsList = postsRepository.findAll()

        //then
        val posts = postsList[0]
        assertThat(posts.title).isEqualTo(title)
        assertThat(posts.content).isEqualTo(content)

    }

    @Test
    internal fun `BaseTimeEntity 등록`() {
        val now = LocalDateTime.of(2019, 6, 4, 0, 0, 0)
        postsRepository.save(Posts(title = "title", content = "content", author = "author"))

        val postsList = postsRepository.findAll()

        val posts = postsList[0]

        println(">>>>> createDate=${posts.createdDate} modifiedDate=${posts.modifiedDate}")

        assertThat(posts.createdDate).isAfter(now)
        assertThat(posts.modifiedDate).isAfter(now)

    }
}