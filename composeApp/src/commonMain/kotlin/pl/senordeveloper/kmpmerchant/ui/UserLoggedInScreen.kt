package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.LoginViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel

@Composable
fun UserLoggedInScreen(viewModel: UserLoggedInViewModel = koinViewModel<UserLoggedInViewModel>()) {
    Scaffold {
        paddingValues ->
        val state by viewModel.state.collectAsStateWithLifecycle()

        Column(Modifier.padding(paddingValues)) {

            state.error?.let {
                Text("Error: $it")
            }
            state.user?.let {
                Text(modifier = Modifier, text = "Logged in as ${it.username}")
            }
        }

    }
}