package com.kotlinspring.catalogservice.service

import com.kotlinspring.catalogservice.domain.Author
import com.kotlinspring.catalogservice.domain.dto.AuthorDTO
import com.kotlinspring.catalogservice.repository.AuthorRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AuthorService(val authorRepository: AuthorRepository) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    fun addAuthor(authorDTO: AuthorDTO): AuthorDTO {
        val author = authorDTO.let {
            Author(it.id, it.name)
        }

        val authorSaved = authorRepository.save(author)

        return authorSaved.let {
            AuthorDTO(it.id, it.name)
        }
    }

    fun findByAuthorId(id: Long): Optional<Author> = authorRepository.findById(id)

}