package com.kotlinspring.catalogservice.exception

import java.lang.RuntimeException

class AuthorNotFoundException (message: String): RuntimeException(message)