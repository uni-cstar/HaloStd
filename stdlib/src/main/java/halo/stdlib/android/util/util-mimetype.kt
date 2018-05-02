package halo.stdlib.android.util

import android.webkit.MimeTypeMap
import halo.stdlib.kotlin.ext.getSuffix
import java.io.File


/**
 * Created by Lucio on 18/1/18.
 */

const val MIME_TYPE_APK = "application/vnd.android.package-archive"
const val MIME_TYPE_PDF = "application/pdf"
const val MIME_TYPE_ZIP = "application/zip"

const val MIME_TYPE_JPEG = "image/jpeg"
const val MIME_TYPE_JPG = MIME_TYPE_JPEG
const val MIME_TYPE_PNG = "image/png"
const val MIME_TYPE_BMP = "image/bmp"
const val MIME_TYPE_GIF = "image/gif"

const val MIME_TYPE_HTML = "text/html"
const val MIME_TYPE_HTM = MIME_TYPE_HTML

const val MIME_TYPE_MP3 = "audio/x-mpeg"
const val MIME_TYPE_MP4 = "video/mp4"
const val MIME_TYPE_3GP = "video/3gpp"
const val MIME_TYPE_ALL = "*/*"

/**
 * 获取文件名后缀
 */
fun File.getSuffix(): String? {
    if (isDirectory) {
        return null
    }
    return name.orEmpty().getSuffix()
}

/**
 * 获取文件mimte type
 */
fun File.getMimeType(file: File): String = file.name.getMimeType()

fun String.getMimeType(): String {
    val suffix = getSuffix() ?: return "file/*"
    val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix)
    if (!type.isNullOrEmpty()) {
        return type
    }
    return "file/*"
}


//{".3gp", "video/3gpp"},
//{".apk", "application/vnd.android.package-archive"},
//{".asf", "video/x-ms-asf"},
//{".avi", "video/x-msvideo"},
//{".bin", "application/octet-stream"},
//{".bmp", "image/bmp"},
//{".c", "text/plain"},
//{".class", "application/octet-stream"},
//{".conf", "text/plain"},
//{".cpp", "text/plain"},
//{".doc", "application/msword"},
//{".exe", "application/octet-stream"},
//{".gif", "image/gif"},
//{".gtar", "application/x-gtar"},
//{".gz", "application/x-gzip"},
//{".h", "text/plain"},
//{".htm", "text/html"},
//{".html", "text/html"},
//{".jar", "application/java-archive"},
//{".java", "text/plain"},
//{".jpeg", "image/jpeg"},
//{".jpg", "image/jpeg"},
//{".js", "application/x-javascript"},
//{".log", "text/plain"},
//{".m3u", "audio/x-mpegurl"},
//{".m4a", "audio/mp4a-latm"},
//{".m4b", "audio/mp4a-latm"},
//{".m4p", "audio/mp4a-latm"},
//{".m4u", "video/vnd.mpegurl"},
//{".m4v", "video/x-m4v"},
//{".mov", "video/quicktime"},
//{".mp2", "audio/x-mpeg"},
//{".mp3", "audio/x-mpeg"},
//{".mp4", "video/mp4"},
//{".mpc", "application/vnd.mpohun.certificate"},
//{".mpe", "video/mpeg"},
//{".mpeg", "video/mpeg"},
//{".mpg", "video/mpeg"},
//{".mpg4", "video/mp4"},
//{".mpga", "audio/mpeg"},
//{".msg", "application/vnd.ms-outlook"},
//{".ogg", "audio/ogg"},
//{".pdf", "application/pdf"},
//{".png", "image/png"},
//{".pps", "application/vnd.ms-powerpoint"},
//{".ppt", "application/vnd.ms-powerpoint"},
//{".prop", "text/plain"},
//{".rar", "application/x-rar-compressed"},
//{".rc", "text/plain"},
//{".rmvb", "audio/x-pn-realaudio"},
//{".rtf", "application/rtf"},
//{".sh", "text/plain"},
//{".tar", "application/x-tar"},
//{".tgz", "application/x-compressed"},
//{".txt", "text/plain"},
//{".wav", "audio/x-wav"},
//{".wma", "audio/x-ms-wma"},
//{".wmv", "audio/x-ms-wmv"},
//{".wps", "application/vnd.ms-works"},
////{".xml", "text/xml"},
//{".xml", "text/plain"},
//{".z", "application/x-compress"},
//{".zip", "application/zip"},
//{"", "*/*"}