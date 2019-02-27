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

// TODO: describe sequence of interactions in the comments

class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter() = MainPresenter()

    override fun sayHelloWorldIntent() = helloWorldButton.clicks()

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
