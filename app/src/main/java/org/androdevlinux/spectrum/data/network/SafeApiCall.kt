package org.androdevlinux.spectrum.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.net.ssl.SSLHandshakeException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                println("SafeApiCall Error ${throwable.message}")
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    is SSLHandshakeException -> Resource.Failure(false, 700, null)
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}