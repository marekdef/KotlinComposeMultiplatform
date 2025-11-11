package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    onLoggedIn: () -> Unit = {}
) {

    ObserveAsEvents(viewModel.events) {
        when (it) {
            is LoginViewModel.Event.LoggedIn -> onLoggedIn()
        }
    }

    LoginScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onUserNameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginViewModel.LoginState,
    onUserNameChange: (TextFieldValue) -> Unit = {},
    onPasswordChange: (TextFieldValue) -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text("Login")
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username,
                readOnly = state.isLoading,
                onValueChange = onUserNameChange
            )

            TextField(
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                readOnly = state.isLoading,
                value = state.password,
                onValueChange = onPasswordChange
            )

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            state.error?.let { error ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = state.error,
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = onLoginClick,
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    Text("Loading...")
                } else {
                    Text("Login")
                }
            }
        }

    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginViewModel.LoginState(
            username = TextFieldValue("Jov"),
            password = TextFieldValue("Mit"),
            isLoading = false,
        )
    )
}

@Preview
@Composable
fun LoginScreenErrorPreview() {
    LoginScreen(
        state = LoginViewModel.LoginState(
            username = TextFieldValue("Jov"),
            password = TextFieldValue("Mit"),
            isLoading = false,
            error = "Invalid login"
        )
    )
}