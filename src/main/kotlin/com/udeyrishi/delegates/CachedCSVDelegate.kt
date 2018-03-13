package com.udeyrishi.delegates

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class CachedCSVDelegate(private val filePath: String, private val index: Int) : ReadWriteProperty<Any, String> {
    private var cache: String? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return cache ?: run {
            val contents = readFile(filePath)
            cache = contents[index]
            contents[index]
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        val newContents = readFile(filePath).toMutableList().apply {
            set(index, value)
        }

        cache = value
        writeFile(filePath, newContents)
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

fun cachedCSV(filePath: String, index: Int): ReadWriteProperty<Any, String> = CachedCSVDelegate(filePath, index)