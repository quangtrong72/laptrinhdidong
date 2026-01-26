package org.example.Bai4

import kotlinx.coroutines.*

// ===== HÀM TẠM NGƯNG =====
suspend fun getValue(): Double {
    delay(1000)
    return 3.14
}

suspend fun processValue() {
    val value = getValue()
    println("Giá trị sau xử lý: $value")
}

fun main() = runBlocking {

    // chạy coroutine chuẩn
    val job = launch {
        val output = getValue()
        println("Output trong coroutine: $output")
    }

    delay(500)
    job.cancel()

    // runBlocking
    val output = getValue()
    println("Output runBlocking: $output")

    // async / await
    val deferred = async { getValue() }
    println("Output async: ${deferred.await()}")

    // gọi hàm suspend khác
    processValue()

    // object
    DataProviderManager.printInfo()

    // try catch
    try {
        val x = 10 / 0
        println(x)
    } catch (exception: Exception) {
        println("Có lỗi xảy ra: ${exception.message}")
    }

    // enum
    val direction = Direction.NORTH
    when (direction) {
        Direction.NORTH -> println("Đi lên trên ⬆️")
        Direction.SOUTH -> println("Đi xuống dưới ⬇️")
        Direction.WEST -> println("Đi sang trái ⬅️")
        Direction.EAST -> println("Đi sang phải ➡️")
    }
}

// ===== OBJECT =====
object DataProviderManager {
    fun printInfo() {
        println("DataProviderManager đang hoạt động")
    }
}

// ===== ENUM =====
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
