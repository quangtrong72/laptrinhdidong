package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val role = intent.getStringExtra("role") ?: "user"

        setContent {
            MainScreen(role)
        }
    }

    @Composable
    fun MainScreen(role: String) {

        // 🔵 THEME XANH
        val bg = Color(0xFFE3F2FD)
        val surface = Color(0xFFFFFFFF)
        val accent1 = Color(0xFF2196F3)
        val accent2 = Color(0xFF1976D2)
        val textPrimary = Color(0xFF0D47A1)
        val textSecondary = Color(0xFF64B5F6)

        var notes by remember { mutableStateOf<List<Note>>(emptyList()) }
        var loading by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            db.collection("notes")
                .addSnapshotListener { value, error ->
                    loading = false
                    if (error != null) return@addSnapshotListener

                    val list = mutableListOf<Note>()
                    value?.forEach { doc ->
                        val note = doc.toObject(Note::class.java)
                        note.id = doc.id
                        list.add(note)
                    }
                    notes = list
                }
        }

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

            Scaffold(
                containerColor = Color.Transparent,

                // 🔥 Admin mới có nút +
                floatingActionButton = {
                    if (role == "admin") {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    brush = Brush.linearGradient(listOf(accent1, accent2)),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    startActivity(
                                        Intent(this@MainActivity, AddEditNoteActivity::class.java)
                                    )
                                },
                                containerColor = Color.Transparent,
                                contentColor = Color.White,
                                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                modifier = Modifier.size(60.dp),
                                shape = CircleShape
                            ) {
                                Icon(
                                    Icons.Rounded.Add,
                                    contentDescription = "Thêm"
                                )
                            }
                        }
                    }
                }

            ) { padding ->

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {

                    // ===== HEADER =====
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Ghi Chú",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = textPrimary
                            )
                            Text(
                                text = if (role == "admin") "Admin Mode" else "User Mode",
                                fontSize = 13.sp,
                                color = if (role == "admin") accent1 else textSecondary
                            )
                        }

                        IconButton(
                            onClick = {
                                FirebaseAuth.getInstance().signOut()
                                startActivity(
                                    Intent(this@MainActivity, LoginActivity::class.java)
                                )
                                finish()
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .background(surface, CircleShape)
                        ) {
                            Icon(
                                Icons.Rounded.ExitToApp,
                                contentDescription = "Logout",
                                tint = accent1
                            )
                        }
                    }

                    // ===== LOADING =====
                    if (loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = accent1)
                        }
                    }

                    // ===== EMPTY =====
                    if (!loading && notes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Chưa có ghi chú",
                                color = textSecondary
                            )
                        }
                    }

                    // ===== LIST =====
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(notes) { note ->
                            NoteItem(
                                note = note,
                                isAdmin = role == "admin",
                                onEditClick = {
                                    val intent = Intent(
                                        this@MainActivity,
                                        AddEditNoteActivity::class.java
                                    )
                                    intent.putExtra("noteId", note.id)
                                    startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}