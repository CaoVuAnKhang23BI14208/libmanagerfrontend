package com.exemple.usthlibraryfrontend.remote

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class UserService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { 
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true 
            })
        }
    }

    private val baseUrl = "http://10.0.2.2:5001/api/users"

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        val response: HttpResponse = client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }

        val responseBody = response.bodyAsText()

        android.util.Log.d("UserService", "Login Response Body: $responseBody")

        return try {
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            android.util.Log.e("UserService", "Error parsing JSON: ${e.message}")
            throw e
        }
    }

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        val response: HttpResponse = client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }

        val responseBody = response.bodyAsText()
        android.util.Log.d("UserService", "Register Response Body: $responseBody")

        return try {
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            android.util.Log.e("UserService", "Error parsing JSON: ${e.message}")
            throw e
        }
    }
}