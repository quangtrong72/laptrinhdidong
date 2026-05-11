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

private val MauXanh = Color(0xFF00C6FB)
private val MauTim = Color(0xFF6A11CB)
private val MauNen = Color(0xFFF7F8FC)

@Composable
fun SignUp(navController: NavController) {

    val context = LocalContext.current

    val firebaseAuth = FirebaseAuth.getInstance()

    var email by remember {
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
                            MauXanh,
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
                    text = "Tạo Tài Khoản ✨",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        "Đăng ký để bắt đầu trải nghiệm ứng dụng",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 17.sp
                )
            }
        }

        // Form đăng ký
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
                    text = "Đăng Ký",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MauTim
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
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

                // Button đăng ký
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MauTim
                    ),
                    onClick = {

                        if (
                            email.isNotEmpty() &&
                            password.isNotEmpty()
                        ) {

                            firebaseAuth
                                .createUserWithEmailAndPassword(
                                    email,
                                    password
                                )
                                .addOnCompleteListener {

                                    if (it.isSuccessful) {

                                        Toast.makeText(
                                            context,
                                            "Đăng ký thành công",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        navController.navigate(
                                            Screen.Signin.rout
                                        ) {

                                            popUpTo(
                                                Screen.Signup.rout
                                            ) {
                                                inclusive = true
                                            }
                                        }

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
                        text = "ĐĂNG KÝ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // Chuyển sang đăng nhập
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text = "Đã có tài khoản?"
                    )

                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Text(
                            text = "Đăng nhập ngay",
                            color = MauTim,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}