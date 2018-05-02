package halo.kotlin

import java.util.concurrent.locks.Lock

/**
 * Created by Lucio on 17/11/23.
 */

inline fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}