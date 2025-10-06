package pl.senordeveloper.kmpmerchant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.senordeveloper.kmpmerchant.network.dto.users.FullUser
import pl.senordeveloper.kmpmerchant.network.services.UserService

class UsersViewModel(
    val userService: UserService
) : ViewModel(

) {

    // create mutable state flow
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> =
        _state.onStart { loadUsers() }.stateIn(viewModelScope, WhileSubscribed(5000), State())

    fun loadUsers() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    error = null
                )
            }
            userService.getUsers().onRight {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        error = null,
                        users = it.users
                    )
                }
            }
                .onLeft {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
                }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val users: List<FullUser> = emptyList(),
    )
}
