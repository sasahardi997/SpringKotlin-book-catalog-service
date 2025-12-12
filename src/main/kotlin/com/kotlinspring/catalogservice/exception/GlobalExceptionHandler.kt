package com.kotlinspring.catalogservice.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {

        log.error(ex) { "MethodArgumentNotValidException caught ${ex.message}" }

        val errors = ex.bindingResult
            .fieldErrors
            .associate { fieldError ->
                fieldError.field to (fieldError.defaultMessage ?: "Invalid value")
            }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(BookNotFoundException::class)
    fun handleBookNotFoundException(ex: BookNotFoundException): ResponseEntity<BookServiceErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(BookServiceErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message!!))
    }
}