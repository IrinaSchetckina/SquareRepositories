package com.shchotkina.squarerepositories.di

import org.junit.Assert.assertNotNull
import org.junit.Test

class AppModuleTest {
    private val appModule = AppModule()

    @Test
    fun `okHttpCallFactory provides a non-null value`() {
        val result = appModule.okHttpCallFactory()
        assertNotNull(result)
    }

    @Test
    fun `provideRepositoriesApi provides a non-null value`() {
        val okHttpCallFactory = appModule.okHttpCallFactory()
        val result = appModule.provideRepositoriesApi(okHttpCallFactory)
        assertNotNull(result)
    }
}