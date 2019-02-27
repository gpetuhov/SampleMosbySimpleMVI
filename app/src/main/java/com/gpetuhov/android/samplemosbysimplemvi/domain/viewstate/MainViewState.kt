package com.gpetuhov.android.samplemosbysimplemvi.domain.viewstate

// In MVI ViewState resides at domain layer.
// This is the state of the UI (every UI detail is represented here:
// data to be displayed, visibility of the views, scroll positions etc).
// ViewState is rendered into UI by the presenter.
// ViewState is immutable (new instance is created each time we need to change UI).

// In MVI ViewState is the MODEL !!!

// ViewState can be implemented differently depending on developers preferences
// (here Kotlin sealed classes are used).
sealed class MainViewState {
    object LoadingState : MainViewState()
    data class DataState(val greeting: String) : MainViewState()
    data class ErrorState(val error: Throwable) : MainViewState()
}