package com.hyecheon.kotlinspringboot.config.auth

import com.hyecheon.kotlinspringboot.domain.user.Role
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfig(private val customOAuth2UserService: CustomOAuth2UserService) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name)
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
    }
}
//nohup java -jar /home/ec2-user/app/step1/kotlin-spring-boot -Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-real-db.properties -Dspring.profiles.active=real 2>&1 &
