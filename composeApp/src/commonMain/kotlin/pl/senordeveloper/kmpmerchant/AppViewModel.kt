package pl.senordeveloper.kmpmerchant

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.senordeveloper.kmpmerchant.di.ApiService
import pl.senordeveloper.kmpmerchant.di.User
import pl.senordeveloper.kmpmerchant.di.UserRequest

class AppViewModel(val apiService: ApiService) : ViewModel() {
    fun login(username: String, password: String) {
        viewModelScope.launch {

        }
    }

    private val _appState = MutableStateFlow<AppState>(
        AppState.LoginState(
            username = TextFieldValue(""),
            password = TextFieldValue(""),
            isLoading = false
        )
    )
    val appState = _appState.asStateFlow()

    sealed interface AppState {
        data class LoginState(
            val username: TextFieldValue,
            val password: TextFieldValue,
            val isLoading: Boolean
        ): AppState
    }
}
