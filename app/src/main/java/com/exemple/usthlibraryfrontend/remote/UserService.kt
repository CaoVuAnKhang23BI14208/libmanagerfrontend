package com.exemple.usthlibraryfrontend.remote

import android.content.Context
import com.exemple.usthlibraryfrontend.data.SessionManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class UserService(context: Context) {
    private val sessionManager = SessionManager(context)

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            sessionManager.fetchAuthToken()?.let { token ->
                header("Authorization", "Bearer $token")
            }
        }
    }

    private val baseUrl = "http://10.0.2.2:5001/api/users"

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return client.post("$baseUrl/login") {
            setBody(loginRequest)
        }.body()
    }

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return client.post("$baseUrl/register") {
            setBody(registerRequest)
        }.body()
    }

    suspend fun getAllUsers(page: Int = 1, limit: Int = 100, fullname: String? = null): GetAllUsersResponse {
        return client.get(baseUrl) {
            url {
                parameters.append("page", page.toString())
                parameters.append("limit", limit.toString())
                fullname?.let { parameters.append("fullname", it) }
            }
        }.body()
    }
}