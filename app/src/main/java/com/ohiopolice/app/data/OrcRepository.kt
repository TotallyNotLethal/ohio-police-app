package com.ohiopolice.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class OrcRepository(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loadOrcStatutes(): List<OrcStatute> = withContext(Dispatchers.IO) {
        val raw = context.assets.open("orc_ohio.json").bufferedReader().use { it.readText() }
        json.decodeFromString(raw)
    }
}
