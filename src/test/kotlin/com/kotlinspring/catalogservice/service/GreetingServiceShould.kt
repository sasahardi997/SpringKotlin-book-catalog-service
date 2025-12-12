package com.kotlinspring.catalogservice.service

import kotlin.test.Test
import kotlin.test.assertEquals

class GreetingServiceShould {

    private val greetingService = GreetingService().apply {
        message = "test profile"
    }

    @Test
    fun `return greeting message with name and environment`() {
        val message = greetingService.retrieveGreeting("Alex")

        assertEquals("Hello Alex from test profile", message)
    }
}