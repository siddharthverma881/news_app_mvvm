package com.example.okcreditassignment.repository

import android.util.Log
import com.example.okcreditassignment.network.ApiInterface
import org.apache.http.conn.ConnectTimeoutException
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import com.example.okcreditassignment.network.Result

open class BaseRepository {
    val apiInterface: ApiInterface by inject(ApiInterface::class.java)

    fun <T : Any> unwrapResponse(response: Response<T>): Result<T> {
        val body = response.body()
        val errorBody = response.errorBody()

        if(response.isSuccessful) {
            return Result.Success(body!!)
        } else {
//            val networkError = Result.Error<Any>(response.message().toString(), body.status)
//            return networkError
        }
        return Result.Success(response.body()!!)
    }

//    fun getStatusCode(cause: Throwable?): Codes.StatusCode {
//        var status: Codes.StatusCode = Codes.StatusCode.NONE
//        if(!NetworkUtils.isDeviceOnline(getAppContext())) {
//            return Codes.StatusCode.NETWORK_ERROR
//        }
//        if(cause == null) {
//            Codes.StatusCode.SOME_OTHER_ERROR
//        }
//        if (cause is UnknownHostException || cause is SocketTimeoutException || cause is ConnectException) {
//            status = Codes.StatusCode.NETWORK_ERROR
//        } else if (cause is java.lang.IllegalStateException) {
//            status = Codes.StatusCode.PARSING_ERROR
//        }
//        return status
//    }
//
//    fun resolveNetworkError(cause: Throwable?): Constants.StatusDescription {
//        if(!NetworkUtils.isDeviceOnline(getAppContext())) {
//            return Constants.StatusDescription.NO_INTERNET
//        }
//        if(cause == null) {
//            return Constants.StatusDescription.UNKNOWN_ERROR_OCCURRED
//        }
//
//        when (cause) {
//            is UnknownHostException -> return Constants.StatusDescription.NO_INTERNET
//            is SocketTimeoutException -> return Constants.StatusDescription.SOCKET_TIMED_OUT
//            is ConnectTimeoutException -> return Constants.StatusDescription.CONNECTION_TIMED_OUT
//            is SSLHandshakeException -> return Constants.StatusDescription.SSL_HANDSHAKE_FAILED
//            is ConnectException -> return Constants.StatusDescription.CONNECTION_REFUSED
//            is SSLException -> return Constants.StatusDescription.CONNECTION_RESET_BY_PEER
//            is IOException -> return Constants.StatusDescription.UNEXPECTED_END_OF_STREAM
//            is IllegalStateException -> return Constants.StatusDescription.PARSING_ERROR
//
//            /*
//             * When cause is unknown do this
//             */
//            else -> {
//                Log.e(
//                    BaseRepository::class.simpleName,
//                    "resolveNetworkError: " + cause.message
//                )
//                Log.e(
//                    BaseRepository::class.simpleName,
//                    "resolveNetworkError: $cause"
//                )
//                return Constants.StatusDescription.UNKNOWN_ERROR_OCCURRED
//            }
//        }
//    }
}