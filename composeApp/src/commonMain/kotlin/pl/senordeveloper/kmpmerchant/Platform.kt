package pl.senordeveloper.kmpmerchant

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform