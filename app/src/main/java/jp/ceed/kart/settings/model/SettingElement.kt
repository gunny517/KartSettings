package jp.ceed.kart.settings.model

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingElement(val label: Int, val index: Int, val inputType: Int)
