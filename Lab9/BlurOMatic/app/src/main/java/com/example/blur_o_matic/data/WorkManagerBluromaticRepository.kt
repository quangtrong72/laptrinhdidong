package com.example.blur_o_matic.data

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.blur_o_matic.IMAGE_MANIPULATION_WORK_NAME
import com.example.blur_o_matic.KEY_BLUR_LEVEL
import com.example.blur_o_matic.KEY_IMAGE_URI
import com.example.blur_o_matic.TAG_OUTPUT
import com.example.blur_o_matic.getImageUri
import com.example.blur_o_matic.workers.BlurWorker
import com.example.blur_o_matic.workers.CleanupWorker
import com.example.blur_o_matic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    private var imageUri: Uri = context.getImageUri()
    private val workManager = WorkManager.getInstance(context)

    // Lọc lấy công việc đang chạy hoặc công việc mới nhất có Tag tương ứng
    override val outputWorkInfo: Flow<WorkInfo?> =
        workManager.getWorkInfosByTagFlow(TAG_OUTPUT).map { infoList ->
            infoList.find { it.state.isFinished.not() } ?: infoList.lastOrNull()
        }

    override fun applyBlur(blurLevel: Int) {
        var continuation = workManager.beginUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(CleanupWorker::class.java)
        )

        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
        blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))
        continuation = continuation.then(blurBuilder.build())

        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        continuation.enqueue()
    }

    override fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}
