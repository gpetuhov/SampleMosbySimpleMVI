package com.gpetuhov.android.samplemosbysimplemvi.domain.repository

import com.gpetuhov.android.samplemosbysimplemvi.domain.model.Greeting
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

// In a Production app, inject your Repository into your use case (interactor) instead of using singleton
object Repository {

    // Repository returns Observable that emits result with a delay
    fun loadHelloWorldText(): Observable<Greeting> {
        return Observable
            .just(Greeting(getRandomMessage()))
            .delay(5, TimeUnit.SECONDS)     // this is needed to mock database or network latency
    }

    private fun getRandomMessage(): String {
        val messages = listOf("Hello World", "Hola Mundo", "Hallo Welt", "Bonjour le monde")
        return messages[Random().nextInt(messages.size)]
    }
}