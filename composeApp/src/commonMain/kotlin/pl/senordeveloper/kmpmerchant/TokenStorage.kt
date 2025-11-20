package pl.senordeveloper.kmpmerchant

import androidx.datastore.core.DataStore
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withTimeout

class TokenStorage(
    private val dataStore: DataStore<BearerTokens?>
) {
    suspend fun storeBearerToken(tokens: BearerTokens) {
        dataStore.updateData { data ->
            tokens
        }
    }

    suspend fun readBearerToken(): BearerTokens? =
        withTimeout(1000) {
            dataStore.data.firstOrNull()
        }

}



