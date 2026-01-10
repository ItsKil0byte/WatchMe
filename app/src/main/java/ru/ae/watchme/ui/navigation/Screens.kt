package ru.ae.watchme.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    /* NOTE: Сериализую для того, чтобы в навигацию путей можно было передавать классы и объекты
    * Как я понял это упрощает процесс написания маршрутов и повышает безопасность */

    // Source - https://developer.android.com/guide/navigation/design/type-safety,
    // Source - https://medium.com/@ramadan123sayed/understanding-kotlinx-serialization-with-json-compile-time-and-runtime-mechanics-handling-url-080e064d1454

    @Serializable
    object MovieList : Screen

    @Serializable
    object RandomWheel : Screen

    @Serializable
    data class MovieDetails(val id: Int) : Screen
}