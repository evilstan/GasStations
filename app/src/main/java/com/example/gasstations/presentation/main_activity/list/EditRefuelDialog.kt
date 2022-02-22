package com.example.gasstations.presentation.main_activity.list


import android.app.Dialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gasstations.R
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.databinding.DialogEditRefuelBinding
import com.example.gasstations.presentation.MoneyFuelInputFilter

class EditRefuelDialog(
    private val onRefuelChangedListener: OnRefuelChangedListener,
    private val refuelCache: RefuelCache
) :
    DialogFragment() {

    private var _binding: DialogEditRefuelBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: AlertDialog


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        _binding = DialogEditRefuelBinding.inflate(layoutInflater)
        dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
        dialog.setContentView(binding.root)

        initComponents()
        return dialog
    }

    private fun initComponents() {
        binding.spinnerFuelType.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fuel_types, R.layout.spinner_item
        )

        binding.editPrice.filters = arrayOf(MoneyFuelInputFilter())
        binding.editVolume.filters = arrayOf(MoneyFuelInputFilter())
        binding.editPrice.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) addStation()
            true
        }

        binding.spinnerFuelType.setSelection(
            resources.getStringArray(R.array.fuel_types)
                .indexOf(refuelCache.fuelType)
        )
        binding.editVolume.setText(refuelCache.fuelVolume.toString())
        binding.editPrice.setText(refuelCache.fuelPrice.toString())

        binding.btnUpdate.setOnClickListener { addStation() }
        binding.btnCancel.setOnClickListener { dialog.cancel() }
    }

    private fun addStation() {
        when {
            binding.editVolume.text.isNullOrEmpty() -> {
                binding.editVolume.requestFocus()
            }
            binding.editPrice.text.isNullOrEmpty() -> {
                binding.editPrice.requestFocus()
            }
            else -> {
                val type = binding.spinnerFuelType.selectedItem.toString()
                val volume = binding.editVolume.text.toString().toDouble()
                val price = binding.editPrice.text.toString().toDouble()

                if (type == refuelCache.fuelType &&
                    volume == refuelCache.fuelVolume &&
                    price == refuelCache.fuelPrice
                ) {
                    dialog.cancel()
                } else {
                    onRefuelChangedListener.onChange(
                        RefuelCache(
                            refuelCache.brand,
                            refuelCache.latitude,
                            refuelCache.longitude,
                            type,
                            volume,
                            price,
                            refuelCache.id,
                            uploaded = false,
                            deleted = false
                        )
                    )
                    dialog.cancel()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}