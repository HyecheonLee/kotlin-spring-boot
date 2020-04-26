package com.hyecheon.kotlinspringboot.web.dto

import com.hyecheon.kotlinspringboot.domain.posts.Posts
import java.time.LocalDateTime

data class PostsSaveRequestDto(val title: String = "", val content: String = "", val author: String = "") {
    fun toEntity(): Posts {
        return Posts(title = title, content = content, author = author)
    }
}

data class PostsUpdateRequestDto(val title: String = "", val content: String = "")
data class PostsResponseDto(val id: Long, val title: String, val content: String, val author: String, val modifiedDate: LocalDateTime? = null)