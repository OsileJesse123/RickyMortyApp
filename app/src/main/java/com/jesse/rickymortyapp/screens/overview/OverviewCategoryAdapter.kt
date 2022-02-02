package com.jesse.rickymortyapp.screens.overview

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.jesse.rickymortyapp.R
import com.jesse.rickymortyapp.databinding.CategoriesItemBinding

@RequiresApi(Build.VERSION_CODES.M)
class OverviewCategoryAdapter(private val context: Context, private var categories: List<String>,
                              private var switchCategoryListener:
                              ((status: String, itemPosition: Int) -> Unit))
    : RecyclerView.Adapter<OverviewCategoryAdapter.CategoryViewholder>() {

    var selectedItem = 0

    inner class CategoryViewholder(val binding: CategoriesItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        private val categoryCardView = binding.categoryCardView
        private var status = ""
        init {
            binding.categoryCardView.setOnClickListener {
                selectedItem = adapterPosition
                switchCategoryListener(status, adapterPosition)
                notifyDataSetChanged()
            }
        }

        fun bind(category: String) {
            binding.category = category
            status = category
            binding.executePendingBindings()
        }

        fun setCardColor(position: Int) {
            if (selectedItem == position) {
                categoryCardView.setCardBackgroundColor(context.getColor(R.color.deep_green))
                categoryCardView.animate().scaleX(1.1f)
                categoryCardView.animate().scaleY(1.1f)
            } else {
                categoryCardView.setCardBackgroundColor(context.getColor(R.color.green))
                categoryCardView.animate().scaleX(1f)
                categoryCardView.animate().scaleY(1f)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryBinding = CategoriesItemBinding.inflate(layoutInflater,
            parent, false)
        return CategoryViewholder(categoryBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewholder, position: Int) {
        holder.bind(categories[position])
        holder.setCardColor(position)
    }

    override fun getItemCount(): Int = categories.size
}