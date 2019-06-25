package com.majorik.data.repositories

import retrofit2.Response
import java.io.IOException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class BaseRepository {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BaseRepository::class.java)
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success -> data = result.data

            is Result.Error -> {
                logger.debug("$errorMessage & exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }else{
                logger.debug("isFailure: " + response.message())
            }
        } catch (e: Exception) {
            logger.debug(e.message)
        }

        return Result.Error(IOException("Произошла ошибка при выполнении запроса, *custom ERROR* - $errorMessage"))
    }
}