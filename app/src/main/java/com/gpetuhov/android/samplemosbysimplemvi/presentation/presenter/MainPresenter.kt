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

// In MVP Presenter may update the UI in many places. In MVI - only when view state changes.

// Presenter survives orientation changes.
// All subscriptions are temporarily unsubscribed when the view is detached,
// and resubscribed after the view is recreated and reattached (this is done by Mosby)

// In this simple example next view state does NOT depend on the previous one,
// that's why State Reducer is not used.

class MainPresenter : MviBasePresenter<MainView, MainViewState>() {

    // This method is invoked only the first time a View is attached to the Presenter.
    // It’s not invoked again when the View gets reattached (i.e. after a screen orientation change).
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

        // Subscribe to listen to ViewState changes.
        // This is the only place, where UI is updated.
        // View is always rendered with the LATEST ViewState.
        subscribeViewState(helloWorldState, MainView::render)
    }

    // We don't have to clear disposables as Mosby does it automatically,
    // when the View is detached permanently.
}
