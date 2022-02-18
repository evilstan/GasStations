package com.example.gasstations.presentation.main_activity.list

import com.example.gasstations.domain.models.RefuelDomain

interface OnRefuelLongClickListener {
    fun onLongClick(refuelDomain:RefuelDomain):Boolean
}