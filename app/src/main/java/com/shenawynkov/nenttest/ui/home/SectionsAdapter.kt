package com.shenawynkov.nenttest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shenawynkov.domain.model.section.Section
import com.shenawynkov.nenttest.R

class SectionsAdapter(var list: List<Section>, val sectionsListener: SectionsListener) :
    RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvTitle: TextView
        val ivFav: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            tvName = view.findViewById(R.id.tv_name)
            tvTitle = view.findViewById(R.id.tv_title)
            ivFav = view.findViewById(R.id.iv_fav)
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

        val section = list[position]
        viewHolder.tvTitle.text = section.title
        viewHolder.tvName.text = section.name
        setFav(section.fav, viewHolder.ivFav)

        viewHolder.itemView.setOnClickListener {
            sectionsListener.onSectionSelected(list[viewHolder.adapterPosition])
        }

        viewHolder.ivFav.setOnClickListener {
            list[viewHolder.adapterPosition].fav = !list[viewHolder.adapterPosition].fav

            setFav(list[viewHolder.adapterPosition].fav, it as ImageView)

            sectionsListener.onFavChanged(
                list[viewHolder.adapterPosition].fav,
                list[viewHolder.adapterPosition].id
            )
        }


    }

    override fun getItemCount() = list.size


    fun setNewList(newList: List<Section>) {
        this.list = newList;
        notifyDataSetChanged()
    }

    interface SectionsListener {
        fun onSectionSelected(section: Section)
        fun onFavChanged(fav: Boolean, id: String)
    }

    private fun setFav(fav: Boolean, imageView: ImageView) {
        when (fav) {
            true -> imageView.setImageResource(R.drawable.star_filled)
            false -> imageView.setImageResource(R.drawable.star)
        }
    }
}
