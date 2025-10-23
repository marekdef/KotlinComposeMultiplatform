package pl.senordeveloper.kmpmerchant.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import io.ktor.client.plugins.auth.providers.BearerTokens
import okio.FileSystem
import okio.SYSTEM
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.BearerTokenSerializer
import pl.senordeveloper.kmpmerchant.TokenStorage
import pl.senordeveloper.kmpmerchant.datastore.PathProvider

val dataStoreModule = module {
    single<TokenStorage> {
        TokenStorage(
            dataStore = get()
        )
    }

    single<DataStore<BearerTokens?>> {
        val pathProvider = get<PathProvider>()
        DataStoreFactory.create(
            storage = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                producePath = {
                    pathProvider("token_datastore.pb")
                },
                serializer = BearerTokenSerializer
            )
        )
    }

}

