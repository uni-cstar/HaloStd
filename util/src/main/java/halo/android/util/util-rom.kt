//package halo.android.util
//
//import android.os.Environment
//import android.support.annotation.IntDef
//import halo.kotlin.util.Matcher
//import java.io.File
//import java.io.FileInputStream
//import java.util.*
//
//
///**
// * Created by Lucio on 18/4/18.
// */
//
//object RomUtil {
//
//    // 小米 : MIUI
//    private const val KEY_MIUI_VERSION = "ro.build.version.incremental" // "7.6.15"
//    private const val KEY_MIUI_VERSION_NANE = "ro.miui.ui.version.name" // "V8"
//    private const val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code" // "6"
//    private const val VALUE_MIUI_CLIENT_ID_BASE = "android-xiaomi"
//    // 华为 : EMUI
//    private const val KEY_EMUI_VERSION = "ro.build.version.emui" // "EmotionUI_3.0"
//    private const val KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level" //
//    private const val KEY_EMUI_SYSTEM_VERSION = "ro.confg.hw_systemversion" // "T1-A21wV100R001C233B008_SYSIMG"
//    // 魅族 : Flyme
//    private const val KEY_FLYME_PUBLISHED = "ro.flyme.published" // "true"
//    private const val KEY_FLYME_SETUP = "ro.meizu.setupwizard.flyme" // "true"
//
//    private const val VALUE_FLYME_DISPLAY_ID_CONTAIN = "Flyme" // "Flyme OS 4.5.4.2U"
//    // OPPO : ColorOS
//    private const val KEY_COLOROS_VERSION = "ro.oppo.theme.version" // "703"
//    private const val KEY_COLOROS_THEME_VERSION = "ro.oppo.version" // ""
//    private const val KEY_COLOROS_ROM_VERSION = "ro.rom.different.version" // "ColorOS2.1"
//
//    private const val VALUE_COLOROS_BASE_OS_VERSION_CONTAIN = "OPPO" // "OPPO/R7sm/R7sm:5.1.1/LMY47V/1440928800:user/release-keys"
//    private const val VALUE_COLOROS_CLIENT_ID_BASE = "android-oppo"
//    // vivo : FuntouchOS
//    private const val KEY_FUNTOUCHOS_OS_NAME = "ro.vivo.os.name" // "Funtouch"
//    private const val KEY_FUNTOUCHOS_OS_VERSION = "ro.vivo.os.version" // "3.0"
//    private const val KEY_FUNTOUCHOS_DISPLAY_ID = "ro.vivo.os.build.display.id" // "FuntouchOS_3.0"
//    private const val KEY_FUNTOUCHOS_ROM_VERSION = "ro.vivo.rom.version" // "rom_3.1"
//
//    private const val VALUE_FUNTOUCHOS_CLIENT_ID_BASE = "android-vivo"
//    // Samsung
//    private const val VALUE_SAMSUNG_BASE_OS_VERSION_CONTAIN = "samsung" // "samsung/zeroltezc/zeroltechn:6.0.1/MMB29K/G9250ZCU2DQD1:user/release-keys"
//    private const val VALUE_SAMSUNG_CLIENT_ID_BASE = "android-samsung"
//    // Sony
//    private const val KEY_SONY_PROTOCOL_TYPE = "ro.sony.irremote.protocol_type" // "2"
//    private const val KEY_SONY_ENCRYPTED_DATA = "ro.sony.fota.encrypteddata" // "supported"
//
//    private const val VALUE_SONY_CLIENT_ID_BASE = "android-sonyericsson"
//    // 乐视 : eui
//    private const val KEY_EUI_VERSION = "ro.letv.release.version" // "5.9.023S"
//    private const val KEY_EUI_VERSION_DATE = "ro.letv.release.version_date" // "5.9.023S_03111"
//    private const val KEY_EUI_NAME = "ro.product.letv_name" // "乐1s"
//    private const val KEY_EUI_MODEL = "ro.product.letv_model" // "Letv X500"
//    // 金立 : amigo
//    private const val KEY_AMIGO_ROM_VERSION = "ro.gn.gnromvernumber" // "GIONEE ROM5.0.16"
//    private const val KEY_AMIGO_SYSTEM_UI_SUPPORT = "ro.gn.amigo.systemui.support" // "yes"
//
//    private const val VALUE_AMIGO_DISPLAY_ID_CONTAIN = "amigo" // "amigo3.5.1"
//    private const val VALUE_AMIGO_CLIENT_ID_BASE = "android-gionee"
//    // 酷派 : yulong
//    private const val KEY_YULONG_VERSION_RELEASE = "ro.yulong.version.release" // "5.1.046.P1.150921.8676_M01"
//    private const val KEY_YULONG_VERSION_TAG = "ro.yulong.version.tag" // "LC"
//
//    private const val VALUE_YULONG_CLIENT_ID_BASE = "android-coolpad"
//    // HTC : Sense
//    private const val KEY_SENSE_BUILD_STAGE = "htc.build.stage" // "2"
//    private const val KEY_SENSE_BLUETOOTH_SAP = "ro.htc.bluetooth.sap" // "true"
//
//    private const val VALUE_SENSE_CLIENT_ID_BASE = "android-htc-rev"
//    // LG : LG
//    private const val KEY_LG_SW_VERSION = "ro.lge.swversion" // "D85720b"
//    private const val KEY_LG_SW_VERSION_SHORT = "ro.lge.swversion_short" // "V20b"
//    private const val KEY_LG_FACTORY_VERSION = "ro.lge.factoryversion" // "LGD857AT-00-V20b-CUO-CN-FEB-17-2015+0"
//    // 联想
//    private const val KEY_LENOVO_DEVICE = "ro.lenovo.device" // "phone"
//    private const val KEY_LENOVO_PLATFORM = "ro.lenovo.platform" // "qualcomm"
//    private const val KEY_LENOVO_ADB = "ro.lenovo.adb" // "apkctl,speedup"
//
//    private const val VALUE_LENOVO_CLIENT_ID_BASE = "android-lenovo"
//
//    lateinit var rom: Rom
//        private set
//
//    init {
//        var fis: FileInputStream? = null
//        try {
//            val buildProperties = Properties()
//            fis = FileInputStream(File(Environment.getRootDirectory(), "build.prop"))
//            buildProperties.load(fis)
//            if (isMIUI(buildProperties)) {
//                rom = createMIUIRomBean(buildProperties)
//            } else if (isFuntouch(buildProperties)) {
//                rom = createFuntouchRomBean(buildProperties)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            try {
//                fis?.close()
//            } catch (e: Exception) {
//            }
//        }
//    }
//
//    //是否是小米操作系统
//    private inline fun isMIUI(props: Properties): Boolean {
//        return props.containsKey(KEY_MIUI_VERSION_NANE) || props.containsKey(KEY_MIUI_VERSION_CODE)
//    }
//
//    //创建小米ROM Bean
//    private inline fun createMIUIRomBean(props: Properties): Rom {
//        val rom = Rom(ROM_TYPE_MIUI)
//        if (props.containsKey(KEY_MIUI_VERSION_NANE)) {
//            rom.versionName = props.getProperty(KEY_MIUI_VERSION_NANE)// V8
//        }
//        if (props.containsKey(KEY_MIUI_VERSION)) {
//            val versionStr = props.getProperty(KEY_MIUI_VERSION)
//            if (!versionStr.isNullOrEmpty() && Matcher.isVersionNumber(versionStr)) {
//                rom.versionCode = versionStr
//            }
//        }
//        return rom
//    }
//
//    //是否是华为操作系统
//    private inline fun isEMUI(props: Properties):Boolean{
//
//    }
//    /**
//     * 是否是vivo 操作系统
//     */
//    private inline fun isFuntouch(props: Properties): Boolean {
//        return props.containsKey(KEY_FUNTOUCHOS_OS_NAME)
//                || props.containsKey(KEY_FUNTOUCHOS_OS_VERSION)
//                || props.containsKey(KEY_FUNTOUCHOS_DISPLAY_ID)
//    }
//
//    /**
//     * 创建vivo ROM Bean
//     */
//    private inline fun createFuntouchRomBean(props: Properties): Rom {
//        val rom = Rom(ROM_TYPE_FuntouchOS)
//        if (props.containsKey(KEY_FUNTOUCHOS_OS_VERSION)) {
//            val osVersion = props.getProperty(KEY_FUNTOUCHOS_OS_VERSION)// 2.5
//            rom.versionName = osVersion
//            if (!osVersion.isNullOrEmpty() && Matcher.isVersionNumber(osVersion)) {
//                rom.versionCode = osVersion
//            }
//        } else if (props.containsKey(KEY_FUNTOUCHOS_ROM_VERSION)) {
//            val romVersion = props.getProperty(KEY_FUNTOUCHOS_ROM_VERSION)// rom_2.5
//            val versions = romVersion.split("_")
//            for (version in versions) {
//                if (!version.isEmpty() && Matcher.isVersionNumber(version)) {
//                    rom.versionName = version
//                    rom.versionCode = version
//                    break
//                }
//            }
//        }
//        return rom
//    }
//}
//
//
//class Rom(@RomType val type: Long) {
//
//    var versionName: String = "1.0"
//        internal set
//
//    var versionCode: String = "1.0"
//        internal set
//}
//
//const val ROM_TYPE_UNKNOW = 0L
//
////小米
//const val ROM_TYPE_MIUI = 1L
//
////华为
//const val ROM_TYPE_EMUI = 2L
//
////vivo
//const val ROM_TYPE_FuntouchOS = 3L
//
////OPPO
//const val ROM_TYPE_ColorOS = 4L
//
////魅族
//const val ROM_TYPE_FLYME = 5L
//
////三星
//const val ROM_TYPE_SamSung = 6L
//
//
//@IntDef(ROM_TYPE_UNKNOW, ROM_TYPE_MIUI, ROM_TYPE_EMUI, ROM_TYPE_FuntouchOS, ROM_TYPE_ColorOS, ROM_TYPE_FLYME, ROM_TYPE_SamSung)
//@Retention(AnnotationRetention.BINARY)
//annotation class RomType
//
