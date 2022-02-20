package com.example.gasstations.presentation.map_activity


import android.app.Dialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gasstations.R
import com.example.gasstations.databinding.DialogAddRefuelBinding
import com.example.gasstations.presentation.MoneyFuelInputFilter

class AddRefuelDialog(private val onRefuelAddListener: OnRefuelAddListener) :
    DialogFragment() {

    private var _binding: DialogAddRefuelBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: AlertDialog


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        _binding = DialogAddRefuelBinding.inflate(layoutInflater)
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

        binding.btnAdd.setOnClickListener { addStation() }
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
                onRefuelAddListener.addRefuel(
                    binding.spinnerFuelType.selectedItem.toString(),
                    binding.editVolume.text.toString().toDouble(),
                    binding.editPrice.text.toString().toDouble()
                )
                dialog.cancel()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}