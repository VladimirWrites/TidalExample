package dev.vladimirj.tidal.search.ui.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
inline val TestCoroutineScope.testDispatcher: TestCoroutineDispatcher
    get() {
        val handler = coroutineContext[ContinuationInterceptor]
        return handler as? TestCoroutineDispatcher ?: throw IllegalStateException(
            "TestCoroutineScope was expected to have TestCoroutineDispatcher but was $handler"
        )
    }