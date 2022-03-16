package jp.ceed.kart.settings.ui.session.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.model.dto.SessionListItem
import jp.ceed.kart.settings.ui.session.fragment.SessionFragment
import jp.ceed.kart.settings.ui.session.fragment.SessionHeaderFragment

class SessionListAdapter(context: Context, private val fragmentManager: FragmentManager): RecyclerView.Adapter<SessionListAdapter.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_SESSION = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private val inflater = LayoutInflater.from(context)

    private var itemList: List<SessionListItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.session_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sessionListItem: SessionListItem = itemList[position]
        val fragment = if(TYPE_HEADER == getItemViewType(position)){
            SessionHeaderFragment.newInstance(sessionListItem as SessionListItem.SessionHeader)
        }else{
            SessionFragment()
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]){
            is SessionListItem.SessionHeader -> {
                TYPE_HEADER
            }else -> {
                TYPE_SESSION
            }
        }
    }

    fun setItemList(_itemList: List<SessionListItem>){
        itemList = _itemList
    }
}