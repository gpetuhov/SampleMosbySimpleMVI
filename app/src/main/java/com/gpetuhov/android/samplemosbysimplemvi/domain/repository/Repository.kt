package com.gpetuhov.android.samplemosbysimplemvi.domain.repository

import io.reactivex.Observable
import java.util.*

// In a Production app, inject your Repository into your use case (interactor) instead of using singleton
object Repository {

    fun loadHelloWorldText(): Observable<String> = Observable.just(getRandomMessage())

    private fun getRandomMessage(): String {
        val messages = listOf("Hello World", "Hola Mundo", "Hallo Welt", "Bonjour le monde")
        return messages[Random().nextInt(messages.size)]
    }
}