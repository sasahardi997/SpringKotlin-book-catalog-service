package com.kotlinspring.catalogservice.exception

import java.lang.RuntimeException

class BookNotFoundException(message: String): RuntimeException(message) {}