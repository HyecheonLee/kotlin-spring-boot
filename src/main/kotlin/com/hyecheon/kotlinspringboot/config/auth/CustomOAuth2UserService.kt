package com.hyecheon.kotlinspringboot.config.auth

import com.hyecheon.kotlinspringboot.config.auth.dto.OAuthAttributes
import com.hyecheon.kotlinspringboot.config.auth.dto.SessionUser
import com.hyecheon.kotlinspringboot.domain.user.User
import com.hyecheon.kotlinspringboot.domain.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class CustomOAuth2UserService(private val userRepository: UserRepository,
                              private val httpSession: HttpSession) :
        OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId

        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        val attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.attributes)
        val user = saveOrUpdate(attributes)
        val sessionUser = SessionUser(user.name, user.email, user.picture)

        httpSession.setAttribute("user", sessionUser)

        return DefaultOAuth2User(
                Collections.singleton(SimpleGrantedAuthority(user.getKey())),
                attributes.attributes,
                attributes.nameAttributeKey)
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): User {
        val user = userRepository.findByEmail(attributes.email)
                .map { entity ->
                    entity.copy(name = attributes.name, picture = attributes.picture)
                }.orElse(attributes.toEntity())
        return userRepository.save(user)
    }
}