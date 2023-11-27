package com.wolfbytetechnologies.ielts.viewModel.lessonViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LessonViewModelFactory(
    private var lessonJson: String? = ""
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = lessonJson?.let {
        LessonViewModel(
            it
        )
    } as T

}