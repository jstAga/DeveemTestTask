package com.example.deveemtesttask.data.core.bases

import android.util.Log
import com.example.deveemtesttask.data.core.Either
import com.example.deveemtesttask.data.core.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

abstract class BaseRepository {

    protected fun <T> doRemoteRequest(response: suspend () -> Resource<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(response().data))
        } catch (ioException: IOException) {
            emit(Resource.Error(ioException.localizedMessage ?: "unknown exception"))
        }
    }.flowOn(Dispatchers.IO)

    protected fun <T> makeNetworkRequest(
        gatherIfSucceed: ((T) -> Unit)? = null,
        request: suspend () -> T
    ) =
        flow<Either<String, T>> {
            request().also {
                gatherIfSucceed?.invoke(it)
                emit(Either.Right(value = it))
            }
        }.flowOn(Dispatchers.IO).catch { exception ->
            emit(Either.Left(value = exception.localizedMessage ?: "Error Occurred!"))
        }

    protected fun <T> doLocalRequest(response: suspend () -> T): Flow<Resource<T>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(response()))
        } catch (ioException: IOException) {
            Log.e("aga", "doLocalRequest: $ioException", )
            emit(Resource.Error(ioException.localizedMessage ?: "unknown exception"))
        }
    }.flowOn(Dispatchers.IO)
}