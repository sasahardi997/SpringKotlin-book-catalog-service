package com.kotlinspring.catalogservice.domain.dto

import jakarta.validation.constraints.NotBlank

data class AuthorDTO(
    val id: Long?,

    @field:NotBlank("Author cannot be blank")
    val name: String,
)