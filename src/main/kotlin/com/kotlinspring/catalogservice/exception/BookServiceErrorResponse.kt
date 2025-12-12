package com.kotlinspring.catalogservice.exception

import java.time.LocalDateTime

data class BookServiceErrorResponse(val errorCode: Int,
                                    val message: String,
                                    val timestamp: LocalDateTime = LocalDateTime.now()
)