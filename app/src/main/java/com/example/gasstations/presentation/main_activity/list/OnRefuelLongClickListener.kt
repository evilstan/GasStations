package com.example.gasstations.presentation.main_activity.list

import com.example.gasstations.data.storage.models.RefuelModel

interface OnRefuelLongClickListener {
    fun onLongClick(refuelModel: RefuelModel):Boolean
}

interface OnRefuelClickListener {
    fun onClick(refuelModel:RefuelModel):Boolean
}

interface OnRefuelChangedListener {
    fun onChange(refuelModel:RefuelModel)
}