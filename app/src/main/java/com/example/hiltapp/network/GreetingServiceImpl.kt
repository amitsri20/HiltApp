package com.example.hiltapp.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GreetingServiceImpl @Inject constructor() : GreetingService {
    override fun getGreeting(): String {
        return "Hello from Hilt (via Interface)!"
    }
}