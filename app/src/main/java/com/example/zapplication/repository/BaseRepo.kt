package com.example.zapplication.repository

import com.example.zapplication.utils.ErrorEntity
import com.example.zapplication.utils.GeneralErrorHandler
import com.example.zapplication.utils.NetworkUtils
import com.example.zapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class BaseRepo {
    @Inject
    lateinit var errorHandler: GeneralErrorHandler

    @Inject
    lateinit var networkUtils: NetworkUtils

    protected fun loadFromApi(api: suspend () -> Any) =
        if (networkUtils.isNetworkAvailable()) {
            flow {
                val result = api.invoke()
                emit(Resource.success(result))
            }.catch { e ->
                e.printStackTrace()
                emit(Resource.error(errorHandler.getErrorMessage(errorHandler.getError(e)), e))
            }.flowOn(Dispatchers.IO)
        } else {
            flow { emit(Resource.error<Nothing>(errorHandler.getErrorMessage(ErrorEntity.Network))) }.flowOn(
                Dispatchers.IO
            )
        }

}