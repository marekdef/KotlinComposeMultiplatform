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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.senordeveloper.kmpmerchant.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: AppViewModel.AppState.LoginState,
    onUserNameChange: (TextFieldValue) -> Unit = {},
    onPasswordChange: (TextFieldValue) -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text("Login")
        })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username,
                onValueChange = onUserNameChange
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                onValueChange = onPasswordChange
            )

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = onLoginClick,
                enabled = !state.isLoading
            ) {
                Text("Login")
            }
        }

    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = AppViewModel.AppState.LoginState(
            username = androidx.compose.ui.text.input.TextFieldValue("Jov"),
            password = androidx.compose.ui.text.input.TextFieldValue("Mit"),
            isLoading = false,
        )
    )
}