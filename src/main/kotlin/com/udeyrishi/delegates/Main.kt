package com.udeyrishi.delegates

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Please specify the CSV path as command line args")
    }

    val user = User(args[0])
    println(user)

    user.firstName = "Erlich"
    user.lastName = "Backman"
    println(user)

    user.firstName = "Udey"
    user.lastName = "Rishi"
    println(user)
}