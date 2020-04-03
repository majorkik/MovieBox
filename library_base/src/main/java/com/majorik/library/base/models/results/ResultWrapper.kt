package com.majorik.library.base.models.results

import com.majorik.library.base.models.error.ErrorResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}
