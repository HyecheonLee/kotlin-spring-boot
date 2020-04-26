package com.hyecheon.kotlinspringboot.domain.user

import com.hyecheon.kotlinspringboot.domain.BaseTimeEntity
import java.io.Serializable
import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,
        @Column(nullable = false)
        val name: String,
        @Column(nullable = false)
        val email: String,
        val picture: String,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        val role: Role
) : BaseTimeEntity() {
    inline fun getKey() = role.key
}