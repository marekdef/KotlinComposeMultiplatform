package pl.senordeveloper.kmpmerchant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pl.senordeveloper.kmpmerchant.network.dto.users.Address
import pl.senordeveloper.kmpmerchant.network.dto.users.Bank
import pl.senordeveloper.kmpmerchant.network.dto.users.Company
import pl.senordeveloper.kmpmerchant.network.dto.users.Coordinates
import pl.senordeveloper.kmpmerchant.network.dto.users.Crypto
import pl.senordeveloper.kmpmerchant.network.dto.users.FullUser
import pl.senordeveloper.kmpmerchant.network.dto.users.Hair
import pl.senordeveloper.kmpmerchant.viewmodel.UsersViewModel


@Composable
fun UsersScreen(viewModel: UsersViewModel = koinViewModel<UsersViewModel>()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UsersScreen(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    state: UsersViewModel.State,
    onBack: () -> Unit = {}
) {
    Scaffold { paddingValues ->

        Column(Modifier.padding(paddingValues).fillMaxWidth()) {
            if (state.isLoading)
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))

            state.error?.let {
                Text("Error: $it", modifier = Modifier.padding(16.dp))
            }

            if (state.users.isNotEmpty()) {
                LazyColumn {
                    items(state.users.size) { index ->
                        val user = state.users[index]
                        UserItem(user)
                    }
                }
            } else
                Text("No users", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun UserItem(user: FullUser) {
    Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier.padding(8.dp),
            model = user.image,
            contentDescription = "User avatar",
        )

        Column(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = user.username
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Text("${user.firstName} ${user.lastName}")
            }

            Text(user.email)
        }

    }
}

@Preview
@Composable
fun UsersScreenPreview() {
    UsersScreen(
        UsersViewModel.State(
            isLoading = false,
            users = listOf(
                FullUser(
                    id = 1,
                    firstName = "John",
                    lastName = "Doe",
                    maidenName = "Smith",
                    age = 30,
                    gender = "male",
                    email = "john.doe@example.com",
                    phone = "+123456789",
                    username = "johndoe",
                    password = "password123",
                    birthDate = "1994-01-01",
                    image = "https://example.com/image.jpg",
                    bloodGroup = "A+",
                    height = 180.0,
                    weight = 75.0,
                    eyeColor = "blue",
                    hair = Hair(color = "brown", type = "straight"),
                    ip = "192.168.1.1",
                    address = Address(
                        address = "123 Main St",
                        city = "Sample City",
                        coordinates = Coordinates(lat = 52.2297, lng = 21.0122),
                        postalCode = "00-001",
                        state = "Sample State",
                        stateCode = "SS",
                        country = "Sample Country"
                    ),
                    macAddress = "00:1B:44:11:3A:B7",
                    university = "Sample University",
                    bank = Bank(
                        cardExpire = "06/25",
                        cardNumber = "1234-5678-9012-3456",
                        cardType = "Visa",
                        currency = "USD",
                        iban = "US00SAMPLE123456789"
                    ),
                    company = Company(
                        address = Address(
                            address = "456 Business Rd",
                            city = "Business City",
                            coordinates = Coordinates(lat = 40.7128, lng = -74.0060),
                            postalCode = "10001",
                            state = "Business State",
                            stateCode = "BS",
                            country = "Sample Country"
                        ),
                        department = "Engineering",
                        name = "SampleCorp",
                        title = "Software Engineer"
                    ),
                    ein = "12-3456789",
                    ssn = "123-45-6789",
                    userAgent = "Mozilla/5.0",
                    crypto = Crypto(
                        coin = "Bitcoin",
                        wallet = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa",
                        network = "BTC"
                    ),
                    role = "user"
                )
            )
        )
    )
}

@Preview
@Composable
fun UsersScreenLoadingPreview() {
    UsersScreen(
        state = UsersViewModel.State(isLoading = true)
    )
}
