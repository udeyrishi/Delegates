package com.udeyrishi.delegates

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class User(private val filePath: String) {
    private var cachedFirstName: String? = null
    private var cachedLastName: String? = null

    fun getFirstName(): String {
        return cachedFirstName ?: run {
            val contents = readFile(filePath)
            cachedFirstName = contents[0]
            contents[0]
        }
    }

    fun setFirstName(value: String) {
        val newContents = readFile(filePath).toMutableList().apply {
            set(0, value)
        }

        cachedFirstName = value
        writeFile(filePath, newContents)
    }

    fun getLastName(): String {
        return cachedLastName ?: run {
            val contents = readFile(filePath)
            cachedLastName = contents[1]
            contents[1]
        }
    }

    fun setLastName(value: String) {
        val newContents = readFile(filePath).toMutableList().apply {
            set(1, value)
        }
        cachedLastName = value
        writeFile(filePath, newContents)
    }

    override fun toString(): String {
        return "First Name: ${getFirstName()} | Last Name: ${getLastName()}"
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