package com.example.myapplication.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

fun URL.getString(): String? {

    return try {
        val stream = openStream()
        val r = BufferedReader(InputStreamReader(stream))
        val result = StringBuilder()
        var line: String?
        while (r.readLine().also { line = it } != null) {
            result.append(line).appendln()
        }
        result.toString()
    }catch (e: IOException){
        e.toString()
    }
}