package com.example.foody.util

/**
 * sealed class에 대해서는 https://codechacha.com/ko/kotlin-sealed-classes/ <- 블로그를 참고할 것
 * */
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
) {

    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(data: T? = null, message: String?,): NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()

}
