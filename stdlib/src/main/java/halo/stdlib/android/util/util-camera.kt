package halo.stdlib.android.util

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import halo.stdlib.kotlin.BestPerformance
import halo.stdlib.kotlin.util.orDefault
import halo.stdlib.kotlin.util.orDefaultIfNullOrEmpty
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Lucio on 18/1/10.
 * 参考：http://developer.android.com/training/camera/photobasics.html
 * 国内访问地址：https://developer.android.google.cn/training/camera/photobasics.html
 */

//在清单文件中添加如下配置，确保设备是否支持照相机才能下载应用
//<manifest ... >
//<uses-feature android:name="android.hardware.camera"
//android:required="true" />
//...
//</manifest>


/**
 * 照相机是否可用
 */
fun Context.isCameraEnable(): Boolean {
    return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
}

/**
 *  让系统媒体扫描器扫描指定路径的文件，确保生成的图片文件等能够被相册和其他应用发现使用
 *  场景：拍照之后，能够被其他应用QQ等选取发送
 *  invoke the system's media scanner to add your photo to the Media Provider's database,
 *  making it available in the Android Gallery application and to other apps.
 */
@Deprecated("maybe,it does not work on some devices")
fun Context.mediaScannerScanFile(path: String) {
    val f = File(path)
    mediaScannerScanFile(Uri.fromFile(f))
}

/**
 *  让系统媒体扫描器扫描指定路径的文件，确保生成的图片文件等能够被相册和其他应用发现使用
 *  场景：拍照之后，能够被其他应用QQ等选取发送
 *  @see [mediaScannerScanFile]
 *  invoke the system's media scanner to add your photo to the Media Provider's database,
 *  making it available in the Android Gallery application and to other apps.
 */
@BestPerformance("invoke the system's media scanner to add your photo to the Media Provider's database,\n" +
        "making it available in the Android Gallery application and to other apps.")
@Deprecated("maybe,it does not work on some devices")
fun Context.mediaScannerScanFile(uri: Uri) {
    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
    sendBroadcast(mediaScanIntent)
}

/**
 * 扫描指定媒体文件
 */
fun Context.mediaScannerScanFile(file: File, callback: MediaScannerConnection.OnScanCompletedListener?) {
    val path = file.absolutePath
    val mimeType = file.getMimeType()
    mediaScannerScanFile(arrayOf(path), arrayOf(mimeType), callback)
}

/**
 * 扫描指定媒体文件
 */
fun Context.mediaScannerScanFile(path: String, callback: MediaScannerConnection.OnScanCompletedListener?) {
    val mimeType = path.getMimeType()
    mediaScannerScanFile(arrayOf(path), arrayOf(mimeType), callback)
}

/**
 * 扫描指定媒体文件
 */
fun Context.mediaScannerScanFile(paths: Array<String>, mimeTypes: Array<String>?, callback: MediaScannerConnection.OnScanCompletedListener?) {
    MediaScannerConnection.scanFile(this, paths, mimeTypes, callback)
}


/**
 * 关于打开一个intent（特别是隐式启动或者打开系统等非app内置intent），最好是调用resolveActivity确保app不会崩溃
 */
class ImageCaptureHelper {

    /**
     * 构造器
     */
    class Builder {

        private var dirFile: File? = null
        private var fileName: String? = null
        private var fileSuffix: String? = null
        private var callback: ((String) -> Unit)? = null

        private var helperContext: HelperContext

        constructor(acty: Activity) {
            helperContext = object : HelperContext {
                override fun startActivityForResult(intent: Intent, requestCode: Int) {
                    acty.startActivityForResult(intent, requestCode)
                }

                override val ctx: Context
                    get() = acty
            }
        }

        constructor(fragment: Fragment) {
            helperContext = object : HelperContext {
                override fun startActivityForResult(intent: Intent, requestCode: Int) {
                    fragment.startActivityForResult(intent, requestCode)
                }

                override val ctx: Context
                    get() = fragment.activity
            }
        }

        constructor(fragment: android.support.v4.app.Fragment) {
            helperContext = object : HelperContext {
                override fun startActivityForResult(intent: Intent, requestCode: Int) {
                    fragment.startActivityForResult(intent, requestCode)
                }

                override val ctx: Context
                    get() = fragment.context
            }
        }

        /**
         * 设置回调
         */
        fun setCallback(callback: (String) -> Unit): Builder {
            this.callback = callback
            return this
        }

        /**
         * 设置回调
         */
        fun setCallback(listener: OnImageCaptureListener): Builder {
            this.callback = listener::onImageCaptureResult
            return this
        }

        /**
         * 设置目录
         * 默认规则：优先外置存储Picture目录 -> 外置App私有共享目录
         */
        fun setStorageDirectory(dir: File): Builder {
            dirFile = dir
            return this
        }

        /**
         * 设置文件名字，默认规则： "IMG{yyyyMMddHHmmss}"
         * 其中n为Math.randomIntInternal()随机得到的转换成非负数
         */
        fun setFileName(fileName: String): Builder {
            this.fileName = fileName
            return this
        }

        /**
         * 设置文件后缀，默认".jpg"
         */
        fun setSuffix(suffix: String): Builder {
            this.fileSuffix = suffix
            return this
        }

        fun build(): ImageCaptureHelper {
            return ImageCaptureHelper(helperContext, callback ?: {}, dirFile, fileName, fileSuffix)
        }
    }

    private val TAG = ImageCaptureHelper::class.java.simpleName

    private var helperCtx: HelperContext
    private var callback: (String) -> Unit
    private var suffix: String
    private var storageDir: File
    private var fileName: String?

    private var requestCode = 1
    private val KEY_CAPTURE_REQUEST_CODE = "capture_request_code"
    private val KEY_CAPTURED_PHOTO_PATH = "captured_photo_path"

    /**
     * @param ctx 帮助器上下文，主要是为了供Activity or Fragment不同的场景使用
     * @param callback 回调
     * @param storageDir 拍照存储路径，如果为空，则使用默认规则如果外置存储可用，则使用[Environment.getExternalStoragePublicDirectory],并且type＝Environment.DIRECTORY_DCIM
     * 默认完整规则为[Environment.getExternalStoragePublicDirectory]/Environment.DIRECTORY_DCIM/Camera
     * 否则使用[Context.getFilesDir]作为存储路径
     *
     */
    private constructor(ctx: HelperContext,
                        callback: (String) -> Unit,
                        storageDir: File? = null,
                        fileName: String? = null,
                        suffix: String? = null) {
        this.helperCtx = ctx
        this.callback = callback
        this.storageDir = storageDir.orDefault {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera")
            } else {
                this.helperCtx.ctx.filesDir
            }
        }

        this.fileName = fileName
        this.suffix = suffix.orDefaultIfNullOrEmpty(".jpg")
    }

    /**
     * 当前拍照图片路径
     */
    var currentPhotoFile: String = ""
        private set

    fun onSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (!currentPhotoFile.isNullOrEmpty()) {
                savedInstanceState.putString(KEY_CAPTURED_PHOTO_PATH, currentPhotoFile)
            }
            savedInstanceState.putInt(KEY_CAPTURE_REQUEST_CODE, requestCode)
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (savedInstanceState.containsKey(KEY_CAPTURED_PHOTO_PATH)) {
                currentPhotoFile = savedInstanceState.getString(KEY_CAPTURED_PHOTO_PATH)
            }

            if (savedInstanceState.containsKey(KEY_CAPTURE_REQUEST_CODE)) {
                requestCode = savedInstanceState.getInt(KEY_CAPTURE_REQUEST_CODE, 1)
            }
        }
    }

    /**
     * 发送拍照意图
     */
    @Throws(ActivityNotFoundException::class, IOException::class)
    fun dispatchTakePictureIntent(requestCode: Int) {
        this.requestCode = requestCode
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        //Ensure that there's a camera activity to handle the intent
        @BestPerformance("Performing this check is important because if you call startActivityForResult() using an intent that no app can handle, your app will crash.")
        if (takePictureIntent.resolveActivity(helperCtx.ctx.packageManager) != null) {
            // Create the File where the photo should go
            val photoFile: File = createImageFile()
            // Continue only if the File was successfully created
            val photoURI: Uri = Uri.fromFile(photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            helperCtx.startActivityForResult(takePictureIntent, this.requestCode)
        } else {
            throw ActivityNotFoundException("activity not found for ${MediaStore.ACTION_IMAGE_CAPTURE}")
        }
    }

    /**
     * 创建图片文件
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        //如果目录未创建，则创建目录
        if (!this.storageDir.exists()) {
            this.storageDir.mkdirs()
        }

        val imageFile: File
        //如果配置了文件名字
        if (!fileName.isNullOrEmpty()) {
            imageFile = File(storageDir, fileName + suffix)
//            //如果指定的文件已经存在，则先删除文件
//            if (imageFile.exists()) {
//                imageFile.delete()
//            }
        } else {
            // Create an image file name
            val imageFileName = "IMG${SimpleDateFormat("yyyyMMddHHmmss").format(Date())}"
            imageFile = File(storageDir, imageFileName + suffix)
            Log.d(TAG, "拍照存储文件路径：${imageFile.absolutePath}")
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoFile = imageFile.absolutePath
        return imageFile
    }

    /**
     * 拍照结果返回处理
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (this.requestCode == requestCode) {
            if (currentPhotoFile.isNullOrEmpty())
                return

            if (resultCode == RESULT_OK) {
                helperCtx.ctx.applicationContext.mediaScannerScanFile(arrayOf(currentPhotoFile), null, MediaScannerConnection.OnScanCompletedListener { path, uri ->
                    Log.d(TAG, "拍照存储文件扫描结束：path=$path uri=$uri")
                })
                callback(currentPhotoFile)
            } else {//取消拍照，资源重置
                val file = File(currentPhotoFile)
                if (file.exists()) {
                    file.delete()
                    Log.d(TAG, "取消拍照，删除cache文件")
                }
                currentPhotoFile = ""
            }
        }
    }

    /**
     * 帮助类上下文
     */
    private interface HelperContext {
        fun startActivityForResult(intent: Intent, requestCode: Int)

        val ctx: Context
    }

    /**
     * 拍照结果返回
     */
    interface OnImageCaptureListener {
        fun onImageCaptureResult(path: String)
    }

}