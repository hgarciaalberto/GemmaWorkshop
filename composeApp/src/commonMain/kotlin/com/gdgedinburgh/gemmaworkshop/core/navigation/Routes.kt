package com.gdgedinburgh.gemmaworkshop.core.navigation

import kotlinx.serialization.Serializable

interface Destination

@Serializable
object HomeDestination : Destination

@Serializable
object GenerateTextDestination : Destination

@Serializable
object SpeechToTextDestination : Destination

@Serializable
object SummaryTextDestination : Destination
