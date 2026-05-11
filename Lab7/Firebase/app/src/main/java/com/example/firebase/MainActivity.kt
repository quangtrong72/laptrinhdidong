package com.example.firebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.firebase.firestore.FirebaseFirestore

private val MauTimDau = Color(0xFF6A11CB)
private val MauTimCuoi = Color(0xFF2575FC)
private val MauNen = Color(0xFFF5F7FF)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirebaseTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MauNen
                ) {

                    val context = LocalContext.current

                    ManHinhThemKhoaHoc(context)
                }
            }
        }
    }
}

@Composable
fun ManHinhThemKhoaHoc(context: Context) {

    var tenKhoaHoc by remember { mutableStateOf("") }
    var thoiLuong by remember { mutableStateOf("") }
    var moTa by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // Phần tiêu đề
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MauTimDau,
                            MauTimCuoi
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterStart)
            ) {

                Text(
                    text = "Quản Lý Khóa Học",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tạo và quản lý khóa học dễ dàng",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp
                )
            }
        }

        // Thẻ nội dung
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .offset(y = (-40).dp),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Thêm Khóa Học",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauTimDau
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tên khóa học
                OutlinedTextField(
                    value = tenKhoaHoc,
                    onValueChange = { tenKhoaHoc = it },
                    label = { Text("Tên khóa học") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Book,
                            contentDescription = null,
                            tint = MauTimDau
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Thời lượng
                OutlinedTextField(
                    value = thoiLuong,
                    onValueChange = { thoiLuong = it },
                    label = { Text("Thời lượng") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Timer,
                            contentDescription = null,
                            tint = MauTimDau
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Mô tả
                OutlinedTextField(
                    value = moTa,
                    onValueChange = { moTa = it },
                    label = { Text("Mô tả khóa học") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = null,
                            tint = MauTimDau
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    minLines = 4
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Nút lưu khóa học
                Button(
                    onClick = {

                        if (
                            tenKhoaHoc.isBlank() ||
                            thoiLuong.isBlank() ||
                            moTa.isBlank()
                        ) {

                            Toast.makeText(
                                context,
                                "Vui lòng nhập đầy đủ thông tin",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            val db = FirebaseFirestore.getInstance()

                            val ref = db
                                .collection("KhoaHoc")
                                .document()

                            val khoaHoc = Course(
                                ref.id,
                                tenKhoaHoc,
                                thoiLuong,
                                moTa
                            )

                            ref.set(khoaHoc)
                                .addOnSuccessListener {

                                    Toast.makeText(
                                        context,
                                        "Lưu khóa học thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    tenKhoaHoc = ""
                                    thoiLuong = ""
                                    moTa = ""
                                }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MauTimDau
                    )
                ) {

                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "LƯU KHÓA HỌC",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nút xem danh sách
                OutlinedButton(
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                CourseDetailsActivity::class.java
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MauTimDau
                    )
                ) {

                    Text(
                        text = "XEM KHÓA HỌC ĐÃ LƯU",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}