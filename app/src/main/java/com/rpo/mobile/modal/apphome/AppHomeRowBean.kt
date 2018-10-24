package com.rpo.mobile.modal.apphome

data class AppHomeRowBean(val posname: String?, val posstatus: PosStatus?, val postotal: Int?, val posnotificationcount: Int?)

enum class PosStatus {
    LOCKED,
    INACTIVE,
    ACTIVE,
    OFFLINE
}