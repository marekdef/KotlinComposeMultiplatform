package pl.senordeveloper.kmpmerchant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.senordeveloper.kmpmerchant.network.dto.User
import pl.senordeveloper.kmpmerchant.network.services.AuthService

class UserLoggedInViewModel(private val authService: AuthService) : ViewModel() {
    private val _state = MutableStateFlow(State(isLoading = true))
    val state = _state.onStart {
        refreshUser()
    }.stateIn(viewModelScope, WhileSubscribed(5000), State(isLoading = true))

    fun refresh() {
        viewModelScope.launch {
            refreshUser()
        }
    }

    private suspend fun refreshUser() {
        _state.update {
            it.copy(isLoading = true, error = null)
        }
        authService.get().onLeft { throwable ->
            _state.update {
                it.copy(
                    isLoading = false,
                    error = throwable.message
                )
            }
        }.onRight { user ->
            _state.update {
                it.copy(
                    isLoading = false,
                    user = user
                )
            }
        }
    }


    data class State(
        val isLoading: Boolean = false,
        val user: User? = null,
        val error: String? = null,
    )
}
