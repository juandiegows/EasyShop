package com.example.easyshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.easyshop.databinding.ActivityPreviaBinding

class PreviaActivity : AppCompatActivity() {

    lateinit var binding: ActivityPreviaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(
            this@PreviaActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(this@PreviaActivity)
                        .setMessage("¿Desea salir?")
                        .setPositiveButton("Si") { _, _ ->
                            finish()

                        }.setNegativeButton("No") { _, _ ->
                            Toast.makeText(
                                this@PreviaActivity,
                                "se ha cancelado ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }.create().show()
                    // No Longer Needs to Callback and Finish
                }
            })

        binding.apply {
            btnAtras.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            txtName.setText(intent.getStringExtra("nombre"))
            txtLastName.setText(intent.getStringExtra("apellido"))
            txtPhone.setText(intent.getStringExtra("celular"))
            txtEmail.setText(intent.getStringExtra("correo"))
        }
    }


}