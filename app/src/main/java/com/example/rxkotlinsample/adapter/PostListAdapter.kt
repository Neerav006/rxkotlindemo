package com.example.rxkotlinsample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxkotlinsample.R
import com.example.rxkotlinsample.model.postmodel.Post

class PostListAdapter(private val context: Context, private val postList: List<Post>, private val onItemClick:(Post, Int)->Unit)
    : RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    var postListFiltered:List<Post> = postList

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val tvName: TextView =  view.findViewById(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.text_row_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postListFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = postListFiltered[position].title+" "+position.toString()
        holder.itemView.setOnClickListener {
            onItemClick(postListFiltered[position],position)
        }
    }
}