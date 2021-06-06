package gladun.vlad.weather.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

abstract class BaseListAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _items = ArrayList<T>()
    var items: List<T>
        get() {
            return Collections.unmodifiableList(_items)
        }
        set(value) {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return itemCount
                }

                override fun getNewListSize(): Int {
                    return value.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return areItemsTheSame(_items[oldItemPosition], value[newItemPosition])
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return areContentsTheSame(_items[oldItemPosition], value[newItemPosition])
                }
            })

            _items.apply {
                clear()
                addAll(value)
            }

            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int {
        return _items.size
    }

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}