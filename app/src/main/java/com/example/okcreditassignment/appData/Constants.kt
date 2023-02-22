package com.example.okcreditassignment.appData

import android.content.Context

interface Constants {
    companion object {
        val NETWORK_STATUS_TIMER: Long = 4000
    }
//    enum class StatusDescription(private val resourceId: Int) {
//        CUSTOM(-1),
//        NONE(R.string.empty),
//        HTTP_ERROR(R.string.http_error),
//        CONNECTION_REFUSED(R.string.connection_refused_error),
//        SSL_HANDSHAKE_FAILED(R.string.slow_internet),
//        NO_INTERNET(R.string.not_connected_to_internet_text),
//        SOCKET_TIMED_OUT(R.string.slow_internet),
//        CONNECTION_TIMED_OUT(R.string.connection_timed_out_error),
//        NO_HTTP_RESPONSE(R.string.remote_server_could_not_respond),
//        PARSING_ERROR(R.string.an_error_was_procured_while_parsing),
//        RUNTIME_ERROR(R.string.an_unexpected_error_occurred),
//        UNKNOWN_ERROR_OCCURRED(R.string.an_unexpected_error_occurred),
//        CONNECTION_RESET_BY_PEER(R.string.connection_reset_error),
//        UNEXPECTED_END_OF_STREAM(R.string.unexpected_end_stream);
//
//        private var message: String? = null
//        fun setMessage(message: String?) {
//            this.message = message
//        }
//
//        fun getMessage(context: Context?): String? {
//            return if (resourceId == -1) message else getString(resourceId)
//        }
//    }
}