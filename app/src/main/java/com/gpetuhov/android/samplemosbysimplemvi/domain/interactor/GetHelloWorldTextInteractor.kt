package com.gpetuhov.android.samplemosbysimplemvi.domain.interactor

import com.gpetuhov.android.samplemosbysimplemvi.domain.repository.Repository
import com.gpetuhov.android.samplemosbysimplemvi.domain.viewstate.MainViewState
import io.reactivex.Observable

// This is use case (interactor).
// We should have interactor for every user action (in this example only one).

// In a Production app, this should be injected instead of using singleton.
object GetHelloWorldTextInteractor {

    fun getHelloWorldText(): Observable<MainViewState> {
        // Make a call to the repository to get the data
        return Repository.loadHelloWorldText()
            // Create DataState and cast it into MainViewState
            .map<MainViewState> { MainViewState.DataState(it.text) }
            // Emit LoadingState value prior to emitting the data
            .startWith(MainViewState.LoadingState)
            // Do no throw an error - emit the ErrorState instead
            .onErrorReturn { MainViewState.ErrorState(it) }
    }
}