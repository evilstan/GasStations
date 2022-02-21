package com.example.gasstations.data.storage.firebase

import com.example.gasstations.data.storage.models.RefuelModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class FirebaseDb {
    private val db = FirebaseFirestore.getInstance()
    fun put(refuelModel: RefuelModel) {
        println("Putting data")
        db.collection("refuels").
                add(refuelModel).
                addOnSuccessListener { object: OnSuccessListener<DocumentReference>{
                    override fun onSuccess(documentReference: DocumentReference) {
                        println("Object added with ID ${documentReference.id}")
                    }

                } }.
                addOnFailureListener(object: OnFailureListener{
                    override fun onFailure(e: Exception) {
                        println("Error adding object $e")
                    }

                })
    }
}