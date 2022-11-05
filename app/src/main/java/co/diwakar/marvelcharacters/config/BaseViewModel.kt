package co.diwakar.marvelcharacters.config

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    open fun onError(message: String?) = Unit
    open fun onLoadUpdated(isLoading: Boolean) = Unit
}