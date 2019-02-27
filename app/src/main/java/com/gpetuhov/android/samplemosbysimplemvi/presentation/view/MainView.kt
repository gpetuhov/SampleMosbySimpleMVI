package com.gpetuhov.android.samplemosbysimplemvi.presentation.view

import com.gpetuhov.android.samplemosbysimplemvi.domain.viewstate.MainViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface MainView : MvpView {

    // View emits user actions (events, intents, commands).
    // In this sample view emits button clicks as Observables
    fun sayHelloWorldIntent(): Observable<Unit>

    // Render the state in the UI
    fun render(state: MainViewState)
}
