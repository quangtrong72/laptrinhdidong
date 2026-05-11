package com.example.inventoryapproom.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventoryapproom.InventoryApplication
import com.example.inventoryapproom.ui.home.HomeViewModel
import com.example.inventoryapproom.ui.item.ItemDetailsViewModel
import com.example.inventoryapproom.ui.item.ItemEditViewModel
import com.example.inventoryapproom.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // SỬA LỖI: Cung cấp Repository cho ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        // Cái này bạn làm đúng rồi nè
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        // SỬA LỖI: Cung cấp Repository cho ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        // SỬA LỖI: Cung cấp Repository cho HomeViewModel (Quan trọng nhất để hiển thị danh sách)
        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)