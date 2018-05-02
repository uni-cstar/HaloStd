/**
 * Created by SupLuo on 2016/7/27.
 */
package halo.android.content

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.BaseAdapter
import java.util.ArrayList

abstract class QuickAdapter<T> : BaseAdapter {

    protected var dataList: MutableList<T>

    protected var mCtx: Context

    constructor(ctx: Context) : this(ctx, arrayListOf())

    constructor(ctx: Context, datas: List<T>) {
        this.mCtx = ctx
        dataList = datas.toMutableList()
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun add(elem: T) {
        dataList.add(elem)
        notifyDataSetChanged()
    }

    fun addAll(elem: List<T>) {
        dataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun set(oldElem: T, newElem: T) {
        val index = dataList.indexOf(oldElem)
        if (index >= 0) {
            set(index, newElem)
        }
    }

    operator fun set(index: Int, elem: T) {
        dataList[index] = elem
        notifyDataSetChanged()
    }

    fun remove(elem: T) {
        dataList.remove(elem)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        dataList.removeAt(index)
        notifyDataSetChanged()
    }

    fun replaceAll(elem: List<T>) {
        dataList.clear()
        dataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun contains(elem: T): Boolean {
        return dataList.contains(elem)
    }

    /**
     * Clear data list
     */
    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

}

abstract class QuickRecycleAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    protected var dataList: MutableList<T>

    protected var mContext: Context

    constructor(ctx: Context) : this(ctx, ArrayList<T>())

    constructor(ctx: Context, datas: List<T>) {
        this.mContext = ctx
        dataList = datas.toMutableList()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun add(elem: T) {
        dataList.add(elem)
        notifyDataSetChanged()
    }

    fun addAll(elem: List<T>) {
        dataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun set(oldElem: T, newElem: T) {
        val index = dataList.indexOf(oldElem)
        if (index >= 0) {
            set(index, newElem)
        }
    }

    operator fun set(index: Int, elem: T) {
        dataList[index] = elem
        notifyDataSetChanged()
    }

    fun remove(elem: T) {
        dataList.remove(elem)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        dataList.removeAt(index)
        notifyDataSetChanged()
    }

    fun replaceAll(elem: List<T>) {
        dataList.clear()
        dataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun contains(elem: T): Boolean {
        return dataList.contains(elem)
    }

    /**
     * Clear data list
     */
    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

}
