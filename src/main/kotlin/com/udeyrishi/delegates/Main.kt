package com.udeyrishi.delegates

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Please specify the CSV path as command line args")
    }

    val user = User(args[0], firstName = "Bertram", lastName = "Gilfoyle")
    println(user)

    user.firstName = "Erlich"
    user.lastName = "Bachman"
    println(user)

    user.firstName = "Udey"
    user.lastName = "Rishi"
    println(user)

    val firstName = user[0]
    val lastName = user[1]
    println("Indexed firstName: $firstName | Indexed lastName: $lastName")

    try {
        user.firstName = "        \t   "
    } catch (e: IllegalArgumentException) {
        println("${e.message}")
    }

    println(user)
}