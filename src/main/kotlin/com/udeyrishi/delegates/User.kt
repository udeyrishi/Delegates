/**
 Copyright (c) 2018 Udey Rishi. All rights reserved.
*/

package com.udeyrishi.delegates

class User(filePath: String, firstName: String? = null, lastName: String? = null) {
    var firstName: String by cachedCSV(filePath, 0, initialValue = firstName)
    var lastName: String by cachedCSV(filePath, 1, initialValue = lastName)

    override fun toString(): String {
        return "First Name: $firstName | Last Name: $lastName"
    }

    operator fun get(index: Int): String {
        return when (index) {
            0 -> firstName
            1 -> lastName
            else -> throw IndexOutOfBoundsException("index must be in the range [0, 1]")
        }
    }
}