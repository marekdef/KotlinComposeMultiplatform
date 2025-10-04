package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.LoginViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoggedInScreen(viewModel: UserLoggedInViewModel = koinViewModel<UserLoggedInViewModel>()) {
    Scaffold { paddingValues ->
        val state by viewModel.state.collectAsStateWithLifecycle()

        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = viewModel::refresh,
        ) {
            Column(
                Modifier.padding(paddingValues).padding(top = 16.dp)
            ) {
                state.error?.let {
                    Text("Error: $it")
                }
                state.user?.let {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Logged in as ${it.username}"
                    )
                }

                Button(onClick = viewModel::refresh, enabled = !state.isLoading) {
                    Text(if (state.isLoading) "Loading..." else "Refresh")
                }
            }
        }


    }
}