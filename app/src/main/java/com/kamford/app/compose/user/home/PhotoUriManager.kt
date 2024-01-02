package com.kamford.app.compose.user.home

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

/**
 * Manages the creation of photo Uris. The Uri is used to store the photos taken with camera.
 */
class PhotoUriManager(private val appContext: Context) {

    fun buildNewUri(): PhotoState {
        val photosDir = File(appContext.externalCacheDir, PHOTOS_DIR)
        photosDir.mkdirs()
        val photoFile = File(photosDir, generateFilename())
        val authority = "${appContext.packageName}.$FILE_PROVIDER"
        return PhotoState(file = photoFile, uri = FileProvider.getUriForFile(appContext, authority, photoFile))
    }

    /**
     * Create a unique file name based on the time the photo is taken
     */
    private fun generateFilename() = "selfie-${System.currentTimeMillis()}.jpg"

    companion object {
        private const val PHOTOS_DIR = "external_files"
        private const val FILE_PROVIDER = "fileprovider"
    }
}

data class PhotoState(
    var uri: Uri? = null,
    var file: File? = null
)

enum class MimeType(val type: String) {
    JPEG(type = "image/jpeg"),
    PNG(type = "image/png"),
    WEBP(type = "image/webp"),
    HEIC(type = "image/heic"),
    HEIF(type = "image/heif"),
    BMP(type = "image/x-ms-bmp"),
    GIF(type = "image/gif"),
    MPEG(type = "video/mpeg"),
    MP4(type = "video/mp4"),
    QUICKTIME(type = "video/quicktime"),
    THREEGPP(type = "video/3gpp"),
    THREEGPP2(type = "video/3gpp2"),
    MKV(type = "video/x-matroska"),
    WEBM(type = "video/webm"),
    TS(type = "video/mp2ts"),
    AVI(type = "video/avi");

    companion object {

        fun ofAll(hasGif: Boolean = true): Set<MimeType> {
            return if (hasGif) {
                entries.toSet()
            } else {
                entries.filter { it != GIF }.toSet()
            }
        }

        fun ofImage(hasGif: Boolean = true): Set<MimeType> {
            return if (hasGif) {
                entries.filter { it.isImage }
            } else {
                entries.filter { it.isImage && it != GIF }
            }.toSet()
        }

        fun ofVideo(): Set<MimeType> {
            return entries.filter { it.isVideo }.toSet()
        }

    }
    internal val isImage: Boolean
        get() = type.startsWith(prefix = "image")
    internal val isVideo: Boolean
        get() = type.startsWith(prefix = "video")

}

//
//@Stable
//@Parcelize
//data class MediaResource(
//    internal val id: Long,
//    internal val bucketId: String,
//    internal val bucketName: String,
//    val uri: Uri,
//    val path: String,
//    val name: String,
//    val mimeType: String,
//) : Parcelable {
//
//    val isImage: Boolean
//        get() = mimeType.startsWith(prefix = "image")
//
//    val isVideo: Boolean
//        get() = mimeType.startsWith(prefix = "video")
//
//}
//
//
//
//
