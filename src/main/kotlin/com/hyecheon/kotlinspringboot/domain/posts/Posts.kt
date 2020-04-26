package com.hyecheon.kotlinspringboot.domain.posts

import com.hyecheon.kotlinspringboot.domain.BaseTimeEntity
import javax.persistence.*

@Entity
data class Posts(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,
        @Column(length = 500, nullable = false)
        val title: String,
        @Column(columnDefinition = "TEXT", nullable = false)
        val content: String = "",
        val author: String = ""
) : BaseTimeEntity()