package com.borispuhacin.imagesearcher.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import java.io.IOException

object Utils {
    fun imageViewToBitmap(image: ImageView): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val bitmapDrawable: BitmapDrawable = image.drawable as BitmapDrawable
            bitmap = bitmapDrawable.bitmap
        } catch (e: IOException) {
            Log.e("Bitmap", "Cannot capture image")
        }
        return bitmap
    }

    fun saveImageToExternalStorage(displayName: String, bmp: Bitmap, context: Context): Boolean {
        val resolver = context.contentResolver
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.WIDTH, bmp.width)
            put(MediaStore.MediaColumns.HEIGHT, bmp.height)
        }

        return try {
            resolver.insert(imageCollection, contentValues)?.also { uri ->
                resolver.openOutputStream(uri).use { outputStream ->
                    if (!bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        throw IOException("Could Not Save Bitmap")
                    }
                }
            } ?: throw IOException("Could Not Create MediaStore entry")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}