package com.kotlinspring.catalogservice.repository

import com.kotlinspring.catalogservice.SpringContext
import com.kotlinspring.catalogservice.domain.Book
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Stream
import kotlin.test.assertEquals

class BookRepositoryCT: SpringContext() {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setUp() {
        bookRepository.deleteAll()

        val book1984 = Book(null, name = "1984", genre = "Novel")
        val bookIkigai = Book(null, name = "Ikigai", genre = "Popular psychology")
        val bookIkigai2ndEdition = Book(null, name = "Ikigai 2nd Edition", genre = "Poplar psychology")
        bookRepository.saveAll(listOf(book1984, bookIkigai, bookIkigai2ndEdition))
    }

    @ParameterizedTest
    @MethodSource("bookAndSize")
    fun nativeQueryShouldReturnListOfTwo(bookName: String, expectedSize: Int) {
        val list = bookRepository.findBookByName(bookName)

        assertEquals(expectedSize, list.size)
    }

    @ParameterizedTest
    @MethodSource("bookAndSize")
    fun shouldReturnListOfTwo(bookName: String, expectedSize: Int) {
        val list = bookRepository.findByNameContaining(bookName)

        assertEquals(expectedSize, list.size)
    }

    companion object {

        @JvmStatic
        fun bookAndSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("Ikigai", 2),
                Arguments.arguments("1984", 1)
            )
        }
    }
}