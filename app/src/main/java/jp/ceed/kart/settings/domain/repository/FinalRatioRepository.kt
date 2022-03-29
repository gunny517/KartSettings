package jp.ceed.kart.settings.domain.repository

class FinalRatioRepository {

    private val driveList: List<Int> = listOf(10, 11, 12, 13)

    companion object {
        const val DRIVEN_MIN = 80
        const val DRIVEN_MAX = 95
    }

    fun getFinalRatioData(): List<String> {
        val list: ArrayList<String> = ArrayList()
        list.add("")
        for(drive in driveList){
            list.add(drive.toString())
        }
        for(driven in DRIVEN_MIN..DRIVEN_MAX){
            list.add(driven.toString())
            for(drive in driveList){
                val ratio: Float = driven.toFloat() / drive.toFloat()
                list.add(String.format("%.3f", ratio))
            }
        }
        return list
    }

}