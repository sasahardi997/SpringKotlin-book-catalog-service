package com.kotlinspring.catalogservice.controller

import com.kotlinspring.catalogservice.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import io.github.oshai.kotlinlogging.KotlinLogging

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(val greetingService: GreetingService) {

    //Doesn't use @Slf4j, but both approaches are popular in Kotlin/SpringBoot projects
    companion object {
        private val log = KotlinLogging.logger {}
    }

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String {
        log.info {"Received name: $name"}
        return greetingService.retrieveGreeting(name)
    }
}