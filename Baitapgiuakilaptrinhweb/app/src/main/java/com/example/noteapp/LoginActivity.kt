package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {

            // 🎨 THEME XANH
            val BluePrimary = Color(0xFF2196F3)
            val BlueDark = Color(0xFF1976D2)
            val Background = Color(0xFFE3F2FD)

            val colorScheme = lightColorScheme(
                primary = BluePrimary,
                secondary = BlueDark,
                background = Background
            )

            MaterialTheme(colorScheme = colorScheme) {

                var isLoading by remember { mutableStateOf(false) }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Đăng nhập",
                            style = MaterialTheme.typography.headlineMedium,
                            color = BlueDark
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Password
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Mật khẩu") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                isLoading = true
                                loginUser(email, password) {
                                    isLoading = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BluePrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text("Đăng nhập")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loginUser(
        email: String,
        pass: String,
        onDone: () -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->

                onDone()

                if (task.isSuccessful) {

                    // 🔥 PHÂN QUYỀN
                    val adminList = listOf(
                        "admin@gmail.com",
                        "admin1@gmail.com",
                        "admin2@gmail.com"
                    )

                    val role = if (adminList.contains(email)) "admin" else "user"

                    toast("Đăng nhập thành công ($role)")

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("role", role)

                    startActivity(intent)
                    finish()

                } else {

                    val e = task.exception

                    val message = when (e) {
                        is FirebaseAuthInvalidUserException ->
                            "Email chưa đăng ký"

                        is FirebaseAuthInvalidCredentialsException ->
                            "Sai mật khẩu"

                        else ->
                            e?.message ?: "Login thất bại"
                    }

                    toast(message)
                }
            }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}