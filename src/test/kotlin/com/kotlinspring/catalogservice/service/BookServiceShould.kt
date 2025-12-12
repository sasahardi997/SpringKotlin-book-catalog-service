package com.kotlinspring.catalogservice.service

import com.kotlinspring.catalogservice.domain.Author
import com.kotlinspring.catalogservice.domain.Book
import com.kotlinspring.catalogservice.domain.dto.BookDTO
import com.kotlinspring.catalogservice.exception.AuthorNotFoundException
import com.kotlinspring.catalogservice.repository.BookRepository
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.assertThrows

class BookServiceShould {

    private val bookRepository: BookRepository = mock()
    private val authorService: AuthorService = mock()

    private val bookService = BookService(bookRepository, authorService)

    @Test
    fun `addBook should save book when author exists`() {
        // given
        val authorId = 1L
        val bookDTO = BookDTO(null, "Test Book", "Fiction", authorId)

        val author = mock<Author> {
            on { id } doReturn authorId
        }

        whenever(authorService.findByAuthorId(authorId))
            .thenReturn(Optional.of(author))

        val savedBook = Book(10L, "Test Book", "Fiction", author)
        whenever(bookRepository.save(any()))
            .thenReturn(savedBook)

        // when
        val result = bookService.addBook(bookDTO)

        // then
        assertEquals(savedBook.id, result.id)
        assertEquals(savedBook.name, result.name)
        assertEquals(savedBook.genre, result.genre)
        assertEquals(authorId, result.authorId)

        verify(authorService).findByAuthorId(authorId)
        verify(bookRepository).save(any())
    }

    @Test
    fun `addBook should throw exception when authorId is null`() {
        val bookDTO = BookDTO(null, "Book", "Fiction", null)

        val exception = assertThrows<AuthorNotFoundException> {
            bookService.addBook(bookDTO)
        }

        assertEquals("Author id not provided", exception.message)
        verifyNoInteractions(authorService)
        verifyNoInteractions(bookRepository)
    }

    @Test
    fun `addBook should throw exception when author does not exist`() {
        val authorId = 999L
        val bookDTO = BookDTO(null, "Book", "Fiction", authorId)

        whenever(authorService.findByAuthorId(authorId))
            .thenReturn(Optional.empty())

        val exception = assertThrows<AuthorNotFoundException> {
            bookService.addBook(bookDTO)
        }

        assertEquals("Author with id $authorId not found", exception.message)
        verify(authorService).findByAuthorId(authorId)
        verifyNoMoreInteractions(bookRepository)
    }
}