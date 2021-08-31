package com.mpdevelopment.imdbsearch.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

//If we have a login feature we can inject the UserManager and get the apikey from the User class
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("x-rapidapi-host", "imdb8.p.rapidapi.com")
            .header("x-rapidapi-key", "6bd21712f4mshfede02fb3f2dbf5p175116jsn211cb7cd59cf")
            .build()
        return chain.proceed(request)
    }
}