
package com.example.Bai3

fun main() {

    // ===== SET (NHÓM) =====
    // Tạo một nhóm từ danh sách
    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    val setOfNumbers = numbers.toSet()
    println("Set từ list: $setOfNumbers")

    // Xác định một nhóm
    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 4, 5)

    // Phép toán trên nhóm
    println("Giao: ${set1.intersect(set2)}")   // 3
    println("Hợp: ${set1.union(set2)}")       // 1,2,3,4,5


    // ===== MAP (SƠ ĐỒ / TẬP HỢP KEY - VALUE) =====
    // Xác định một map có thể thay đổi
    val peopleAges = mutableMapOf<String, Int>(
        "Fred" to 30,
        "Ann" to 23
    )

    // Thêm / cập nhật giá trị trong map
    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51

    // Lặp qua map
    peopleAges.forEach {
        print("${it.key} là ${it.value}, ")
    }
    println()

    // Chuyển đổi từng phần tử trong map
    println(
        peopleAges.map { "${it.key} là ${it.value}" }
            .joinToString(", ")
    )

    // Lọc các phần tử trong map
    val filteredNames = peopleAges.filter { it.key.length < 4 }
    println("Tên ngắn hơn 4 ký tự: $filteredNames")


    // ===== CÁC PHÉP TOÁN KHÁC TRÊN LIST =====
    val words = listOf("about", "acute", "balloon", "best", "brief", "class")
    val filteredWords = words.filter { it.startsWith("b", ignoreCase = true) }
        .shuffled()
        .take(2)
        .sorted()

    println("Từ lọc ra: $filteredWords")


    // ===== HÀM PHẠM VI (SCOPE FUNCTIONS) =====
    // let
    var arguments: String? = "ABC"
    var letterId = ""

    arguments?.let {
        letterId = it
    }
    println("letterId = $letterId")

    // apply (ví dụ mô phỏng)
    val person = Person().apply {
        name = "Anh Yêu"
        age = 20
    }
    println("Person: ${person.name} - ${person.age}")


    // ===== THUỘC TÍNH DỰ PHÒNG (BACKING PROPERTY) =====
    val game = Game()
    println("Từ hiện tại: ${game.currentScrambledWord}")


    // ===== LỆNH GỌI AN TOÀN =====
    val text: String? = null
    val length = text?.length
    println("Độ dài chuỗi (an toàn): $length")


    // ===== HÀM LAMBDA =====
    val triple: (Int) -> Int = { a: Int -> a * 3 }
    println("Triple 5 = ${triple(5)}")


    // ===== COMPANION OBJECT =====
    println("Hằng số LETTER: ${DetailActivity.LETTER}")


    // ===== ỦY QUYỀN THUỘC TÍNH (MINH HỌA) =====
    val viewModel: GameViewModel by lazy {
        GameViewModel()
    }
    println("ViewModel đã khởi tạo: $viewModel")


    // ===== KHỞI TẠO TRỄ (LATEINIT) =====
    val manager = WordManager()
    manager.currentWord = "Kotlin"
    println("Từ hiện tại: ${manager.currentWord}")


    // ===== TOÁN TỬ ELVIS =====
    var quantity: Int? = null
    println(quantity ?: 0)

    quantity = 4
    println(quantity ?: 0)
}


// ===== CÁC LỚP PHỤ TRỢ =====

class Person {
    var name: String = ""
    var age: Int = 0
}

class Game {
    private var _currentScrambledWord = "test"
    val currentScrambledWord: String
        get() = _currentScrambledWord
}

class DetailActivity {
    companion object {
        const val LETTER = "letter"
    }
}

class GameViewModel

class WordManager {
    lateinit var currentWord: String
}
