package com.hyecheon.kotlinspringboot.service

import com.hyecheon.kotlinspringboot.domain.posts.Posts
import com.hyecheon.kotlinspringboot.domain.posts.PostsRepository
import com.hyecheon.kotlinspringboot.web.dto.PostsResponseDto
import com.hyecheon.kotlinspringboot.web.dto.PostsSaveRequestDto
import com.hyecheon.kotlinspringboot.web.dto.PostsUpdateRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostsService(val postsRepository: PostsRepository) {

    @Transactional
    fun save(postsSaveRequestDto: PostsSaveRequestDto): Posts {
        return postsRepository.save(postsSaveRequestDto.toEntity())
    }

    @Transactional
    fun update(id: Long, postsUpdateRequestDto: PostsUpdateRequestDto): Long {
        var posts = postsRepository.findById(id).orElseThrow { IllegalArgumentException("해당 사용자가 없습니다. id=${id}") }
        posts = posts.copy(title = postsUpdateRequestDto.title, content = postsUpdateRequestDto.content)
        postsRepository.save(posts)
        return id
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): PostsResponseDto {
        val entity = postsRepository.findById(id).orElseThrow { IllegalArgumentException("해당 사용자가 없습니다. id=${id}") }
        return PostsResponseDto(id = entity.id, author = entity.author, content = entity.content, title = entity.title)
    }

    @Transactional(readOnly = true)
    fun findAllDesc(): List<PostsResponseDto> {
        return postsRepository
                .findAllDesc()
                .map { posts -> PostsResponseDto(posts.id, posts.title, posts.content, posts.author, modifiedDate = posts.modifiedDate) }
    }

    @Transactional
    fun delete(id: Long) {
        val posts = postsRepository.findById(id).orElseThrow { IllegalArgumentException("해당 게시글이 없습니다. id=${id}") }
        postsRepository.delete(posts)
    }

}