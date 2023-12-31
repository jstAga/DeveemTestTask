package com.example.deveemtesttask.ui.core.bases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deveemtesttask.data.core.Either
import com.example.deveemtesttask.data.core.Resource
import com.geeks.ulul.ui.state.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> mutableUiStateFlow() = MutableStateFlow<UIState<T>>(UIState.Idle())

    protected fun <T, S> Flow<Either<String, T>>.gatherRequest(
        state: MutableStateFlow<UIState<S>>,
        mappedData: (data: T) -> S,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@gatherRequest.collect {
                when (it) {
                    is Either.Left -> state.value = UIState.Error(it.value)
                    is Either.Right -> state.value =
                        UIState.Success(mappedData(it.value))
                }
            }

        }
    }

    protected fun <T> Flow<Either<String, T>>.gatherRequest(
        state: MutableStateFlow<UIState<T>>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@gatherRequest.collect {
                when (it) {
                    is Either.Left -> state.value = UIState.Error(it.value)
                    is Either.Right -> state.value =
                        UIState.Success(it.value)
                }
            }

        }
    }

    protected fun <T> Flow<Resource<T>>.collectFlow(
        _state: MutableStateFlow<UIState<T>>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            this@collectFlow.collect { res ->
                when (res) {
                    is Resource.Error -> {
                        if (res.message != null) {
                            _state.value = UIState.Error(res.message)
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = UIState.Loading()
                    }
                    is Resource.Success -> {
                        if (res.data != null) {
                            _state.value = UIState.Success(res.data)
                        }
                    }
                }
            }
        }
    }
}