package com.hod.uuidreceiver.ui

import com.hod.uuidreceiver.data.Entity

data class Model(val code: String, val count: Int)

fun Entity.toModel(): Model = Model(responseCode, count)
