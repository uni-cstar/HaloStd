/*
 * Copyright (C) 2018 Lucio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package halo.stdlib.android.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.BaseAdapter
import java.util.*

abstract class QuickAdapter<T> @JvmOverloads constructor(protected var mCtx: Context,
                                                         protected var mDataList: MutableList<T> = arrayListOf()) : BaseAdapter() {

    override fun getCount(): Int {
        return mDataList.size
    }

    override fun getItem(position: Int): T {
        return mDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun add(elem: T) {
        mDataList.add(elem)
        notifyDataSetChanged()
    }

    fun addAll(elem: List<T>) {
        mDataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun set(oldElem: T, newElem: T) {
        val index = mDataList.indexOf(oldElem)
        if (index >= 0) {
            set(index, newElem)
        }
    }

    operator fun set(index: Int, elem: T) {
        mDataList[index] = elem
        notifyDataSetChanged()
    }

    fun remove(elem: T) {
        mDataList.remove(elem)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        mDataList.removeAt(index)
        notifyDataSetChanged()
    }

    fun replaceAll(elem: List<T>) {
        mDataList.clear()
        mDataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun contains(elem: T): Boolean {
        return mDataList.contains(elem)
    }

    /**
     * Clear data list
     */
    fun clear() {
        mDataList.clear()
        notifyDataSetChanged()
    }
}

abstract class QuickRecycleAdapter<T, VH : RecyclerView.ViewHolder> @JvmOverloads constructor(protected var mCtx: Context,
                                                                                              protected var mDataList: MutableList<T> = ArrayList<T>())
    : RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun getItem(position: Int): T {
        return mDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun add(elem: T) {
        mDataList.add(elem)
        notifyDataSetChanged()
    }

    fun addAll(elem: List<T>) {
        mDataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun set(oldElem: T, newElem: T) {
        val index = mDataList.indexOf(oldElem)
        if (index >= 0) {
            set(index, newElem)
        }
    }

    operator fun set(index: Int, elem: T) {
        mDataList[index] = elem
        notifyDataSetChanged()
    }

    fun remove(elem: T) {
        mDataList.remove(elem)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        mDataList.removeAt(index)
        notifyDataSetChanged()
    }

    fun replaceAll(elem: List<T>) {
        mDataList.clear()
        mDataList.addAll(elem)
        notifyDataSetChanged()
    }

    operator fun contains(elem: T): Boolean {
        return mDataList.contains(elem)
    }

    /**
     * Clear data list
     */
    fun clear() {
        mDataList.clear()
        notifyDataSetChanged()
    }
}
