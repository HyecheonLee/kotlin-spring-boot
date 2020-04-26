package com.hyecheon.kotlinspringboot.config.auth.dto

import java.io.Serializable

data class SessionUser(
        val name: String,
        val email: String,
        val picture: String
) : Serializable