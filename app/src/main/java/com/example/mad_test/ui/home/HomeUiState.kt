package com.example.mad_test.ui.home

import com.example.mad_test.data.model.Article

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val articles: List<Article>) : HomeUiState
    data class Empty(val query: String) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
