package com.example.blur_o_matic.workers

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.blur_o_matic.DELAY_TIME_MILLIS
import com.example.blur_o_matic.KEY_IMAGE_URI
import com.example.blur_o_matic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "SaveImageToFileWorker"

class SaveImageToFileWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override suspend fun doWork(): Result {
        makeStatusNotification(
            applicationContext.resources.getString(R.string.saving_image),
            applicationContext
        )

        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)

            val resolver = applicationContext.contentResolver
            return@withContext try {
                val resourceUri = inputData.getString(KEY_IMAGE_URI)
                require(!resourceUri.isNullOrEmpty()) { "Input URI is null or empty" }
                
                val uri = Uri.parse(resourceUri)
                
                // Cách đọc Bitmap an toàn hơn: hỗ trợ cả file:// và content://
                val bitmap = if (uri.scheme == "file") {
                    BitmapFactory.decodeFile(uri.path)
                } else {
                    resolver.openInputStream(uri).use { inputStream ->
                        BitmapFactory.decodeStream(inputStream)
                    }
                }

                if (bitmap == null) {
                    Log.e(TAG, "Failed to decode bitmap from URI: $resourceUri")
                    return@withContext Result.failure()
                }

                // Lưu vào MediaStore (Gallery)
                val imageUrl = saveBitmapToGallery(bitmap)

                if (!imageUrl.isNullOrEmpty()) {
                    val output = workDataOf(KEY_IMAGE_URI to imageUrl)
                    Result.success(output)
                } else {
                    Log.e(TAG, "Writing to MediaStore failed")
                    Result.failure()
                }
            } catch (exception: Exception) {
                Log.e(TAG, "Error saving image", exception)
                Result.failure()
            }
        }
    }

    private fun saveBitmapToGallery(bitmap: Bitmap): String? {
        val resolver = applicationContext.contentResolver
        val filename = "blur_${System.currentTimeMillis()}.png"
        
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/BlurOMatic")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        
        return try {
            imageUri?.let { uri ->
                resolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream == null || !bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                        throw Exception("Failed to compress and save bitmap")
                    }
                }
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }
                uri.toString()
            }
        } catch (e: Exception) {
            imageUri?.let { resolver.delete(it, null, null) }
            Log.e(TAG, "Failed to save bitmap", e)
            null
        }
    }
}
