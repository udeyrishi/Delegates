/**
 Copyright (c) 2018 Udey Rishi. All rights reserved.
*/

package com.udeyrishi.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface ReadWriteDelegateProvider<in R, T> {
    operator fun provideDelegate(thisRef: R, property: KProperty<*>): ReadWriteProperty<R, T>
}