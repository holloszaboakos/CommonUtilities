package hu.raven.puppet.example

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

fun main() {
    val lock = ReentrantReadWriteLock()
    lock.write {
        lock.read {
            lock.write {
                lock.read {
                    println("works")
                }
            }
        }
    }
}