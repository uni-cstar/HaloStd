package halo.android.integration.glide

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import halo.android.integration.ImageLoader
import org.jetbrains.annotations.NotNull
import java.io.File

/**
 * Created by Lucio on 17/12/8.
 */

class ImageLoaderImpl : ImageLoader {

    val DEFAULT_REQUEST_OPTIONS = RequestOptions()

    override fun load(@NotNull context: Context?, url: String): RequestBuilder<File> {
        return Glide.with(context)
                .download(url)
    }

    override fun load(fragment: Fragment, imageView: ImageView, url: String,
                      requestOptions: RequestOptions) {
        Glide.with(fragment)
                .load(url)
                .apply(requestOptions)
                .into(imageView)
    }

    override fun load(fragment: android.app.Fragment, imageView: ImageView, url: String) {

    }

    override fun load(activity: Activity, imageView: ImageView, url: String) {
    }

    fun load(fragment: android.app.Fragment, imageView: ImageView, url: String,
             requestOptions: RequestOptions = DEFAULT_REQUEST_OPTIONS) {
        load(fragment, imageView, url, requestOptions)
    }

    fun load(activity: Activity, imageView: ImageView, url: String,
             requestOptions: RequestOptions = DEFAULT_REQUEST_OPTIONS) {
        Glide.with(activity)
                .load(url)
                .apply(requestOptions)
                .into(imageView)
    }

    override fun load(activity: FragmentActivity, imageView: ImageView, url: String,
                      requestOptions: RequestOptions) {
        load(activity, imageView, url, requestOptions)
    }

    /**
     * 虽然即使取消不必要的加载是个很好的实践。
     * 但是这并不是必须的，当 Glide.with() 中传入的 Activity 或 Fragment 实例销毁时，Glide 会自动取消加载并回收资源。
     * 值得注意的是：
     */
    override fun clear(activity: Activity, view: View) {
        Glide.with(activity).clear(view)

    }

    override fun clear(activity: FragmentActivity, view: View) {
        Glide.with(activity).clear(view)
    }

    override fun clear(fragment: Fragment, view: View) {
        Glide.with(fragment).clear(view)
    }

    override fun clear(fragment: android.app.Fragment, view: View) {
        Glide.with(fragment).clear(view)
    }
}
