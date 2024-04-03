package com.read.comicbook.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.FileOutputStream


object FileUtils {

    fun saveImageToAppData(imageUri: Uri, activity: FragmentActivity, result: (fileName: String) -> Unit) {
        try {

            // Get the content resolver
            val contentResolver: ContentResolver = activity.contentResolver

            // Get the file name from the content resolver
            val fileName: String? = getFileName(contentResolver, imageUri)
            val renamed =
                System.currentTimeMillis().toString() + "." + fileName?.substringAfterLast('.')
            result(renamed)
            fileName?.let {
                // Create a file in the app's internal storage directory
                val appDataDirectory: File? = activity.getDir("app_data", Context.MODE_PRIVATE)
                val destinationFile = File(appDataDirectory, renamed)

                // Copy the selected image to the app's internal storage
                copyFileToDestination(contentResolver, imageUri, destinationFile)

                // You can now use the 'destinationFile' as needed
                // For example, you might want to display it using an ImageView
                // imageView.setImageURI(Uri.fromFile(destinationFile))
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
        try {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            return cursor?.use {
                val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                it.moveToFirst()
                it.getString(nameIndex)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return "null"
    }

    private fun copyFileToDestination(
        contentResolver: ContentResolver,
        sourceUri: Uri,
        destinationFile: File
    ) {
        try {
            contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    val buffer = ByteArray(4 * 1024) // 4k buffer size
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun retrieveImgFile(fileName: String, context: Context): Bitmap? {

        // Retrieve the app's internal storage directory
        val appDataDirectory: File? = context.getDir("app_data", Context.MODE_PRIVATE)

        // Create a File object for the saved image
        val savedImageFile = File(appDataDirectory, fileName)

        // Check if the file exists
        return if (savedImageFile.exists()) {
            // File exists, you can use it
            BitmapFactory.decodeFile(savedImageFile.absolutePath)
        } else {
            null
            // File does not exist
            // Handle the case where the file is not found
        }
    }
}