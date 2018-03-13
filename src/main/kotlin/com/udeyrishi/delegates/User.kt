package com.udeyrishi.delegates

class User(filePath: String) {
    var firstName: String by CachedCSVDelegate(filePath, 0)
    var lastName: String by CachedCSVDelegate(filePath, 1)

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