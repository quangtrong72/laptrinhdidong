package org.example.Bai2



import kotlin.math.PI

// ===== LỚP TRỪU TƯỢNG =====
abstract class Dwelling {

    // Thuộc tính trừu tượng: vật liệu xây dựng
    abstract val buildingMaterial: String

    // Phương thức trừu tượng: tính diện tích sàn
    abstract fun floorArea(): Double

    // Phương thức mở: kiểm tra còn chỗ ở không
    open fun hasRoom(): Boolean {
        return true
    }
}

// ===== LỚP MỞ (open) =====
open class RoundHut(val residents: Int) : Dwelling() {

    // Ghi đè vật liệu xây dựng
    override val buildingMaterial = "Rơm"

    // Ghi đè hàm tính diện tích sàn
    override fun floorArea(): Double {
        return PI * 3 * 3   // diện tích hình tròn bán kính 3
    }
}

// ===== LỚP CON KẾ THỪA =====
class SquareCabin(val floors: Int) : Dwelling() {

    // Ghi đè vật liệu xây dựng
    override val buildingMaterial = "Gỗ"

    // Ghi đè hàm tính diện tích sàn
    override fun floorArea(): Double {
        return super.hasRoom().let { 100.0 * floors }
    }
}

// ===== HÀM VARARG (NHIỀU ĐỐI SỐ) =====
fun addToppings(vararg toppings: String) {
    println("Danh sách topping:")
    for (item in toppings) {
        println(item)
    }
}

// ===== HÀM MAIN =====
fun main() {

    // ===== DANH SÁCH CHỈ ĐỌC =====
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    println("Kích thước danh sách: ${numbers.size}")
    println("Phần tử đầu tiên: ${numbers[0]}")
    println("Danh sách đảo ngược: ${listOf("đỏ", "xanh dương", "xanh lá").reversed()}")

    // ===== DANH SÁCH CÓ THỂ THAY ĐỔI =====
    val entrees = mutableListOf<String>()
    entrees.add("mì ý")
    entrees[0] = "lasagna"
    entrees.remove("lasagna")

    // ===== VÒNG LẶP FOR =====
    val myList = listOf("Táo", "Chuối", "Cherry")
    for (element in myList) {
        println("Phần tử: $element")
    }

    // ===== VÒNG LẶP WHILE =====
    var index = 0
    while (index < myList.size) {
        println("Phần tử tại vị trí $index là: ${myList[index]}")
        index++
    }

    // ===== CHUỖI =====
    val name = "Android"
    println("Số ký tự trong chuỗi: ${name.length}")

    val number = 10
    println("$number người")

    val groups = 5
    println("${number * groups} người")

    // ===== TOÁN TỬ GÁN TĂNG CƯỜNG =====
    var a = 10
    var b = 5
    a += b
    a -= b
    a *= b
    a /= b
    println("Giá trị của a sau khi tính toán = $a")

    // ===== SỬ DỤNG WITH =====
    val squareCabin = SquareCabin(2)

    with(squareCabin) {
        println("Vật liệu xây dựng: $buildingMaterial")
        println("Diện tích sàn: ${floorArea()}")
        println("Còn chỗ ở không? ${hasRoom()}")
    }

    // ===== DÙNG HẰNG SỐ TOÁN HỌC PI =====
    val radius = 3.0
    val area = kotlin.math.PI * radius * radius
    println("Diện tích hình tròn = $area")

    // ===== XÂU CHUỖI GỌI HÀM =====
    val text = "  12345  "
    val stringInTextField = text.trim().toString()
    println("Chuỗi sau khi xử lý: $stringInTextField")

    // ===== VARARG =====
    addToppings("Phô mai", "Cà chua", "Ô liu")

    // ===== TẠO ĐỐI TƯỢNG TỪ CLASS =====
    val myHut = RoundHut(3)
    println("Vật liệu của túp lều: ${myHut.buildingMaterial}")
    println("Diện tích túp lều: ${myHut.floorArea()}")
}
