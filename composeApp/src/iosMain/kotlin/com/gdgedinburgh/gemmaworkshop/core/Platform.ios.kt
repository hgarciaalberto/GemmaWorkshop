package com.gdgedinburgh.gemmaworkshop.core

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()