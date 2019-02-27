package com.gpetuhov.android.samplemosbysimplemvi.presentation.presenter

import com.gpetuhov.android.samplemosbysimplemvi.domain.interactor.GetHelloWorldTextInteractor
import com.gpetuhov.android.samplemosbysimplemvi.domain.viewstate.MainViewState
import com.gpetuhov.android.samplemosbysimplemvi.presentation.view.MainView
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

// Presenter listens both to user intents and to ViewState changes.
// On user intent presenter triggers call to the interactor.
// Interactor returns new view state.
// Presenter pushes view state back to the UI (this is the only place where UI is updated).

class MainPresenter : MviBasePresenter<MainView, MainViewState>() {

    override fun bindIntents() {

        // Subscribe to listen to user intents
        val helloWorldState: Observable<MainViewState> = intent(MainView::sayHelloWorldIntent)
            .subscribeOn(Schedulers.io())
            // Avoid handling button clicks in rapid succession
            .debounce(400, TimeUnit.MILLISECONDS)
            // Map intents into calls to the domain layer
            .switchMap { GetHelloWorldTextInteractor.getHelloWorldText() }
            // Log state changes
            .doOnNext { Timber.d("Received new state: %s", it) }
            .observeOn(AndroidSchedulers.mainThread())

        // Subscribe to listen to ViewState changes
        subscribeViewState(helloWorldState, MainView::render)
    }

    // We don't have to clear disposables as Mosby does it automatically,
    // when the View is detached permanently.
}
