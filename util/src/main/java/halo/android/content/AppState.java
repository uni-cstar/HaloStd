package halo.android.content;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Lucio on 18/3/16.
 * 确定App是前台还是后台状态
 */
@TargetApi(14)
public class AppState implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = AppState.class.getSimpleName();

    /**
     * 当前app是否正在前台运行
     */
    private boolean isForeground = false;

    /**
     * 是否正在暂停（用于确定A 启动 B ，A进入pause ，B进入create等中间这段时间能够更正确的表述app状态）
     */
    private boolean isPausing = false;

    /**
     * 用于延迟时间，判断上述交替情况
     */
    private long stateCheckDelayTime = 500;

    private Handler handler = new Handler();
    private Runnable check;

    private List<OnAppStateChangedListener> listeners;

    private AppState() {
    }

    private static AppState instance;


    /**
     * 初始化单例对象
     *
     * @param app
     */
    public static void init(Application app) {
        if (instance == null) {
            instance = new AppState();
            app.registerActivityLifecycleCallbacks(instance);
        }
    }

    /**
     * 得到单例对象
     *
     * @return
     */
    public static AppState get() {
        return instance;
    }

    public interface OnAppStateChangedListener {
        void onAppBecameForeground();

        void onAppBecameBackground();
    }

    public void addListener(OnAppStateChangedListener listener) {
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<OnAppStateChangedListener>();
        }
        listeners.add(listener);
    }

    public void removeListener(OnAppStateChangedListener listener) {
        if (listeners != null)
            listeners.remove(listener);
    }

    /**
     * 当前app是否在前台运行
     *
     * @return
     */
    public boolean isForeground() {
        return isForeground;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        isPausing = false;
        boolean isBackground = !isForeground;
        isForeground = true;

        if (check != null) {
            handler.removeCallbacks(check);
        }

        if (isBackground) {
            Log.d(TAG, "background became foreground");
            if (listeners == null)
                return;
            for (OnAppStateChangedListener listener : listeners) {
                try {
                    listener.onAppBecameForeground();
                } catch (Exception e) {
                    Log.d(TAG, "listener throw exception", e);
                }
            }
        } else {
            Log.d(TAG, "still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        isPausing = true;
        if (check != null) {
            handler.removeCallbacks(check);
        }

        handler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if (isForeground && isPausing) {
                    isForeground = false;
                    Log.d(TAG, "became background");
                    if (listeners == null)
                        return;
                    for (OnAppStateChangedListener listener : listeners) {
                        try {
                            listener.onAppBecameBackground();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "listener throw exception!", e);
                        }
                    }
                } else {
                    Log.d(TAG, "still foreground");
                }
            }
        }, stateCheckDelayTime);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
