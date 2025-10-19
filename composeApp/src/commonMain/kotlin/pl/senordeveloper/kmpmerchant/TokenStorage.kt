package pl.senordeveloper.kmpmerchant

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull

class TokenStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")

    suspend fun storeBearerToken(tokens: BearerTokens) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = tokens.accessToken
            preferences[refreshTokenKey] = tokens.refreshToken ?: ""
        }
    }

    suspend fun readBearerToken(): BearerTokens? = dataStore.data.firstOrNull()?.let { preferences ->
        val accessToken = preferences[accessTokenKey]
        val refreshToken = preferences[refreshTokenKey]

        accessToken?.let {
            return BearerTokens(accessToken, refreshToken)
        }
    }
}
