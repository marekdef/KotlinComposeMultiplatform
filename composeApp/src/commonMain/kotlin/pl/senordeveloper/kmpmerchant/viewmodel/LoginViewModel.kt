package pl.senordeveloper.kmpmerchant.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.senordeveloper.kmpmerchant.TokenStorage
import pl.senordeveloper.kmpmerchant.di.Storage
import pl.senordeveloper.kmpmerchant.network.services.AuthService

class LoginViewModel(
    val authService: AuthService,
    val tokenStorage: TokenStorage,
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(
        LoginState(
            username = TextFieldValue("emilys"),
            password = TextFieldValue("emilyspass")
        )
    )
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events.asSharedFlow()

    fun onUsernameChange(newValue: TextFieldValue) {
        _state.update {
            it.copy(username = newValue)
        }
    }

    fun onPasswordChange(newValue: TextFieldValue) {
        _state.update {
            it.copy(password = newValue)
        }
    }


    fun login() {
        val state = _state.value
        viewModelScope.launch {
            authService.login(state.username.text, state.password.text)
                .onRight { userWithTokens ->
                    tokenStorage.storeBearerToken(BearerTokens(userWithTokens.accessToken, userWithTokens.refreshToken))
                    _events.emit(Event.LoggedIn)
                }.onLeft { throwable ->
                    _state.update {
                        it.copy(error = throwable.message)
                    }
                }
        }

    }

    data class LoginState(
        val username: TextFieldValue = TextFieldValue(),
        val password: TextFieldValue = TextFieldValue(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed interface Event {
        data object LoggedIn : Event
    }
}