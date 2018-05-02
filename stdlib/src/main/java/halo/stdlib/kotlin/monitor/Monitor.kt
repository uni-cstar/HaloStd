package halo.stdlib.kotlin.monitor


object Monitor {

    @JvmField
    var enableTimeMonitor = true

    @JvmStatic
    fun newTimeMonitor(): TimeMonitor {
        return TimeMonitorImpl()

    }
}