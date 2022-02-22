package com.example.gasstations.presentation.main_activity.list

import com.example.gasstations.data.storage.models.RefuelCache

interface OnRefuelLongClickListener {
    fun onLongClick(refuelCache: RefuelCache):Boolean
}

interface OnRefuelClickListener {
    fun onClick(refuelCache:RefuelCache):Boolean
}

interface OnRefuelChangedListener {
    fun onChange(refuelCache:RefuelCache)
}