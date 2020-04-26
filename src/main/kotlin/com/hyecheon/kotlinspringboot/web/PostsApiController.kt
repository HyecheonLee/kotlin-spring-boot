package com.hyecheon.kotlinspringboot.web

import com.hyecheon.kotlinspringboot.domain.posts.Posts
import com.hyecheon.kotlinspringboot.service.PostsService
import com.hyecheon.kotlinspringboot.web.dto.PostsResponseDto
import com.hyecheon.kotlinspringboot.web.dto.PostsSaveRequestDto
import com.hyecheon.kotlinspringboot.web.dto.PostsUpdateRequestDto
import org.springframework.web.bind.annotation.*

@RestController
class PostsApiController(val postsService: PostsService) {

    @PostMapping("/api/v1/posts")
    fun save(@RequestBody postsSaveRequestDto: PostsSaveRequestDto): Posts {
        return postsService.save(postsSaveRequestDto)
    }

    @PutMapping("/api/v1/posts/{id}")
    fun update(@PathVariable id: Long, @RequestBody postsUpdateRequestDto: PostsUpdateRequestDto): Long {
        return postsService.update(id, postsUpdateRequestDto)
    }

    @GetMapping("/api/v1/posts/{id}")
    fun findById(@PathVariable id: Long): PostsResponseDto {
        return postsService.findById(id)
    }

    @DeleteMapping("/api/v1/posts/{id}")
    fun delete(@PathVariable id: Long): Long {
        postsService.delete(id)
        return id
    }
}