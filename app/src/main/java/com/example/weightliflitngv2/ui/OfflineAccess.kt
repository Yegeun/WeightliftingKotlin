package com.example.weightliflitngv2.ui
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

@SuppressLint("Registered")
class OfflineAccess: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // [START rtdb_keep_synced]
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        val athletes = FirebaseDatabase.getInstance().getReference("athletes")
        athletes.keepSynced(true)
        // [END rtdb_keep_synced]
    }
}