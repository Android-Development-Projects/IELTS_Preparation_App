package com.wolfbytetechnologies.ielts.ui.listening.listeningLessons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wolfbytetechnologies.ielts.data.LessonItems
import com.wolfbytetechnologies.ielts.databinding.LessonMainTopicItemsBinding

class ListeningMainTopicsAdapter(
    private val itemArrayList: Array<LessonItems>,
    val itemOnClick : (LessonItems) -> Unit
) :
    RecyclerView.Adapter<ListeningMainTopicsAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: LessonMainTopicItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = LessonMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemArrayList[position]
        holder.binding.apply {
            "${position+1}.".also { tvNumbering.text = it }
            tvTitle.text = currentItem.title
            tvSection.text = currentItem.section
            tvLessonType.text = currentItem.mainTopic
        }
        holder.itemView.setOnClickListener {
            itemOnClick(currentItem)
        }
    }

    override fun getItemCount(): Int = itemArrayList.size
}