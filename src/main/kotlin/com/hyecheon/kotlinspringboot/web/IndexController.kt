package com.hyecheon.kotlinspringboot.web

import com.hyecheon.kotlinspringboot.config.auth.dto.SessionUser
import com.hyecheon.kotlinspringboot.service.PostsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpSession

@Controller
class IndexController(val postsService: PostsService, val httpSession: HttpSession) {
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("posts", postsService.findAllDesc())
        val user = httpSession.getAttribute("user")
        if (user is SessionUser) {
            model.addAttribute("userName", user.name)
        }
        return "index";
    }

    @GetMapping("/posts/save")
    fun postsSave(): String {
        return "posts-save"
    }

    @GetMapping("/posts/update/{id}")
    fun postsUpdate(@PathVariable id: Long, model: Model): String {
        val dto = postsService.findById(id)
        model.addAttribute("post", dto)
        return "posts-update"
    }
}
