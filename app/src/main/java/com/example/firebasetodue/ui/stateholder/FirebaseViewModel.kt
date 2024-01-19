package com.example.firebasetodue.ui.stateholder

import androidx.lifecycle.ViewModel
import com.example.firebasetodue.dataLayer.source.remote.database.FirebaseRepository

class FirebaseViewModel(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {
}