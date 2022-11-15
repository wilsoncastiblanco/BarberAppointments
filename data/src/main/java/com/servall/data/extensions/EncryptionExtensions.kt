package com.servall.data.extensions

import java.security.MessageDigest

internal fun MessageDigest.encrypt(value: ByteArray): String {
    this.update(value)
    return this.digest().toString()
}