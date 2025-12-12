package com.kotlinspring.catalogservice.controller

import com.kotlinspring.catalogservice.SpringContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import kotlin.test.assertEquals

class GreetingControllerIT: SpringContext() {

    private lateinit var webTestClient: WebTestClient

    private val GREETING_URL = "/v1/greetings"

    @BeforeEach
    fun setUp(context: WebApplicationContext) {
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun `return greeting with name and environment`() {
        val result = webTestClient.get()
            .uri("$GREETING_URL/Alex")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()

        assertEquals("Hello Alex from test profile", result.responseBody)
    }


}