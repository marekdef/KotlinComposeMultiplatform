package pl.senordeveloper.kmpmerchant

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.ui.LoginScreen
import pl.senordeveloper.kmpmerchant.ui.UserLoggedInScreen

@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel<AppViewModel>()) {
    val state by viewModel.appState.collectAsStateWithLifecycle()
    MaterialTheme {
        when(val state = state) {
            is AppViewModel.AppState.LoginState -> LoginScreen(
                state = state,
                onUserNameChange = viewModel::onUsernameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::login
            )

            is AppViewModel.AppState.UserLoggedIn -> UserLoggedInScreen(state)
        }
    }
}

