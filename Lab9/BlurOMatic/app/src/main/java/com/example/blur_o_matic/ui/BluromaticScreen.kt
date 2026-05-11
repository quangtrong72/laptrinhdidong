package com.example.blur_o_matic.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blur_o_matic.R
import com.example.blur_o_matic.data.BlurAmount
import com.example.blur_o_matic.ui.theme.BluromaticTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluromaticScreen(blurViewModel: BlurViewModel = viewModel(factory = BlurViewModel.Factory)) {
    val uiState by blurViewModel.blurUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Yêu cầu quyền thông báo cho Android 13+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Hãy cấp quyền thông báo để theo dõi tiến trình", Toast.LENGTH_SHORT).show()
            }
        }
        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Thông báo khi hoàn thành
    LaunchedEffect(uiState) {
        if (uiState is BlurUiState.Complete) {
            Toast.makeText(context, "Đã làm mờ và lưu ảnh thành công!", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("BLUR-O-MATIC", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BluromaticScreenContent(
                blurUiState = uiState,
                blurAmountOptions = blurViewModel.blurAmount,
                applyBlur = { level, label -> 
                    Toast.makeText(context, "Bắt đầu làm mờ: $label", Toast.LENGTH_SHORT).show()
                    blurViewModel.applyBlur(level)
                },
                cancelWork = { 
                    Toast.makeText(context, "Đã dừng tác vụ.", Toast.LENGTH_SHORT).show()
                    blurViewModel.cancelWork() 
                },
                onSeeFileClick = { uri -> 
                    Toast.makeText(context, "Đang mở ảnh...", Toast.LENGTH_SHORT).show()
                    showBlurredImage(context, uri) 
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun BluromaticScreenContent(
    blurUiState: BlurUiState,
    blurAmountOptions: List<BlurAmount>,
    applyBlur: (Int, String) -> Unit,
    cancelWork: () -> Unit,
    onSeeFileClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableIntStateOf(1) }
    val context = LocalContext.current
    
    val currentLabel = stringResource(
        blurAmountOptions.find { it.blurAmount == selectedValue }?.blurAmountRes ?: R.string.blur_lv_1
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.android_cupcake),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop,
            )
        }

        BlurAmountContent(
            selectedValue = selectedValue,
            blurAmounts = blurAmountOptions,
            onSelectedValueChange = { amount, label -> 
                selectedValue = amount
                Toast.makeText(context, "Đã chọn: $label", Toast.LENGTH_SHORT).show()
            }
        )

        BlurActions(
            blurUiState = blurUiState,
            onStartClick = { applyBlur(selectedValue, currentLabel) },
            onSeeFileClick = onSeeFileClick,
            onCancelClick = cancelWork,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BlurActions(
    blurUiState: BlurUiState,
    onStartClick: () -> Unit,
    onSeeFileClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // Nút Bắt đầu luôn hiển thị
        Button(
            onClick = onStartClick, 
            modifier = Modifier.fillMaxWidth(),
            enabled = blurUiState !is BlurUiState.Loading,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (blurUiState is BlurUiState.Loading) "ĐANG XỬ LÝ..." else "BẮT ĐẦU LÀM MỜ", 
                fontWeight = FontWeight.Bold
            )
        }

        if (blurUiState is BlurUiState.Loading) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onCancelClick, 
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("HỦY BỎ TÁC VỤ", fontWeight = FontWeight.Bold)
            }
        }
        
        if (blurUiState is BlurUiState.Complete) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { onSeeFileClick(blurUiState.outputUri) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("XEM ẢNH KẾT QUẢ", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun BlurAmountContent(
    selectedValue: Int,
    blurAmounts: List<BlurAmount>,
    onSelectedValueChange: (Int, String) -> Unit
) {
    Column(modifier = Modifier.selectableGroup()) {
        Text(
            text = "Mức độ làm mờ:",
            style = MaterialTheme.typography.titleLarge, 
            fontWeight = FontWeight.Bold
        )
        blurAmounts.forEach { amount ->
            val label = stringResource(amount.blurAmountRes)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedValue == amount.blurAmount),
                        onClick = { onSelectedValueChange(amount.blurAmount, label) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(selected = (selectedValue == amount.blurAmount), onClick = null)
                Text(text = label, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

private fun showBlurredImage(context: Context, currentUri: String) {
    val uri = if (currentUri.isNotEmpty()) Uri.parse(currentUri) else null
    val actionView = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(actionView)
}
