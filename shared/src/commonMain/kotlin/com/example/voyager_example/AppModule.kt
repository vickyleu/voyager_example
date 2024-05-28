package com.example.voyager_example

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initUoocKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            modelModule,
        )
    }