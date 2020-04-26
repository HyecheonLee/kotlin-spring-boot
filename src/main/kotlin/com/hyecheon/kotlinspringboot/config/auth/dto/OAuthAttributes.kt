package com.hyecheon.kotlinspringboot.config.auth.dto

import com.hyecheon.kotlinspringboot.domain.user.Role
import com.hyecheon.kotlinspringboot.domain.user.User

class OAuthAttributes(
        val attributes: Map<String, Any>,
        val nameAttributeKey: String,
        val name: String,
        val email: String,
        val picture: String) {

    fun toEntity(): User {
        return User(name = name, email = email, picture = picture, role = Role.GUEST)
    }

    companion object {
        fun of(registrationId: String,
               userNameAttribute: String,
               attributes: Map<String, Any>
        ): OAuthAttributes {
            return if (registrationId == "naver") {
                ofNaver(userNameAttribute, attributes)
            } else {
                ofGoogle(userNameAttribute, attributes)
            }
        }

        private fun ofGoogle(userNameAttribute: String, attributes: Map<String, Any>): OAuthAttributes {
            return OAuthAttributes(
                    name = attributes["name"].toString(),
                    email = attributes["email"].toString(),
                    picture = attributes["picture"].toString(),
                    attributes = attributes,
                    nameAttributeKey = userNameAttribute
            )
        }

        private fun ofNaver(userNameAttribute: String, attributes: Map<String, Any>): OAuthAttributes {
            val response = attributes["response"] as Map<String, Any>
            return OAuthAttributes(
                    name = response["name"].toString(),
                    email = response["email"].toString(),
                    picture = response["profile_image"].toString(),
                    attributes = attributes,
                    nameAttributeKey = userNameAttribute
            )
        }
    }
}