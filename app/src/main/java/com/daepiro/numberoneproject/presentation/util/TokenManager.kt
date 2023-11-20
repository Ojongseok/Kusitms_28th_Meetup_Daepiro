package com.daepiro.numberoneproject.presentation.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.daepiro.numberoneproject.presentation.util.TokenManager.Companion.DATA_STORE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = DATA_STORE)

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val MEMBER_ID = stringPreferencesKey("member_id")
        const val DATA_STORE = "data_store"
    }

    suspend fun writeLoginTokens(
        accessToken: String,
        refreshToken: String
    ) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    val accessToken: Flow<String> = context.dataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }

    val refreshToken: Flow<String> = context.dataStore.data.map {
        it[REFRESH_TOKEN] ?: ""
    }

    suspend fun writeMemberId(
        memberId: Int
    ) {
        context.dataStore.edit {
            it[MEMBER_ID] = memberId.toString()
        }
    }

    val memberId: Flow<String> = context.dataStore.data.map {
        it[MEMBER_ID] ?: ""
    }
}
