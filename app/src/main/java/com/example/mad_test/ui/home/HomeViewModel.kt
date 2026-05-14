package com.example.mad_test.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad_test.data.model.Article
import com.example.mad_test.repository.NewsRepository
import com.example.mad_test.util.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    private val allArticles = MutableStateFlow<List<Article>>(emptyList())

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _country = MutableStateFlow(Country.DEFAULT)
    val country: StateFlow<Country> = _country.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        fetch()
    }

    fun onCountrySelected(country: Country) {
        if (country == _country.value) return
        _country.value = country
        fetch()
    }

    fun onQueryChanged(text: String) {
        _query.value = text
        applyFilter()
    }

    fun clearQuery() {
        _query.value = ""
        applyFilter()
    }

    fun refresh() {
        fetch()
    }

    private fun applyFilter() {
        val list = allArticles.value
        val q = _query.value.trim()
        val filtered = if (q.isEmpty()) {
            list
        } else {
            list.filter { a ->
                a.title?.contains(q, ignoreCase = true) == true ||
                    a.description?.contains(q, ignoreCase = true) == true ||
                    a.source?.name?.contains(q, ignoreCase = true) == true
            }
        }
        _uiState.value = when {
            list.isEmpty() -> HomeUiState.Error("No articles available.")
            filtered.isEmpty() -> HomeUiState.Empty(q)
            else -> HomeUiState.Success(filtered)
        }
    }

    private fun fetch() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val articles = repository.getTopHeadlines(_country.value.code)
                allArticles.value = articles
                applyFilter()
                if (articles.isEmpty()) {
                    _uiState.value = HomeUiState.Error("No articles found for ${_country.value.displayName}.")
                }
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("No internet connection. Please check your network.")
            } catch (e: HttpException) {
                val msg = when (e.code()) {
                    401, 403 -> "Invalid API key. Please check GNEWS_API_KEY in local.properties."
                    429 -> "Too many requests. Please try again later."
                    else -> "Server error (${e.code()}). Please try again."
                }
                _uiState.value = HomeUiState.Error(msg)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.localizedMessage ?: "Something went wrong.")
            }
        }
    }
}
