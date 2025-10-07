package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.network.dto.User
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel


@Composable
fun UserLoggedInScreen(
    viewModel: UserLoggedInViewModel = koinViewModel<UserLoggedInViewModel>(),
    onUsers: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UserLoggedInScreen(state, onRefresh = viewModel::refresh, onUsers = onUsers)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoggedInScreen(
    state: UserLoggedInViewModel.State,
    onRefresh: () -> Unit = {},
    onUsers: () -> Unit = {}
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.padding(top = 32.dp).fillMaxSize(),
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        state = pullToRefreshState
    ) {
        Column(
            Modifier
                .padding(top = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            state.error?.let {
                Text("Error: $it")
            }
            state.user?.let { user ->
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Logged in as ${user.username}"
                )
                Row {
                    AsyncImage(
                        model = user.image,
                        contentDescription = "User avatar",
                    )

                    Column {
                        Row {
                            Text(user.firstName)
                            Text(user.lastName)
                        }
                        Text(user.email)
                    }
                }

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

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = onUsers,
                enabled = !state.isLoading
            ) {
                Text(
                    "Go to users"
                )
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
