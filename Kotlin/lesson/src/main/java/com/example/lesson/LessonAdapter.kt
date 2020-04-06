package com.example.lesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseViewHolder
import com.example.lesson.LessonAdapter.LessonViewHolder
import com.example.lesson.entity.Lesson
import java.util.*

class LessonAdapter : RecyclerView.Adapter<LessonViewHolder>() {

    private var list: List<Lesson> = ArrayList()

    internal fun updateAndNotify(list: List<Lesson>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder.onCreate(parent)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    class LessonViewHolder internal constructor(itemView: View) : BaseViewHolder(itemView) {
        internal fun onBind(lesson: Lesson) {
            setText(R.id.tv_date, lesson.date)
            setText(R.id.tv_content, lesson.content)
            setText(R.id.tv_state, lesson.state.stateName())

            val colorRes = when (lesson.state) {
                Lesson.State.PLAYBACK -> R.color.playback
                Lesson.State.LIVE -> R.color.live
                Lesson.State.WAIT -> R.color.wait
            }
            getView<View>(R.id.tv_state)?.setBackgroundColor(itemView.context.getColor(colorRes))
        }

        companion object {
            fun onCreate(parent: ViewGroup): LessonViewHolder {
                return LessonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false))
            }
        }
    }

}