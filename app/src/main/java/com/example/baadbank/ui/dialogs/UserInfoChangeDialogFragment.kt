package com.example.baadbank.ui.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentUserInfoChangeDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserInfoChangeDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentUserInfoChangeDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserInfoChangeDialogBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")

        setListeners()



    }


    private fun setListeners(){
        binding.btnSave.setOnClickListener {

            saveUserInformation()

        }



    }

    private fun saveUserInformation() {
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        userReference.child("fullName").setValue(binding.etFullName.text.toString())
        userReference.child("phoneNumber").setValue(binding.etPhoneNumber.text.toString())

    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}