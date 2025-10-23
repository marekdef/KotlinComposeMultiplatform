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
        if (source.readByte() == 0.toByte()) {
            return null
        }

        val accessToken = source.readUtf8()
        val hasRefreshToken = source.readByte().toInt() != 0
        val refreshToken = if (hasRefreshToken) {
            source.readUtf8()
        } else null

        return BearerTokens(accessToken, refreshToken)
    }

    override suspend fun writeTo(
        t: BearerTokens?,
        sink: BufferedSink
    ) {
        if (t == null) {
            sink.writeByte(0)
            return
        }

        val bearerTokens = t
        sink.writeUtf8(bearerTokens.accessToken)

        val refreshToken = bearerTokens.refreshToken
        if (refreshToken != null) {
            sink.writeByte(1)
            sink.writeUtf8(refreshToken)
        } else {
            sink.writeByte(0)
        }
    }
}