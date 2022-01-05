package com.example.baadbank.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentPasswordChangeDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PasswordChangeDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPasswordChangeDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordChangeDialogBinding.inflate(inflater,container,false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply { (this as BottomSheetDialog).behavior.expandedOffset = 350 }
    }





    override fun onDestroy() {
        super.onDestroy()

        _binding=null
    }

}