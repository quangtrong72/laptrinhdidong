package com.example.noteapp.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    imageUri: Uri?,
    onChooseImage: () -> Unit,
    onSaveClick: (String, String) -> Unit,
    onExitClick: () -> Unit,
    loading: Boolean
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // 🔵 THEME XANH
    val bg = Color(0xFFE3F2FD)
    val surface = Color(0xFFFFFFFF)
    val accent1 = Color(0xFF2196F3)
    val accent2 = Color(0xFF1976D2)
    val textPrimary = Color(0xFF0D47A1)
    val textSecondary = Color(0xFF64B5F6)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {

        // Glow trái
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-80).dp, y = (-80).dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(accent1.copy(alpha = 0.15f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Glow phải
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 60.dp)
                .background(
                    brush = Brush.radialGradient(
                        listOf(accent2.copy(alpha = 0.12f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ===== TOP BAR =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onExitClick,
                    modifier = Modifier
                        .size(42.dp)
                        .background(surface, CircleShape)
                        .border(1.dp, accent1.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = accent1,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Ghi chú mới",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )
                    Text(
                        text = "Thêm tiêu đề và nội dung",
                        fontSize = 13.sp,
                        color = textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                // ===== TITLE =====
                Text(
                    text = "TIÊU ĐỀ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = textSecondary,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Nhập tiêu đề...", color = textSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent1,
                        unfocusedBorderColor = accent2.copy(alpha = 0.3f),
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        cursorColor = accent1,
                        focusedContainerColor = surface,
                        unfocusedContainerColor = surface
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ===== DESCRIPTION =====
                Text(
                    text = "NỘI DUNG",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = textSecondary,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Nhập nội dung ghi chú...", color = textSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accent1,
                        unfocusedBorderColor = accent2.copy(alpha = 0.3f),
                        focusedTextColor = textPrimary,
                        unfocusedTextColor = textPrimary,
                        cursorColor = accent1,
                        focusedContainerColor = surface,
                        unfocusedContainerColor = surface
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ===== IMAGE PICKER =====
                if (imageUri != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(
                                1.dp,
                                Brush.linearGradient(listOf(accent1, accent2)),
                                RoundedCornerShape(16.dp)
                            )
                    ) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                        ) {
                            FilledTonalButton(
                                onClick = onChooseImage,
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = accent1.copy(alpha = 0.8f),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Filled.Add, null)
                                Spacer(Modifier.width(4.dp))
                                Text("Đổi ảnh", fontSize = 12.sp)
                            }
                        }
                    }
                } else {
                    Button(
                        onClick = onChooseImage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accent1.copy(alpha = 0.1f)
                        )
                    ) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = accent1
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "Chọn ảnh",
                            color = textPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ===== SAVE BUTTON =====
                Button(
                    onClick = { onSaveClick(title, description) },
                    enabled = !loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (!loading)
                                    Brush.linearGradient(listOf(accent1, accent2))
                                else
                                    Brush.linearGradient(
                                        listOf(Color(0xFF90CAF9), Color(0xFF64B5F6))
                                    ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Lưu ghi chú",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}