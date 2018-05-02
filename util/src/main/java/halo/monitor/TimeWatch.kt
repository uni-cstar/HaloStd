package halo.monitor

/**
 * Created by Lucio on 18/4/17.
 */

//class TimeWatch {
//
//    private final val start = System.currentTimeMillis()
//
//    fun elapsedTime(): Long {
//        return System.currentTimeMillis() - start
//    }
//
//    fun printElapsedTime(tag: String) {
//        System.out.println("$tag takes ${elapsedTime()} milliseconds")
//    }
//
//
//}

var enableTimeMonitor = true

inline fun Any.timeMonitor(tag: String = "function", func: () -> Unit) {
    val start = System.currentTimeMillis()
    func()
    if (enableTimeMonitor) {
        val end = System.currentTimeMillis() - start
        if (end > 500) {
            System.out.println("[TimeMonitor]: $tag takes ${end / 1000.0} seconds")
        } else {
            System.out.println("[TimeMonitor]: $tag takes ${end} milliseconds")
        }
    }
}
