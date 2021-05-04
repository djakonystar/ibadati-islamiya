package uz.bismillah.ibadatiislamiya.ui.topic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_topic.view.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.model.Topic

class TopicListAdapter : RecyclerView.Adapter<TopicListAdapter.TopicListViewHolder>() {

    var models = listOf<Topic>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return TopicListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopicListViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size

    inner class TopicListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(topic: Topic) {
            itemView.topicTitleTextView.text = topic.name
        }
    }
}