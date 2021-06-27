package com.cleberschuster.roomdatabase.jetpack.ui.subiscriberlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cleberschuster.roomdatabase.jetpack.data.db.entity.SubscriberEntity
import com.cleberschuster.roomdatabase.jetpack.databinding.SubscriberItemBinding

class SubscriberListAdapter(
    private val subscribersList: List<SubscriberEntity>,
    private val onSubscriberClickListener: (subscriber: SubscriberEntity) -> Unit
) : RecyclerView.Adapter<SubscriberListAdapter.SubscriberListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberListViewHolder {
        val binding = SubscriberItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SubscriberListViewHolder(binding, onSubscriberClickListener)
    }

    override fun onBindViewHolder(holder: SubscriberListViewHolder, position: Int) {
        holder.bindView(subscribersList[position])
    }

    override fun getItemCount(): Int = subscribersList.size

    class SubscriberListViewHolder(binding: SubscriberItemBinding,
        private val onSubscriberClickListener: (subscriber: SubscriberEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val textViewSubscriberName: TextView = binding.textSubscriberName
        private val textViewSubscriberEmail: TextView = binding.textSubscriberEmail

        fun bindView(subscriber: SubscriberEntity) {
            textViewSubscriberName.text = subscriber.name
            textViewSubscriberEmail.text = subscriber.email

            itemView.setOnClickListener {
                onSubscriberClickListener.invoke(subscriber)
            }
        }
    }
}