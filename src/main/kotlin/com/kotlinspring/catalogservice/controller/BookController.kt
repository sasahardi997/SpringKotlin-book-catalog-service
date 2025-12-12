package com.kotlinspring.catalogservice.controller

import com.kotlinspring.catalogservice.domain.dto.BookDTO
import com.kotlinspring.catalogservice.service.BookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/books")
class BookController(val bookService: BookService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@Valid @RequestBody bookDTO: BookDTO): BookDTO {
        return bookService.addBook(bookDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllBooks(@RequestParam(value = "bookName", required = false) bookName: String?): List<BookDTO>  {
        if(bookName == null) {
            return bookService.getAllBooks()
        }
        return bookService.findByBookName(bookName);
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBook(@PathVariable("bookId") bookId: Long,
                        @Valid @RequestBody bookDTO: BookDTO): BookDTO {
        return bookService.updateBook(bookId, bookDTO)
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable("bookId") bookId: Long) {
        bookService.deleteBook(bookId)
    }
}