package com.udeyrishi.delegates

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class User(private val filePath: String) {
    private var _firstName: String? = null
    var firstName: String
        get() {
            return _firstName ?: run {
                val contents = readFile(filePath)
                _firstName = contents[0]
                contents[0]
            }
        }
        set(value) {
            val newContents = readFile(filePath).toMutableList().apply {
                set(0, value)
            }

            _firstName = value
            writeFile(filePath, newContents)
        }

    private var _lastName: String? = null
    var lastName: String
        get() {
            return _lastName ?: run {
                val contents = readFile(filePath)
                _lastName = contents[1]
                contents[1]
            }
        }
        set(value) {
            val newContents = readFile(filePath).toMutableList().apply {
                set(1, value)
            }
            _lastName = value
            writeFile(filePath, newContents)
        }

    override fun toString(): String {
        return "First Name: $firstName | Last Name: $lastName"
    }

    companion object {
        private fun readFile(filePath: String): List<String> {
            return BufferedReader(FileReader(filePath)).use {
                it.readLine()
                        .split(",")
                        .map {
                            it.trim()
                        }
                        .filter {
                            it.isNotEmpty()
                        }
            }
        }

        private fun writeFile(filePath: String, newContents: List<String>) {
            BufferedWriter(FileWriter(filePath)).use {
                it.write(newContents.joinToString(separator = ","))
            }
        }
    }
}