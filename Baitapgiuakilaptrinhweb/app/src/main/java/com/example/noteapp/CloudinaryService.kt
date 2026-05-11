package com.example.noteapp

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

object CloudinaryService {

    private var isInitialized = false

    fun init(context: Context) {
        if (isInitialized) return
        val config = mapOf(
            "cloud_name" to "dmjwgmao9",    // <-- thay vào đây
            "api_key"    to "854115274156914",         // <-- thay vào đây
            "api_secret" to "05CQkQHzrCH55-Bok-Wq_zUThbo"       // <-- thay vào đây
        )
        MediaManager.init(context, config)
        isInitialized = true
    }

    fun uploadImage(
        uri: Uri,
        onSuccess: (url: String) -> Unit,
        onError: (msg: String) -> Unit
    ) {
        MediaManager.get()
            .upload(uri)
            .unsigned("noteapp_preset")        // <-- thay upload preset
            .callback(object : UploadCallback {

                override fun onStart(requestId: String) {}

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    val url = resultData["secure_url"] as? String ?: ""
                    onSuccess(url)
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    onError(error.description)
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {}
            })
            .dispatch()
    }
}