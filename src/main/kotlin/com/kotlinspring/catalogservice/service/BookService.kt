package com.kotlinspring.catalogservice.service

import com.kotlinspring.catalogservice.domain.Book
import com.kotlinspring.catalogservice.domain.dto.BookDTO
import com.kotlinspring.catalogservice.exception.BookNotFoundException
import com.kotlinspring.catalogservice.repository.BookRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    fun addBook(bookDTO: BookDTO): BookDTO {
        //Scope function (or use mapper)
        val book = bookDTO.let {
            Book(null, it.name, it.genre)
        }

        val savedBook = bookRepository.save(book)

        log.info { "Saved book: ${savedBook.name}" }

        return savedBook.let {
            BookDTO(it.id, it.name, it.genre)
        }
    }

    fun getAllBooks(): List<BookDTO> {
        return bookRepository.findAll().map {
            BookDTO(it.id, it.name, it.genre)
        }
    }

    fun findByBookName(bookName: String): List<BookDTO> {
        return bookRepository.findBookByName(bookName).map {
            BookDTO(it.id, it.name, it.genre)
        }
    }

    fun updateBook(bookId: Long, bookDTO: BookDTO): BookDTO {
        val existingBookOpt = bookRepository.findById(bookId)

        return if(existingBookOpt.isPresent) {
            existingBookOpt.get().let {
                it.name = bookDTO.name
                it.genre = bookDTO.genre

                bookRepository.save(it)

                BookDTO(it.id, it.name, it.genre)
            }
        } else {
            throw BookNotFoundException("Book with id ${bookDTO.id} not found.")
        }
    }

    fun deleteBook(bookId: Long) {
        val existingBookOpt = bookRepository.findById(bookId)

        if(existingBookOpt.isPresent) {
                bookRepository.deleteById(bookId)
        } else {
            throw BookNotFoundException("Book with id $bookId not found.")
        }
    }
}