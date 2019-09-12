package ru.hse.anstkras.githubsearcher

import com.eclipsesource.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import java.util.stream.Collectors.toList

private const val linksLimit = 10
/**
 * Searches code fragment using GitHub API. This kind of query is not possible without authorization.
 */
fun main(vararg args: String) {
    try {
        println("Enter username: ")
        val user = readLine()
        println("Enter password: ")
        val password = readLine()
        println("Enter code fragment: ")
        val input = readLine()
        if (input == null || input.isEmpty()) {
            println("Code fragment should not be empty.")
        }
        if (input!!.length > 256) {
            println("Code fragment has length more than 256 symbols.")
            return
        }

        val codeFragment = URLEncoder.encode(input, "UTF-8")
        val queryUrl = "https://api.github.com/search/code?q=$codeFragment+in:file"
        val urlConnection = URL(queryUrl).openConnection() as HttpURLConnection
        val authString = "Basic " + Base64.getEncoder().encodeToString("$user:$password".toByteArray())
        urlConnection.setRequestProperty("Authorization", authString)

        val response = urlConnection.inputStream.readBytes().toString(Charsets.UTF_8)

        val json = Json.parse(response).asObject()
        val links =
            json.get("items").asArray().take(linksLimit).stream().map { o -> o.asObject().get("html_url") }.collect(toList())

        println("${links.size} links with the given code fragment:")
        for (link in links) {
            println(link.asString())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}