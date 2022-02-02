package com.shenawynkov.nenttest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R

class SectionsAdapter(var list: List<Section>,val sectionsListener: SectionsListener): RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val tvName: TextView
         val tvTitle: TextView

        init {
            // Define click listener for the ViewHolder's View.
            tvName = view.findViewById(R.id.tv_name)
            tvTitle = view.findViewById(R.id.tv_title)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_section, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        viewHolder.tvTitle.text = list.get(position).title
        viewHolder.tvName.text = list.get(position).name
        viewHolder.itemView.setOnClickListener {
             sectionsListener.onSectionSelected(list[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount() = list.size


    fun setNewList( newList: List<Section>)
    {
        this.list=newList;
        notifyDataSetChanged()
    }

    interface  SectionsListener{
       fun  onSectionSelected(section:Section)

    }
}
