package com.kamford.app.compose.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

inline fun <reified VM : ViewModel> viewModelProviderFactoryOf(
    crossinline create: () -> VM
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        create()
    }
}
