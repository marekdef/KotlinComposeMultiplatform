package pl.senordeveloper.kmpmerchant

import com.varabyte.truthish.assertThat
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.test.runTest
import okio.Buffer
import kotlin.test.Test

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class BearerTokenSerializerTest {

    @Test
    fun `should return null when source is empty`() = runTest {
        val bufferedSource = Buffer()

        val readFrom = BearerTokenSerializer.readFrom(bufferedSource)

        assertThat(readFrom).isNull()
    }

    @Test
    fun `should serialize null successfully`() = runTest {
        val bufferedSource = Buffer()
        BearerTokenSerializer.writeTo(null, bufferedSource)

        assertThat(bufferedSource.exhausted()).isTrue()
    }

    @Test
    fun `should serialize access token successfully`() = runTest {
        val bufferedSource = Buffer()
        val accessToken = randomString()
        BearerTokenSerializer.writeTo(BearerTokens(accessToken, null), bufferedSource)

        assertThat(bufferedSource.readUtf8()).contains(accessToken)
    }

    @Test
    fun `should serialize and deserialize bearer token with access token successfully`() = runTest {
        val sink = Buffer()
        val accessToken = randomString()
        val bearerTokens = BearerTokens(accessToken, null)

        BearerTokenSerializer.writeTo(bearerTokens, sink)
        val readFrom = BearerTokenSerializer.readFrom(sink)

        assertThat(readFrom?.accessToken).isEqualTo(bearerTokens.accessToken)
        assertThat(readFrom?.refreshToken).isEqualTo(bearerTokens.refreshToken)
    }

    @Test
    fun `should serialize and deserialize bearer token with access and refresh token successfully`() = runTest {
        val sink = Buffer()
        val accessToken = randomString()
        val refreshToken = randomString()
        val bearerTokens = BearerTokens(accessToken, refreshToken)

        BearerTokenSerializer.writeTo(bearerTokens, sink)
        val readFrom = BearerTokenSerializer.readFrom(sink)

        assertThat(readFrom?.accessToken).isEqualTo(bearerTokens.accessToken)
        assertThat(readFrom?.refreshToken).isEqualTo(bearerTokens.refreshToken)
    }
}

@OptIn(ExperimentalUuidApi::class)
private fun randomString() = Uuid.random().toString()