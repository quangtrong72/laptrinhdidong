// ===== CHƯƠNG TRÌNH MAIN NHỎ NHẤT =====
fun main() {

    // In văn bản
    println("Hello, world!")
    println("This is the text to print!")

    // ===== BIẾN =====
    val age = "5"          // không đổi được
    val name = "Rover"

    var roll = 6           // có thể thay đổi
    var rolledValue: Int = 4

    // In biến với mẫu chuỗi
    println("You are already ${age}!")
    println("You are already ${age} days old, ${name}!")

    // ===== TOÁN TỬ CƠ BẢN =====
    val a = 10
    val b = 3
    println("a + b = ${a + b}")
    println("a - b = ${a - b}")
    println("a * b = ${a * b}")
    println("a / b = ${a / b}")

    // ===== TOÁN TỬ LOGIC =====
    println("a > b = ${a > b}")
    println("a < b = ${a < b}")
    println("a == b = ${a == b}")
    println("a != b = ${a != b}")

    // ===== GỌI HÀM KHÔNG ĐỐI SỐ =====
    printHello()

    // ===== GỌI HÀM CÓ ĐỐI SỐ =====
    printBorder("*", 10)

    // ===== GỌI HÀM TRẢ VỀ GIÁ TRỊ =====
    val diceResult = rollDice()
    println("Giá trị xúc xắc: $diceResult")

    // ===== TẠO SỐ NGẪU NHIÊN =====
    val diceRange = 1..6
    val randomNumber = diceRange.random()
    println("Số ngẫu nhiên: $randomNumber")

    // ===== REPEAT =====
    printBorderLine()

    // ===== LỒNG REPEAT =====
    printCakeBottom(5, 3)

    // ===== IF / ELSE =====
    val num = 4
    if (num > 4) {
        println("The variable is greater than 4")
    } else if (num == 4) {
        println("The variable is equal to 4")
    } else {
        println("The variable is less than 4")
    }

    // ===== WHEN =====
    val rollResult = (1..6).random()
    val luckyNumber = 3

    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("So sorry! You rolled a 1. Try again!")
        2 -> println("Sadly, you rolled a 2. Try again!")
        3 -> println("Unfortunately, you rolled a 3. Try again!")
        4 -> println("No luck! You rolled a 4. Try again!")
        5 -> println("Don't cry! You rolled a 5. Try again!")
        6 -> println("Apologies! you rolled a 6. Try again!")
    }

    // ===== CLASS + TẠO ĐỐI TƯỢNG =====
    val myFirstDice = Dice(6)
    val result = myFirstDice.roll()
    println("Kết quả từ class Dice: $result")
}


// ===== HÀM KHÔNG CÓ ĐỐI SỐ =====
fun printHello() {
    println("Hello Kotlin")
}


// ===== HÀM CÓ ĐỐI SỐ =====
fun printBorder(border: String, timesToRepeat: Int) {
    repeat(timesToRepeat) {
        print(border)
    }
    println()
}


// ===== HÀM TRẢ VỀ GIÁ TRỊ =====
fun rollDice(): Int {
    val randomNumber = (1..6).random()
    return randomNumber
}


// ===== REPEAT IN ĐƯỜNG VIỀN =====
fun printBorderLine() {
    repeat(23) {
        print("=")
    }
    println()
}


// ===== LỒNG REPEAT =====
fun printCakeBottom(age: Int, layers: Int) {
    repeat(layers) {
        repeat(age + 2) {
            print("@")
        }
        println()
    }
}


// ===== CLASS ĐƠN GIẢN =====
class Dice(val numSides: Int) {

    fun roll(): Int {
        val randomNumber = (1..numSides).random()
        return randomNumber
    }
}
