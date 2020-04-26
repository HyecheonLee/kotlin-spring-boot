package com.hyecheon.kotlinspringboot.web

import com.hyecheon.kotlinspringboot.config.auth.SecurityConfig
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [HelloController::class], excludeFilters = [
    ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [SecurityConfig::class])
])
internal class HelloControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    @WithMockUser(roles = ["USER"])
    internal fun `hello가 리턴된다`() {
        val hello = "hello"
        mvc.perform(get("/hello"))
                .andExpect(status().isOk)
                .andExpect(content().string(hello))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    internal fun `helloDto가 리턴된다`() {
        val name = "hello"
        val amount = 1000
        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", amount.toString()))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", `is`(name)))
                .andExpect(jsonPath("$.amount", `is`(amount)))

    }
}