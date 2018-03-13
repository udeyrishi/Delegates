/**
 Copyright (c) 2018 Udey Rishi. All rights reserved.
*/

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
        if (initialValue != null) {
            uncheckedSetValue(initialValue)
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

private class CachedCSVDelegateProvider(private val filePath: String, private val index: Int, private val initialValue: String? = null) : ReadWriteDelegateProvider<Any, String> {
    override fun provideDelegate(thisRef: Any, property: KProperty<*>): CachedCSVDelegate {
        if (initialValue != null) {
            checkNotBlank(thisRef, property, initialValue)
        }
        return CachedCSVDelegate(filePath, index, initialValue)
    }
}

fun cachedCSV(filePath: String, index: Int, initialValue: String? = null): ReadWriteDelegateProvider<Any, String> = CachedCSVDelegateProvider(filePath, index, initialValue)