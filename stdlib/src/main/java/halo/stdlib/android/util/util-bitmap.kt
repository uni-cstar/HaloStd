package halo.stdlib.android.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Base64
import halo.BuildConfig
import halo.stdlib.kotlin.util.Try
import java.io.ByteArrayOutputStream

/**
 * 生成文字图片
 * @param texString 文本内容
 * @param textSize 文本大小
 * @param textColor 文本颜色
 */
fun createTextBitmap(texString: String,
                     textSize: Float,
                     @ColorInt textColor: Int): Bitmap? {
    try {
        val replyPaint = TextPaint()
        replyPaint.isAntiAlias = true
        replyPaint.textSize = textSize
        replyPaint.color = textColor

        // 获取文本宽高
        val textHeight = replyPaint.getTextHeight() + 2
        val textWidth = replyPaint.measureText(texString).toInt() + 6

        return createTextBitmap(textWidth, textHeight, texString, replyPaint)
    } catch (e: Exception) {
        return null
    }
}

/**
 * 生成文字图片
 * @param width 图片宽度
 * @param height 图片高度
 * @param text 图片内容
 * @param paint 文字画笔
 * @param config 图片设置
 */
@JvmOverloads
fun createTextBitmap(width: Int, height: Int, text: String, paint: TextPaint, config: Bitmap.Config = Bitmap.Config.ARGB_4444): Bitmap {
    val newBitmap = Bitmap.createBitmap(width, height, config)
    val canvas = Canvas(newBitmap)
    val sl = StaticLayout(text, paint, newBitmap.width,
            Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false)
    sl.draw(canvas)
    return newBitmap
}

/**
 * 缩放图片
 * @param targetW 目标宽度
 * @param targetH 目标高度
 * @param imagePath 图片路径
 * @param config 压缩配置，默认[Bitmap.Config.RGB_565],所占内存最少
 */
fun scaleImage(targetW: Int, targetH: Int, imagePath: String,
               config: Bitmap.Config = Bitmap.Config.RGB_565): Bitmap {

    // Get the dimensions of the bitmap
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imagePath, bmOptions)
    val photoW = bmOptions.outWidth
    val photoH = bmOptions.outHeight

    // Determine how much to scale down the image
    val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

    // Decode the image file into a Bitmap sized to fill the View
    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = scaleFactor

    if (BuildConfig.VERSION_CODE < Build.VERSION_CODES.LOLLIPOP) {
        bmOptions.inPurgeable = true
        bmOptions.inInputShareable = true
    }
    bmOptions.inPreferredConfig = config
    return BitmapFactory.decodeFile(imagePath, bmOptions)
}

/**
 * 转换成字节数组
 * @param needRecycle 是否需要在转换之后回收bitmap,如果bitmap在转换之后不需要再使用，则建议设置为true
 * @param format      转换格式，默认JPEG，这样转换之后的图片不包含透明像素，所占空间会更小（如果jpeg图片使用PNG格式转换，会导致转换之后的空间更大）
 * @param quality     转换质量（0-100取值），默认80
 * @return
 */
fun Bitmap.toByteArray(needRecycle: Boolean,
                       format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
                       quality: Int = 80): ByteArray? {
    var output: ByteArrayOutputStream? = null
    var result: ByteArray? = null
    try {
        output = ByteArrayOutputStream()
        this.compress(format, quality, output)
        if (needRecycle) {
            this.recycle()
        }
        result = output.toByteArray()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        Try {
            output?.close()
        }
    }
    return result
}

/**
 * 转换成Base64编码Stringx
 * @param format      转换格式，默认JPEG，这样转换之后的图片不包含透明像素，所占空间会更小（如果jpeg图片使用PNG格式转换，会导致转换之后的空间更大）
 * @param quality     转换质量（0-100取值），默认80
 * @return
 */
fun Bitmap.toBase64String(format: Bitmap.CompressFormat, quality: Int): String {
    return Base64.encodeToString(this.toByteArray(false, format, quality), Base64.DEFAULT)
}

/**
 * 转换成Drawable
 */
fun Bitmap.toDrawable(res: Resources): BitmapDrawable {
    return BitmapDrawable(res, this)
}

/**
 * 混合logo(居中混合，小图放在大图中间)
 * 使用场景：二维码中间贴上logo
 * @param markBmp 小图
 * @param config 建议[Bitmap.Config.RGB_565],占用的内存更少
 * @return 混合之后的图片
 */
fun Bitmap.mixtureCenterBitmap(markBmp: Bitmap,
                               config: Bitmap.Config): Bitmap? {

    val sW = this.width
    val sH = this.height
    val mW = markBmp.width
    val mH = markBmp.height
    val newBmp = Bitmap.createBitmap(sW, sH, config)
    val cv = Canvas(newBmp)
    cv.drawBitmap(this, 0f, 0f, null)
    cv.drawBitmap(markBmp, (sW / 2 - mW / 2).toFloat(), (sH / 2 - mH / 2).toFloat(), null)
    cv.save(Canvas.ALL_SAVE_FLAG)
    cv.restore()
    return newBmp
}