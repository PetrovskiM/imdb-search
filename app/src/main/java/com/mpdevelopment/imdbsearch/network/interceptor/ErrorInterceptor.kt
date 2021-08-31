package com.mpdevelopment.imdbsearch.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//TODO WE CAN HAVE A GLOBAL INTERCEPTOR TO HANDLE 400 ETC CASES
//We can use an RxJava PublishSubject / SharedFlow to have a stream of errors that can be injected
//in places where we want to listen to for errors etc.
//We can also have an Authenticator : Interceptor which adds needed headers
//AccessTokenAuthenticator : Authenticator can be used to oAuth / refresh tokens
class ErrorInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}