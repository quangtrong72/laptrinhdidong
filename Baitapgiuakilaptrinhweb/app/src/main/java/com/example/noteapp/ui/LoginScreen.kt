package com.example.noteapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    isLoading: Boolean,
    onLogin: (String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val bg = Color(0xFF0F0F17)
    val surface = Color(0xFF16161E)
    val accent1 = Color(0xFF9B59B6)
    val accent2 = Color(0xFF2980B9)
    val textPrimary = Color(0xFFF0F0FF)
    val textSecondary = Color(0xFF6060AA)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg),
        contentAlignment = Alignment.Center
    ) {

        // Glow top-left
        Box(
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopStart)
                .offset(x = (-100).dp, y = (-100).dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(accent1.copy(alpha = 0.2f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Glow bottom-right
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 80.dp, y = 80.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(accent2.copy(alpha = 0.15f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ===== LOGO / TITLE =====
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(accent1, accent2)),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✦",
                    fontSize = 28.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "NoteApp",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Đăng nhập để tiếp tục",
                fontSize = 14.sp,
                color = textSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gradient divider
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(3.dp)
                    .background(
                        brush = Brush.horizontalGradient(listOf(accent1, accent2)),
                        shape = RoundedCornerShape(2.dp)
                    )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // ===== EMAIL =====
            Text(
                text = "EMAIL",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textSecondary,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Nhập email...", color = textSecondary) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent1,
                    unfocusedBorderColor = textSecondary.copy(alpha = 0.3f),
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    cursorColor = accent1,
                    focusedContainerColor = surface,
                    unfocusedContainerColor = surface
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ===== PASSWORD =====
            Text(
                text = "MẬT KHẨU",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textSecondary,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Nhập mật khẩu...", color = textSecondary) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent1,
                    unfocusedBorderColor = textSecondary.copy(alpha = 0.3f),
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    cursorColor = accent1,
                    focusedContainerColor = surface,
                    unfocusedContainerColor = surface
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // ===== LOGIN BUTTON =====
            Button(
                onClick = {
                    if (!isLoading && email.isNotEmpty() && password.isNotEmpty()) {
                        onLogin(email.trim(), password.trim())
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = if (!isLoading)
                                Brush.linearGradient(listOf(accent1, accent2))
                            else
                                Brush.linearGradient(
                                    listOf(Color(0xFF3A2A4A), Color(0xFF1A2A3A))
                                ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = textPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Đăng nhập",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ===== HINT =====
            Text(
                text = "Chưa có tài khoản? Liên hệ admin",
                fontSize = 13.sp,
                color = textSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}