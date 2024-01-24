package net.ddns.raywel.helper

import kotlin.random.Random

object GenerateBearerToken {
    fun getToken(): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..24)
            .map { Random.nextInt(characters.length) }
            .map(characters::get)
            .joinToString("")
    }
}
