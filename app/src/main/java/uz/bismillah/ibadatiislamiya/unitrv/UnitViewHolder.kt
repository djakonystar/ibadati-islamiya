package uz.bismillah.ibadatiislamiya.unitrv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_unit.view.*

class UnitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun populateUnit(unit: UnitModel) {
        itemView.unitTitle.text = unit.title
    }
}