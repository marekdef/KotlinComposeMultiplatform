package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.senordeveloper.kmpmerchant.AppViewModel

@Composable
fun UserLoggedInScreen(state: AppViewModel.AppState.UserLoggedIn) {
    Scaffold {

        Text(modifier = Modifier.padding(it), text = "Logged in ${state.user.username}")
    }
}