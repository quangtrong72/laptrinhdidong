package com.example.noteapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.noteapp.model.Note
import com.google.firebase.firestore.FirebaseFirestore

class AddEditNoteActivity : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var imageUri by mutableStateOf<Uri?>(null)

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CloudinaryService.init(this)
        val noteId = intent.getStringExtra("noteId")
        setContent {
            AddEditScreen(noteId)
        }
    }

    @Composable
    fun AddEditScreen(noteId: String?) {

        val bg = Color(0xFF0F0F17)
        val surface = Color(0xFF16161E)
        val accent1 = Color(0xFF9B59B6)
        val accent2 = Color(0xFF2980B9)
        val textPrimary = Color(0xFFF0F0FF)
        val textSecondary = Color(0xFF6060AA)

        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var oldImageUrl by remember { mutableStateOf("") }
        var loading by remember { mutableStateOf(false) }

        LaunchedEffect(noteId) {
            if (noteId != null) {
                db.collection("notes")
                    .document(noteId)
                    .get()
                    .addOnSuccessListener { doc ->
                        val note = doc.toObject(Note::class.java)
                        title = note?.title ?: ""
                        description = note?.description ?: ""
                        oldImageUrl = note?.fileUrl ?: ""
                    }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
        ) {

            // Glow top-left
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

            // Glow bottom-right
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
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            onBackPressedDispatcher.onBackPressed()
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .background(surface, CircleShape)
                            .border(1.dp, textSecondary.copy(alpha = 0.3f), CircleShape)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = textPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = if (noteId == null) "Thêm ghi chú" else "Chỉnh sửa",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary
                        )
                        Text(
                            text = if (noteId == null) "Tạo ghi chú mới" else "Cập nhật ghi chú",
                            fontSize = 13.sp,
                            color = textSecondary
                        )
                    }
                }

                // Gradient divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 20.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color.Transparent,
                                    accent1.copy(alpha = 0.5f),
                                    accent2.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(20.dp))

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
                            unfocusedBorderColor = textSecondary.copy(alpha = 0.3f),
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
                        placeholder = { Text("Nhập nội dung...", color = textSecondary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
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

                    // ===== IMAGE PREVIEW =====
                    when {
                        imageUri != null -> {
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
                                        onClick = { pickImage.launch("image/*") },
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = Color.Black.copy(alpha = 0.6f),
                                            contentColor = textPrimary
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text("Đổi ảnh", fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        oldImageUrl.isNotEmpty() -> {
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
                                    model = oldImageUrl,
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
                                        onClick = { pickImage.launch("image/*") },
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = Color.Black.copy(alpha = 0.6f),
                                            contentColor = textPrimary
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text("Đổi ảnh", fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        else -> {
                            Button(
                                onClick = { pickImage.launch("image/*") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = surface
                                )
                            ) {
                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = accent1,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    "Chọn ảnh",
                                    color = textPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // ===== SAVE BUTTON =====
                    Button(
                        onClick = {
                            if (title.isEmpty()) {
                                Toast.makeText(
                                    this@AddEditNoteActivity,
                                    "Nhập tiêu đề",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            loading = true
                            if (imageUri != null) {
                                CloudinaryService.uploadImage(
                                    imageUri!!,
                                    onSuccess = { url ->
                                        saveNote(noteId, title, description, url)
                                    },
                                    onError = { msg ->
                                        Toast.makeText(
                                            this@AddEditNoteActivity,
                                            msg,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        loading = false
                                    }
                                )
                            } else {
                                saveNote(noteId, title, description, oldImageUrl)
                            }
                        },
                        enabled = !loading,
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
                                    brush = if (!loading)
                                        Brush.linearGradient(listOf(accent1, accent2))
                                    else
                                        Brush.linearGradient(
                                            listOf(Color(0xFF3A2A4A), Color(0xFF1A2A3A))
                                        ),
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = textPrimary,
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

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    private fun saveNote(
        noteId: String?,
        title: String,
        description: String,
        imageUrl: String
    ) {
        val note = hashMapOf(
            "title" to title,
            "description" to description,
            "fileUrl" to imageUrl
        )
        if (noteId == null) {
            db.collection("notes")
                .add(note)
                .addOnSuccessListener { finish() }
        } else {
            db.collection("notes")
                .document(noteId)
                .update(note as Map<String, Any>)
                .addOnSuccessListener { finish() }
        }
    }
}