package jp.ceed.kart.settings

abstract class AbsEventContent<T>(
    val eventType: T,
    val value: Int
)