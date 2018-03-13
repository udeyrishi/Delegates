package com.udeyrishi.delegates

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Please specify the CSV path as command line args")
    }

    val user = User(args[0])
    println(user)

    user.setFirstName("Erlich")
    user.setLastName("Bachman")
    println(user)

    user.setFirstName("Udey")
    user.setLastName("Rishi")
    println(user)
}