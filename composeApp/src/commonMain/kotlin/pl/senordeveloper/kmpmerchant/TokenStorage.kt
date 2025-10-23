package pl.senordeveloper.kmpmerchant

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.firstOrNull

class TokenStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val accessTokenKey = stringPreferencesKey("access_token")


    suspend fun storeBearerToken(tokens: BearerTokens) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = BearerTokensSerializer.serialize(
                BearerTokensWrapper.fromBearerTokens(tokens))
        }
    }

    suspend fun readBearerToken(): BearerTokens? = dataStore.data.firstOrNull()?.let { preferences ->
        val tokens = preferences[accessTokenKey]
        return tokens?.let {
            BearerTokensWrapper.toBearerTokens(BearerTokensSerializer.deserialize(it))
        }
    }
}



