package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.network.dto.User
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel


@Composable
fun UserLoggedInScreen(viewModel: UserLoggedInViewModel = koinViewModel<UserLoggedInViewModel>()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UserLoggedInScreen(state, onRefresh = viewModel::refresh)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoggedInScreen(
    state: UserLoggedInViewModel.State,
    onRefresh: () -> Unit = {}
) {
    Scaffold { paddingValues ->
        val pullToRefreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues).fillMaxWidth(),
            isRefreshing = state.isLoading,
            onRefresh = onRefresh,
            state = pullToRefreshState
        ) {
            Column(
                Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                state.error?.let {
                    Text("Error: $it")
                }
                state.user?.let {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "Logged in as ${it.username}"
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = onRefresh,
                    enabled = !state.isLoading
                ) {
                    Text(
                        if (state.isLoading)
                            "Loading..."
                        else "Refresh"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserLoggedInScreenPreview() {
    UserLoggedInScreen(
        UserLoggedInViewModel.State(
            isLoading = false,
            user = User(
                id = "1",
                username = "johnd",
                firstName = "John",
                lastName = "Doe",
                email = "johnd@example.com",
                gender = "male",
                image = "null"
            )
        )
    )
}
