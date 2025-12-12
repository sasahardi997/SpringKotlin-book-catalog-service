package com.kotlinspring.catalogservice.controller

import com.kotlinspring.catalogservice.domain.dto.AuthorDTO
import com.kotlinspring.catalogservice.service.AuthorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/authors")
@Validated
class AuthorController(val authorService: AuthorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAuthor(@Valid @RequestBody authorDTO: AuthorDTO) = authorService.addAuthor(authorDTO)
}