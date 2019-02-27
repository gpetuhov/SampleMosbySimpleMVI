package com.gpetuhov.android.samplemosbysimplemvi.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gpetuhov.android.samplemosbysimplemvi.R
import com.gpetuhov.android.samplemosbysimplemvi.domain.viewstate.MainViewState
import com.gpetuhov.android.samplemosbysimplemvi.presentation.presenter.MainPresenter
import com.gpetuhov.android.samplemosbysimplemvi.presentation.view.MainView
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_main.*

// MVI is characterized by Unidirectional data flow:
// 1. User clicks button
// 2. View (MainActivity) emits intent (sayHelloWorldIntent)
// 3. Presenter listens to user intents, so it gets triggered
// 4. Presenter triggers interactor
// 5. Interactor queries Repository and returns new view state
// 6. Presenter listens to view state changes, so it gets triggered again
// 7. Presenter renders new view state into the UI by calling MainView.render()

class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter() = MainPresenter()

    // Bind button clicks with RxBinding
    // (we get Observable that emits Unit on every button click).
    override fun sayHelloWorldIntent() = helloWorldButton.clicks()

    // === Private methods ===

    // Update the UI depending on view state
    override fun render(state: MainViewState) {
        when(state) {
            is MainViewState.LoadingState -> renderLoadingState()
            is MainViewState.DataState -> renderDataState(state)
            is MainViewState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState() {
        loadingIndicator.visibility = View.VISIBLE
        helloWorldTextview.visibility = View.GONE
    }

    private fun renderDataState(dataState: MainViewState.DataState) {
        loadingIndicator.visibility = View.GONE
        helloWorldTextview.visibility = View.VISIBLE
        helloWorldTextview.text = dataState.greeting
    }

    private fun renderErrorState(errorState: MainViewState.ErrorState) {
        loadingIndicator.visibility = View.GONE
        helloWorldTextview.visibility = View.GONE
        Toast.makeText(this, "error ${errorState.error}", Toast.LENGTH_LONG).show()
    }
}
