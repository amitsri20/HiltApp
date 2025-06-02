package com.example.hiltapp.network.models

data class Users(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String
)

