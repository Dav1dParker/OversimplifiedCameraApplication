package com.example.cameraapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class FileListAdapter(private val context: Context) :
    RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    private val dataList: MutableList<String> = mutableListOf()

    fun loadData() {
        val file = File(context.getExternalFilesDir("photos"), "date.txt")
        if (file.exists()) {
            dataList.clear()
            val reader = BufferedReader(FileReader(file))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                dataList.add(line!!)
            }
            reader.close()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(data: String) {
            textView.text = data
        }
    }
}