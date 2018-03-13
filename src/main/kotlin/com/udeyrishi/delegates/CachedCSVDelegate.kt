package com.udeyrishi.delegates

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class CachedCSVDelegate(private val filePath: String, private val index: Int, initialValue: String? = null) : ReadWriteProperty<Any, String> {
    private var cache: String? = null

    init {
        // How to get the thisRef and property?! Need to pass from the caller. Ew
        if (initialValue != null) {
            setValue(_, _, initialValue)
        }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return cache ?: run {
            val contents = readFile(filePath)
            cache = contents[index]
            contents[index]
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        checkNotBlank(thisRef, property, value)
        uncheckedSetValue(value)
    }

    private fun uncheckedSetValue(value: String) {
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

private fun checkNotBlank(thisRef: Any, property: KProperty<*>, value: String) {
    if (value.isBlank()) {
        throw IllegalArgumentException("Property '${property.name}' in class '${thisRef.javaClass.name}' cannot be set to whitespace.")
    }
}

fun cachedCSV(filePath: String, index: Int, initialValue: String? = null): ReadWriteProperty<Any, String> = CachedCSVDelegate(filePath, index, initialValue)