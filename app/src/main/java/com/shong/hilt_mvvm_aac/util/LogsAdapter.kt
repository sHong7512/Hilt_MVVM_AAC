package com.shong.hilt_mvvm_aac.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shong.hilt_mvvm_aac.R
import com.shong.hilt_mvvm_aac.data.db.AppLog
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@FragmentScoped
class LogsAdapter @Inject constructor()
    : RecyclerView.Adapter<LogsAdapter.LogsViewHolder>() {

    @Inject lateinit var dateFormatter: LogDateFormatter
    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    private var logsDataSet: List<AppLog> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_log, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${dateFormatter.formatDate(log.timestamp)}"
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateLogsData(logs: List<AppLog>){
        this.logsDataSet = logs
        notifyDataSetChanged()
    }
}