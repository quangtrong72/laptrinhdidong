package com.example.dangnhapdangky

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

private val MauNen = Color(0xFFF5F5F5)
private val MauChinh = Color(0xFF5E35B1)

@Composable
fun HomeScreen(navController: NavController) {

    val firebaseAuth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MauNen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Đăng Nhập Thành Công",
                    color = MauChinh,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "Chào mừng bạn quay lại",
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MauChinh
                    ),
                    onClick = {

                        firebaseAuth.signOut()

                        navController.navigate(
                            Screen.Signin.rout
                        ) {

                            popUpTo(
                                Screen.Home.rout
                            ) {
                                inclusive = true
                            }
                        }
                    }
                ) {

                    Text(
                        text = "ĐĂNG XUẤT",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}