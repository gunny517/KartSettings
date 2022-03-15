package jp.ceed.kart.settings.ui.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.TrackSpinnerItemBinding
import jp.ceed.kart.settings.model.entity.Track

class TrackSpinnerAdapter(context: Context, private val trackList: List<Track>): ArrayAdapter<Track>(context, 0) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val binding: TrackSpinnerItemBinding = DataBindingUtil.inflate(inflater, R.layout.track_spinner_item, parent, false)
//        binding.setVariable(BR.track, trackList[position])
//        return binding.root
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView: TextView = inflater.inflate(android.R.layout.simple_spinner_item, parent, true) as TextView
        textView.text = trackList[position].name
        return textView
    }

    override fun getItem(position: Int): Track? {
        return trackList[position]
    }

}