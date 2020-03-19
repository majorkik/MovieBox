package com.majorik.data.repositories

import com.majorik.domain.tmdbModels.error.ErrorResponse
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class BaseRepository {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)

                        Logger.d("HttpException: $code, ${errorResponse?.statusCode}, ${errorResponse?.statusMessage}")

                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        Logger.d("GenericError")

                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}
