package com.gdgedinburgh.gemmaworkshop.model

import com.gdgedinburgh.gemmaworkshop.core.randomUUID

/**
 * Used to represent a ChatMessage
 */
data class ChatMessage(
    val id: String = randomUUID(),
    val message: String = "",
    val author: String,
    val isLoading: Boolean = false
) {
    val isFromUser: Boolean
        get() = author == USER_PREFIX
}