package dev.vladimirj.tidal.base.ui

import kotlinx.coroutines.CoroutineDispatcher

class CoroutineDispatcherProvider(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)