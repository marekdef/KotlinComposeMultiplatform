@file:OptIn(ExperimentalSerializationApi::class)

package pl.senordeveloper.kmpmerchant

import androidx.datastore.core.okio.OkioSerializer
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.serialization.ExperimentalSerializationApi
import okio.BufferedSink
import okio.BufferedSource

object BearerTokenSerializer : OkioSerializer<BearerTokens?> {
    override val defaultValue: BearerTokens? = null

    override suspend fun readFrom(source: BufferedSource): BearerTokens? {
        if (source.exhausted()) {
            return null
        }

        val accessTokenLength = source.readLong()
        val accessToken = source.readUtf8(accessTokenLength)

        val refreshTokenLength = source.readLong()
        if (refreshTokenLength == 0L) {
            return BearerTokens(accessToken, null)
        }
        val refreshToken = source.readUtf8(refreshTokenLength)

        return BearerTokens(accessToken, refreshToken)
    }

    override suspend fun writeTo(
        t: BearerTokens?,
        sink: BufferedSink
    ) {
        if (t == null) {
            return
        }

        sink.writeLong(t.accessToken.length.toLong())
        sink.writeUtf8(t.accessToken)

        t.refreshToken?.let { refreshToken ->
            sink.writeLong(refreshToken.length.toLong())
            sink.writeUtf8(refreshToken)
        } ?: sink.writeLong(0L)
    }
}