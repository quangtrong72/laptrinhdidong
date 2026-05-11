package com.example.firebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.firebase.ui.theme.FirebaseTheme
import com.google.firebase.firestore.FirebaseFirestore

private val MauTimDau = Color(0xFF6A11CB)
private val MauTimCuoi = Color(0xFF2575FC)
private val MauNen = Color(0xFFF5F7FF)

class CourseDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            FirebaseTheme {

                val context = LocalContext.current

                val danhSachKhoaHoc =
                    remember { mutableStateListOf<Course>() }

                fun taiDuLieu() {

                    FirebaseFirestore
                        .getInstance()
                        .collection("KhoaHoc")
                        .get()
                        .addOnSuccessListener { query ->

                            danhSachKhoaHoc.clear()

                            for (doc in query.documents) {

                                doc.toObject(Course::class.java)
                                    ?.let {
                                        danhSachKhoaHoc.add(it)
                                    }
                            }
                        }
                }

                val lifecycleOwner =
                    LocalLifecycleOwner.current

                DisposableEffect(lifecycleOwner) {

                    val observer =
                        LifecycleEventObserver { _, event ->

                            if (event ==
                                Lifecycle.Event.ON_RESUME
                            ) {
                                taiDuLieu()
                            }
                        }

                    lifecycleOwner.lifecycle
                        .addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle
                            .removeObserver(observer)
                    }
                }

                LaunchedEffect(Unit) {
                    taiDuLieu()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MauNen
                ) {

                    Column {

                        // Header
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
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
                                    text = "Danh Sách Khóa Học",
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text =
                                        "Quản lý các khóa học đã lưu",
                                    color = Color.White.copy(
                                        alpha = 0.9f
                                    ),
                                    fontSize = 16.sp
                                )
                            }
                        }

                        // Danh sách khóa học
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = (-25).dp)
                                .padding(horizontal = 16.dp)
                        ) {

                            items(danhSachKhoaHoc) { khoaHoc ->

                                ItemKhoaHoc(
                                    course = khoaHoc,
                                    context = context
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemKhoaHoc(
    course: Course,
    context: Context
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            // Tên khóa học
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = MauTimDau
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text(
                    text = course.courseName ?: "",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauTimDau
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Thời lượng
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Timer,
                    contentDescription = null,
                    tint = Color.Gray
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Thời lượng: ${course.courseDuration}",
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Mô tả
            Text(
                text = course.courseDescription ?: "",
                fontSize = 15.sp,
                color = Color.Gray,
                lineHeight = 22.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Nút chỉnh sửa
            Button(
                onClick = {

                    val intent = Intent(
                        context,
                        UpdateCourse::class.java
                    ).apply {

                        putExtra(
                            "courseID",
                            course.courseID
                        )

                        putExtra(
                            "courseName",
                            course.courseName
                        )

                        putExtra(
                            "courseDuration",
                            course.courseDuration
                        )

                        putExtra(
                            "courseDescription",
                            course.courseDescription
                        )
                    }

                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MauTimDau
                )
            ) {

                Icon(
                    Icons.Default.Edit,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "CHỈNH SỬA KHÓA HỌC",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}