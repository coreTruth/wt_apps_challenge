package com.example.myapplication.data.repository

import com.example.myapplication.data.network.BookApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val api: BookApi
) {
    fun fetchBookList() =
        api.fetchBookList()
}