package jp.ceed.kart.settings.model

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingLabel(val label: Int, val index: Int)
