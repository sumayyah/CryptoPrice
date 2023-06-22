package com.sumayyah.cryptoprice.di

import com.squareup.anvil.annotations.MergeComponent

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {
}
