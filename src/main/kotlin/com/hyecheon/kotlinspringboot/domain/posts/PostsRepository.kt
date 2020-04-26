package com.hyecheon.kotlinspringboot.domain.posts

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostsRepository : JpaRepository<Posts, Long> {
    @Query("select p from Posts p order by p.id desc ")
    fun findAllDesc(): List<Posts>
}