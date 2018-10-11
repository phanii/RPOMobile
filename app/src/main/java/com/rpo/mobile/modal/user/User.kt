package com.rpo.mobile.modal.user


data class User(
        val username: String, // admin
        val password: String  // password
        // 133
) {
    val id: Int? = null

    constructor(
            username: String, // admin
            password: String, id: Int) : this(username, password)
}
