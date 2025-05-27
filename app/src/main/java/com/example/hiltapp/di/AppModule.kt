package com.example.hiltapp.di

import com.example.hiltapp.network.GreetingService
import com.example.hiltapp.network.GreetingServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Scope this module to the application lifecycle
abstract class AppModule {

    @Binds // Use @Binds for interface bindings when the implementation has an @Inject constructor
    @Singleton // Ensure the binding provides a singleton
    abstract fun bindGreetingService(
        greetingServiceImpl: GreetingServiceImpl
    ): GreetingService
}