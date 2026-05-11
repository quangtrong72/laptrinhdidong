package com.example.dangnhapdangky

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

private val MauHong = Color(0xFFFF4B91)
private val MauTim = Color(0xFF7B61FF)
private val MauNen = Color(0xFFF7F8FC)

@Composable
fun SignIn(navController: NavController) {

    val context = LocalContext.current

    val firebaseAuth = FirebaseAuth.getInstance()

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNen)
            .verticalScroll(rememberScrollState())
    ) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MauHong,
                            MauTim
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .padding(28.dp)
                    .align(Alignment.CenterStart)
            ) {

                Text(
                    text = "Xin Chào 👋",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        "Đăng nhập để tiếp tục sử dụng ứng dụng",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 17.sp
                )
            }
        }

        // Form đăng nhập
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .offset(y = (-50).dp),
            shape = RoundedCornerShape(30.dp),
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
                    text = "Đăng Nhập",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauHong
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // Email
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // Password
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text("Mật khẩu")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    visualTransformation =
                        PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                // Button Login
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MauHong
                    ),
                    onClick = {

                        if (
                            username.isNotEmpty() &&
                            password.isNotEmpty()
                        ) {

                            firebaseAuth
                                .signInWithEmailAndPassword(
                                    username,
                                    password
                                )
                                .addOnCompleteListener {

                                    if (it.isSuccessful) {

                                        navController.navigate(
                                            Screen.Home.rout
                                        )

                                    } else {

                                        Toast.makeText(
                                            context,
                                            "Lỗi: ${
                                                it.exception?.message
                                            }",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {

                            Toast.makeText(
                                context,
                                "Vui lòng nhập đầy đủ thông tin",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) {

                    Text(
                        text = "ĐĂNG NHẬP",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // Chuyển sang đăng ký
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text = "Chưa có tài khoản?"
                    )

                    TextButton(
                        onClick = {
                            navController.navigate(
                                Screen.Signup.rout
                            )
                        }
                    ) {

                        Text(
                            text = "Đăng ký ngay",
                            color = MauHong,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}