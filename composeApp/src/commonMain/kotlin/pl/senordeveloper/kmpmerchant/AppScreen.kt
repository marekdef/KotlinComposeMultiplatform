package pl.senordeveloper.kmpmerchant

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.senordeveloper.kmpmerchant.routes.Routes
import pl.senordeveloper.kmpmerchant.ui.LoginScreen
import pl.senordeveloper.kmpmerchant.ui.UserLoggedInScreen
import pl.senordeveloper.kmpmerchant.ui.UsersScreen

@Composable
@Preview
fun AppScreen() {
    val navController: NavHostController = rememberNavController()

    MaterialTheme {
        Scaffold { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = Routes.Login.route,
            ) {
                composable(
                    route = Routes.Login.route
                ) {
                    LoginScreen(onLoggedIn = {
                        navController.navigate(Routes.User.route)
                    })
                }
                composable(
                    route = Routes.User.route
                ) {
                    UserLoggedInScreen(
                        onUsers = {
                            navController.navigate(Routes.Users.route)
                        }
                    )
                }
                composable(
                    route = Routes.Users.route
                ) {
                    UsersScreen()
                }
            }
        }
    }
}

