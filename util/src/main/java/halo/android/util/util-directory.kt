package halo.android.util

/**
 * Created by Lucio on 18/1/15.
 */


/**
 * [Context.getExternalFilesDir]
 * 获取属于app的私有的共享的外置文件目录，不能作为媒体文件夹对用户可见，在app被删除时，此目录会一并被删除。
 *
 * 备注：共享目录并不总是可用，因为可能被用户弹出，可以使用[Environment.getExternalStorageState]检查状态。
 * 也并没有什么安全性的强制措施使用这个文件夹，因为任何拥有[android.Manifest.permission.WRITE_EXTERNAL_STORAGE]权限
 * 的app是能够读写这些文件的。
 *
 * [android.os.Build.VERSION_CODES.KITKAT]4.4及之后，使用这个目录是不需要任何权限的，它总是对调用的app可用。
 * 不过这仅仅对使用app包名生成的路径文件夹适用，对于访问这个路径下的其他文件夹，也是需要[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] 或[android.Manifest.permission.READ_EXTERNAL_STORAGE]权限的
 * 换句话说：[Context.getExternalFilesDir]应该是在外置存储中使用当前应用的包名生成了一个文件夹，app直接访问这个文件夹，在4.4之后是不需要
 * 任何权限就可以直接访问的（在4.4之前还是需要权限），但是对包名这个文件夹下的子目录进行访问，还是需要权限的。（不知道这么理解是否正确？）
 *
 * 在具有多个用户的设备上（如[UserManager]}所述）,每个用户都有自己独立的共享存储。应用程序只有访问他们正在运行的用户的共享存储。
 *
 * The returned path may change over time if different shared storage media
 * is inserted, so only relative paths should be persisted.
 *
 * Here is an example of typical code to manipulate a file in an
 * application's shared storage:
 *
 * {@sample development/samples/ApiDemos/src/com/example/android/apis/content/ExternalStorage.java
 * * private_file}
 *
 *
 * If you supply a non-null <var>type</var> to this function, the returned
 * file will be a path to a sub-directory of the given type. Though these
 * files are not automatically scanned by the media scanner, you can
 * explicitly add them to the media database with
 * [ MediaScannerConnection.scanFile][android.media.MediaScannerConnection.scanFile]. Note that this is not the same as
 * [ Environment.getExternalStoragePublicDirectory()][android.os.Environment.getExternalStoragePublicDirectory], which provides
 * directories of media shared by all applications. The directories returned
 * here are owned by the application, and their contents will be removed
 * when the application is uninstalled. Unlike
 * [ Environment.getExternalStoragePublicDirectory()][android.os.Environment.getExternalStoragePublicDirectory], the directory returned
 * here will be automatically created for you.
 *
 *
 * Here is an example of typical code to manipulate a picture in an
 * application's shared storage and add it to the media database:
 *
 * {@sample development/samples/ApiDemos/src/com/example/android/apis/content/ExternalStorage.java
 * * private_picture}

 * @param type The type of files directory to return. May be `null`
 * *            for the root of the files directory or one of the following
 * *            constants for a subdirectory:
 * *            [android.os.Environment.DIRECTORY_MUSIC],
 * *            [android.os.Environment.DIRECTORY_PODCASTS],
 * *            [android.os.Environment.DIRECTORY_RINGTONES],
 * *            [android.os.Environment.DIRECTORY_ALARMS],
 * *            [android.os.Environment.DIRECTORY_NOTIFICATIONS],
 * *            [android.os.Environment.DIRECTORY_PICTURES], or
 * *            [android.os.Environment.DIRECTORY_MOVIES].
 * *
 * @return the absolute path to application-specific directory. May return
 * *         `null` if shared storage is not currently available.
 * *
 * @see .getFilesDir

 * @see .getExternalFilesDirs
 * @see Environment.getExternalStorageState
 * @see Environment.isExternalStorageEmulated
 * @see Environment.isExternalStorageRemovable
 */


//Android系统文件目录路径说明
//系统数据存储路径，如下：其中应用程序包名为：com.spt
//
//ContextWrapper类中，包含以下方法：
//
//1. getFilesDir() --> 内部存储
//
//@Override
//public File getFilesDir() {
//    return mBase.getFilesDir();
//}
//k86m_QC机器上数据存储路径：/data/data/com.spt/files
//
//华为手机上数据存储路径：/data/data/com.spt/files
//
//2. getExternalFilesDir(String type) 参数指定为：Environment.DIRECTORY_PICTURES --> 外部存储
//@Override
//public File getExternalFilesDir(String type) {
//    return mBase.getExternalFilesDir(type);
//}
//k86m_QC机器上数据存储路径：/storage/sdcard0/Android/data/com.spt/files/Pictures
//
//华为手机上数据存储路径：/storage/emulated/0/Android/data/com.spt/files/Pictures
//
//3. getCacheDir() --> 内部存储
//@Override
//public File getCacheDir() {
//    return mBase.getCacheDir();
//}
//k86m_QC机器上数据存储路径：/data/data/com.spt/cache
//
//华为手机上数据存储路径：/data/data/com.spt/cache
//
//4. getExternalCacheDir() --> 外部存储
//@Override
//public File getExternalCacheDir() {
//    return mBase.getExternalCacheDir();
//}
//k86m_QC机器上数据存储路径：/storage/sdcard0/Android/data/com.spt/cache
//
//华为手机上数据存储路径：/storage/emulated/0/Android/data/com.spt/cache
//
//
//
//Environment类中，包含以下方法：
//
//1. getDataDirctory()
//
///**
// * Return the user data directory.
// */
//public static File getDataDirectory() {
//    return DATA_DIRECTORY;
//}
//k86m_QC机器上数据存储路径：/data
//
//华为手机上数据存储路径：/data
//
//2. getDownLoadCacheDirectory()
//
///**
// * Return the download/cache content directory.
// */
//public static File getDownloadCacheDirectory() {
//    return DOWNLOAD_CACHE_DIRECTORY;
//}
//k86m_QC机器上数据存储路径：/cache
//
//华为手机上数据存储路径：/cache
//
//3. getExternalStorageDirectory()
//
///**
// * Return the primary external storage directory. This directory may not
// * currently be accessible if it has been mounted by the user on their
// * computer, has been removed from the device, or some other problem has
// * happened. You can determine its current state with
// * {@link #getExternalStorageState()}.
// * <p>
// * <em>Note: don't be confused by the word "external" here. This directory
// * can better be thought as media/shared storage. It is a filesystem that
// * can hold a relatively large amount of data and that is shared across all
// * applications (does not enforce permissions). Traditionally this is an SD
// * card, but it may also be implemented as built-in storage in a device that
// * is distinct from the protected internal storage and can be mounted as a
// * filesystem on a computer.</em>
// * <p>
// * On devices with multiple users (as described by {@link UserManager}),
// * each user has their own isolated external storage. Applications only have
// * access to the external storage for the user they're running as.
// * <p>
// * In devices with multiple "external" storage directories, this directory
// * represents the "primary" external storage that the user will interact
// * with. Access to secondary storage is available through
// * <p>
// * Applications should not directly use this top-level directory, in order
// * to avoid polluting the user's root namespace. Any files that are private
// * to the application should be placed in a directory returned by
// * {@link android.content.Context#getExternalFilesDir
// * Context.getExternalFilesDir}, which the system will take care of deleting
// * if the application is uninstalled. Other shared files should be placed in
// * one of the directories returned by
// * {@link #getExternalStoragePublicDirectory}.
// * <p>
// * Writing to this path requires the
// * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} permission,
// * and starting in read access requires the
// * {@link android.Manifest.permission#READ_EXTERNAL_STORAGE} permission,
// * which is automatically granted if you hold the write permission.
// * <p>
// * Starting in {@link android.os.Build.VERSION_CODES#KITKAT}, if your
// * application only needs to store internal data, consider using
// * {@link Context#getExternalFilesDir(String)} or
// * {@link Context#getExternalCacheDir()}, which require no permissions to
// * read or write.
// * <p>
// * This path may change between platform versions, so applications should
// * only persist relative paths.
// * <p>
// * Here is an example of typical code to monitor the state of external
// * storage:
// * <p>
// * {@sample
// * development/samples/ApiDemos/src/com/example/android/apis/content/ExternalStorage.java
// * monitor_storage}
// *
// * @see #getExternalStorageState()
// * @see #isExternalStorageRemovable()
// */
//public static File getExternalStorageDirectory() {
//    throwIfUserRequired();
//    return sCurrentUser.getExternalDirsForApp()[0];
//}
//k86m_QC机器上数据存储路径：/storage/sdcard0
//
//华为手机上数据存储路径：/storage/emulated/0
//
//4. getRootDirectory()
//
///**
// * Return root of the "system" partition holding the core Android OS.
// * Always present and mounted read-only.
// */
//public static File getRootDirectory() {
//    return DIR_ANDROID_ROOT;
//}
//k86m_QC机器上数据存储路径：/system
//
//华为手机上数据存储路径：/system
//
//5. getExternalStoragePublicDirectory(String type)
//
///**
// * Get a top-level public external storage directory for placing files of
// * a particular type.  This is where the user will typically place and
// * manage their own files, so you should be careful about what you put here
// * to ensure you don't erase their files or get in the way of their own
// * organization.
// *
// * <p>On devices with multiple users (as described by {@link UserManager}),
// * each user has their own isolated external storage. Applications only
// * have access to the external storage for the user they're running as.</p>
// *
// * <p>Here is an example of typical code to manipulate a picture on
// * the public external storage:</p>
// *
// * {@sample development/samples/ApiDemos/src/com/example/android/apis/content/ExternalStorage.java
// * public_picture}
// *
// * @param type The type of storage directory to return.  Should be one of
// * {@link #DIRECTORY_MUSIC}, {@link #DIRECTORY_PODCASTS},
// * {@link #DIRECTORY_RINGTONES}, {@link #DIRECTORY_ALARMS},
// * {@link #DIRECTORY_NOTIFICATIONS}, {@link #DIRECTORY_PICTURES},
// * {@link #DIRECTORY_MOVIES}, {@link #DIRECTORY_DOWNLOADS}, or
// * {@link #DIRECTORY_DCIM}.  May not be null.
// *
// * @return Returns the File path for the directory.  Note that this
// * directory may not yet exist, so you must make sure it exists before
// * using it such as with {@link File#mkdirs File.mkdirs()}.
// */
//public static File getExternalStoragePublicDirectory(String type) {
//    throwIfUserRequired();
//    return sCurrentUser.buildExternalStoragePublicDirs(type)[0];
//}
//k86m_QC机器上数据存储路径：/storage/sdcard0/Pictures
//
//华为手机上数据存储路径：/storage/emulated/0/Pictures
//
//Internal Storage和External Storage的区别：
//
//
//
//getFilesDir() --> 内部存储 /data/data/com.spt/files
//
//getCacheDir() --> 内部存储 /data/data/com.spt/cache
//
//内部存储，对应的是特定的应用程序，如上所述指的是包名为：com.spt应用程序
//
//getExternalFilesDir(String type) --> 外部存储 /storage/sdcard0/Android/data/com.spt/files/Pictures
//
//getExternalCacheDir() --> 外部存储 /storage/sdcard0/Android/data/com.spt/cache
//
//getExternalStoragePublicDirectory(String type) --> 外部存储 /storage/sdcard0/Pictures
//
//getExternalStorageDirectory() --> 外部存储 /storage/sdcard0
//
//1. 外部存储，对应的是/storage/sdcard0/目录；
//
//2. private files：如果需要在卸载应用程序时，删除所有该应用程序的外部存储（同时，该数据是本应用程序私有的），可以使用：getExternalFilesDir(String type)目录，带有应用程序包名；
//
//
//
//3. public files可以存放在：getExternalStoragePublicDirectory(String type)
//
//P.S.
//
//对于特定的智能后视镜设备：Flash --> /mnt/sdcard 硬盘大小 外部存储路径:/storage/sdcard1" 外设的存储设备