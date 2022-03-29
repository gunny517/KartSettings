package jp.ceed.kart.settings.domain.repository

class FinalRatioRepository {

    companion object {
        const val DRIVE_MIN = 10
        const val DRIVEN_MIN = 80
        const val DRIVEN_MAX = 95
        const val COL_SIZE = 5
    }

    fun getFinalRatioData(): List<String> {
        val driveList: ArrayList<Int> = ArrayList()
        for(i in 0 until COL_SIZE - 1){
            driveList.add(DRIVE_MIN + i)
        }
        val list: ArrayList<String> = ArrayList()
        list.add("")
        for(drive in driveList){
            list.add(drive.toString())
        }
        for(driven in DRIVEN_MIN..DRIVEN_MAX){
            list.add(driven.toString())
            for(drive in driveList){
                val ratio: Float = driven.toFloat() / drive.toFloat()
                list.add(String.format("%.2f", ratio))
            }
        }
        return list
    }

}