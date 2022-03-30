package jp.ceed.kart.settings.domain.repository

class FinalRatioRepository {

    companion object {
        const val DRIVE_MIN = 10
        const val DRIVE_MAX = 13
        const val DRIVEN_MIN = 80
        const val DRIVEN_MAX = 95
    }

    fun getFinalRatioData(_driveMin: String?, _driveMax: String?, _drivenMin: String?, _drivenMax: String?): Pair<List<String>, Int> {
        val driveMin: Int = _driveMin?.toInt() ?: DRIVE_MIN
        val driveMax: Int = _driveMax?.toInt() ?: DRIVE_MAX
        val drivenMin: Int = _drivenMin?.toInt() ?: DRIVEN_MIN
        val drivenMax: Int = _drivenMax?.toInt() ?: DRIVEN_MAX

        val driveList: ArrayList<Int> = ArrayList()
        for(i in driveMin..driveMax){
            driveList.add(i)
        }
        val list: ArrayList<String> = ArrayList()
        list.add("")
        for(drive in driveList){
            list.add(drive.toString())
        }
        for(driven in drivenMin..drivenMax){
            list.add(driven.toString())
            for(drive in driveList){
                val ratio: Float = driven.toFloat() / drive.toFloat()
                list.add(String.format("%.2f", ratio))
            }
        }
        return Pair(list, driveList.size + 1)
    }

}