package com.ohiopolice.app

import android.app.Application
import com.ohiopolice.app.data.AppDatabase
import com.ohiopolice.app.data.OrcRepository
import com.ohiopolice.app.data.PinRepository

class OhioPoliceApp : Application() {
    lateinit var database: AppDatabase
        private set

    lateinit var orcRepository: OrcRepository
        private set

    lateinit var pinRepository: PinRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.build(this)
        orcRepository = OrcRepository(this)
        pinRepository = PinRepository(this)
    }
}
