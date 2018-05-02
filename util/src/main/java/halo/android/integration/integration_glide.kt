package halo.android.integration

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import halo.kotlin.BestPerformance
import org.jetbrains.annotations.NotNull
import java.io.File

/**
 * Created by Lucio on 18/1/10.
 */

/**
 * Created by Lucio on 17/12/8.
 */
interface ImageLoader {

    /**
     * Any requests started using a context will only have the application level options applied and will not be started or stopped based on lifecycle events.
     *  This method is appropriate for resources that will be used outside of the normal fragment or activity lifecycle
     * (For example in services, or for notification thumbnails).
     */
    @BestPerformance(message = "不建议使用，与生命周期没绑定，此方法使用场景在诸如Service ,or Notification thumbnails等")
    fun load(@NotNull context: Context?, url: String): RequestBuilder<File>

    fun load(fragment: android.app.Fragment, imageView: ImageView, url: String)

    fun load(fragment: Fragment, imageView: ImageView, url: String, requestOptions: RequestOptions)

    fun load(activity: Activity, imageView: ImageView, url: String)

    fun load(activity: FragmentActivity, imageView: ImageView, url: String, requestOptions: RequestOptions)

    /**
     * 虽然即使取消不必要的加载是个很好的实践。
     * 但是这并不是必须的，当 Glide.with() 中传入的 Activity 或 Fragment 实例销毁时，Glide 会自动取消加载并回收资源。
     * 值得注意的是：
     */
    fun clear(activity: Activity, view: View)

    fun clear(activity: FragmentActivity, view: View)

    fun clear(fragment: Fragment, view: View)

    fun clear(fragment: android.app.Fragment, view: View)
}