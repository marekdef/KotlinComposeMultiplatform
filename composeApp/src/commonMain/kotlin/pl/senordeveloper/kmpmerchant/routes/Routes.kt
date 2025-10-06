package pl.senordeveloper.kmpmerchant.routes

sealed class Routes {
    val route = this::class.qualifiedName ?: "unknown"
    data object Login: Routes()
    data object User: Routes()

    data object Users: Routes()
}