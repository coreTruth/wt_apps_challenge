package com.example.myapplication.data.network

import com.example.myapplication.data.entity.Book
import com.example.myapplication.utils.getString
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookApi @Inject constructor() {
    fun fetchBookList(): NetworkResource<List<Book>> {
        val url: URL? = try {
            URL("http://de-coding-test.s3.amazonaws.com/books.json")
        } catch (e: MalformedURLException) {
            return NetworkResource.Failure
        }

        val list = url?.getString()?.let {
            try {
                Gson().fromJson(it, Array<Book>::class.java).toList()
            } catch (e: JsonSyntaxException) {
                emptyList()
            }
        } ?: emptyList()

        return NetworkResource.Success(list)
    }
}