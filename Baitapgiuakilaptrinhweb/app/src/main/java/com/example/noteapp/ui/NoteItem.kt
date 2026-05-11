package com.example.noteapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.noteapp.model.Note
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NoteItem(
    note: Note,
    isAdmin: Boolean,
    onEditClick: () -> Unit
) {

    val db = FirebaseFirestore.getInstance()

    var pressed by remember { mutableStateOf(false) }

    val cardBg by animateColorAsState(
        targetValue = if (pressed) Color(0xFF1E1E2E) else Color(0xFF16161E),
        animationSpec = tween(200), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Color(0xFF9B59B6), Color(0xFF2980B9))
                ),
                RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {

        Column {

            // IMAGE
            if (note.fileUrl.isNotEmpty()) {
                AsyncImage(
                    model = note.fileUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = note.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = note.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🔥 CHỈ ADMIN MỚI THẤY BUTTON
                if (isAdmin) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        // EDIT
                        FilledTonalButton(onClick = onEditClick) {
                            Icon(Icons.Rounded.Edit, null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Sửa")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // DELETE
                        FilledTonalButton(
                            onClick = {
                                db.collection("notes")
                                    .document(note.id)
                                    .delete()
                            }
                        ) {
                            Icon(Icons.Rounded.Delete, null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Xóa")
                        }
                    }
                }
            }
        }
    }
}