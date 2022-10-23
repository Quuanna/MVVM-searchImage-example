package com.anna.homeworkandroidinterview

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE) // 應用程式和資料庫模式
    }

    companion object {
        const val AUTHORITY = "com.anna.homeworkandroidinterview.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}