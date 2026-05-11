package com.example.firebase

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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
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

class UpdateCourse : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("courseID") ?: ""

        val tenKhoaHoc =
            intent.getStringExtra("courseName") ?: ""

        val thoiLuong =
            intent.getStringExtra("courseDuration") ?: ""

        val moTa =
            intent.getStringExtra("courseDescription") ?: ""

        setContent {

            FirebaseTheme {

                val context = LocalContext.current

                var tenMoi by remember {
                    mutableStateOf(tenKhoaHoc)
                }

                var thoiLuongMoi by remember {
                    mutableStateOf(thoiLuong)
                }

                var moTaMoi by remember {
                    mutableStateOf(moTa)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MauNen
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(
                                rememberScrollState()
                            )
                    ) {

                        // Header
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
                                    .align(
                                        Alignment.CenterStart
                                    )
                            ) {

                                Text(
                                    text = "Cập Nhật Khóa Học",
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text =
                                        "Chỉnh sửa thông tin khóa học",
                                    color = Color.White.copy(
                                        alpha = 0.9f
                                    ),
                                    fontSize = 16.sp
                                )
                            }
                        }

                        // Form chỉnh sửa
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .offset(y = (-40).dp),
                            shape = RoundedCornerShape(28.dp),
                            elevation =
                                CardDefaults.cardElevation(
                                    defaultElevation = 10.dp
                                ),
                            colors =
                                CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                        ) {

                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                            ) {

                                Text(
                                    text =
                                        "Thông Tin Khóa Học",
                                    fontSize = 24.sp,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color = MauTimDau
                                )

                                Spacer(
                                    modifier = Modifier.height(
                                        24.dp
                                    )
                                )

                                // Tên khóa học
                                OutlinedTextField(
                                    value = tenMoi,
                                    onValueChange = {
                                        tenMoi = it
                                    },
                                    label = {
                                        Text(
                                            "Tên khóa học"
                                        )
                                    },
                                    leadingIcon = {

                                        Icon(
                                            Icons.Default.Book,
                                            contentDescription =
                                                null,
                                            tint = MauTimDau
                                        )
                                    },
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    shape =
                                        RoundedCornerShape(
                                            18.dp
                                        ),
                                    singleLine = true
                                )

                                Spacer(
                                    modifier = Modifier.height(
                                        18.dp
                                    )
                                )

                                // Thời lượng
                                OutlinedTextField(
                                    value = thoiLuongMoi,
                                    onValueChange = {
                                        thoiLuongMoi = it
                                    },
                                    label = {
                                        Text("Thời lượng")
                                    },
                                    leadingIcon = {

                                        Icon(
                                            Icons.Default.Timer,
                                            contentDescription =
                                                null,
                                            tint = MauTimDau
                                        )
                                    },
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    shape =
                                        RoundedCornerShape(
                                            18.dp
                                        ),
                                    singleLine = true
                                )

                                Spacer(
                                    modifier = Modifier.height(
                                        18.dp
                                    )
                                )

                                // Mô tả
                                OutlinedTextField(
                                    value = moTaMoi,
                                    onValueChange = {
                                        moTaMoi = it
                                    },
                                    label = {
                                        Text(
                                            "Mô tả khóa học"
                                        )
                                    },
                                    leadingIcon = {

                                        Icon(
                                            Icons.Default.Description,
                                            contentDescription =
                                                null,
                                            tint = MauTimDau
                                        )
                                    },
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    minLines = 4,
                                    shape =
                                        RoundedCornerShape(
                                            18.dp
                                        )
                                )

                                Spacer(
                                    modifier = Modifier.height(
                                        30.dp
                                    )
                                )

                                // Nút cập nhật
                                Button(
                                    onClick = {

                                        if (
                                            tenMoi.isBlank()
                                        ) {

                                            Toast.makeText(
                                                context,
                                                "Vui lòng nhập tên khóa học",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            return@Button
                                        }

                                        val duLieuMoi =
                                            Course(
                                                id,
                                                tenMoi,
                                                thoiLuongMoi,
                                                moTaMoi
                                            )

                                        FirebaseFirestore
                                            .getInstance()
                                            .collection(
                                                "KhoaHoc"
                                            )
                                            .document(id)
                                            .set(duLieuMoi)
                                            .addOnSuccessListener {

                                                Toast.makeText(
                                                    context,
                                                    "Cập nhật thành công",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                (
                                                        context
                                                                as ComponentActivity
                                                        ).finish()
                                            }
                                            .addOnFailureListener { e ->

                                                Toast.makeText(
                                                    context,
                                                    "Lỗi: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(58.dp),
                                    shape =
                                        RoundedCornerShape(
                                            18.dp
                                        ),
                                    colors =
                                        ButtonDefaults.buttonColors(
                                            containerColor =
                                                MauTimDau
                                        )
                                ) {

                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription =
                                            null
                                    )

                                    Spacer(
                                        modifier = Modifier.width(
                                            10.dp
                                        )
                                    )

                                    Text(
                                        text =
                                            "CẬP NHẬT KHÓA HỌC",
                                        fontSize = 16.sp,
                                        fontWeight =
                                            FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}