package com.wolfbytetechnologies.ielts.ui.reading.readingTests.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReadingTestViewModelFactory(
    var readingTestJson: String = ""
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ReadingTestViewModel(readingTestJson) as T
}