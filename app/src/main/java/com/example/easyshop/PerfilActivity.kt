package com.example.easyshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.easyshop.databinding.ActivityPerfilBinding
import com.example.easyshop.helper.IsValid
import com.example.easyshop.helper.required

class PerfilActivity : AppCompatActivity() {

    lateinit var binding: ActivityPerfilBinding
    lateinit var controlsValidar: MutableList<View>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Init()

        binding.apply {
            btnAtras.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(
                this@PerfilActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        AlertDialog.Builder(this@PerfilActivity)
                            .setMessage("¿Desea salir?")
                            .setPositiveButton("Si") { _, _ ->
                                finish()

                            }.setNegativeButton("No") { _, _ ->
                                Toast.makeText(
                                    this@PerfilActivity,
                                    "se ha cancelado ",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }.create().show()
                        // No Longer Needs to Callback and Finish
                    }
                })
            btnPrevia.setOnClickListener {
                startActivity(Intent(this@PerfilActivity, PreviaActivity::class.java).apply {
                    putExtra("nombre", txtName.text.toString())
                    putExtra("apellido", txtLastName.text.toString())
                    putExtra("celular", txtPhone.text.toString())
                    putExtra("correo", txtEmail.text.toString())
                })
            }

            btnGuardar.setOnClickListener {
                if (controlsValidar.IsValid()) {
                    getSharedPreferences("db", MODE_PRIVATE).edit()
                        .putString("nombre", txtName.text.toString())
                        .putString("apellido", txtLastName.text.toString())
                        .putString("celular", txtPhone.text.toString())
                        .putString("correo", txtEmail.text.toString()).apply()
                    Toast.makeText(
                        this@PerfilActivity,
                        "se guardo correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun Init() {
        binding.apply {
            txtPhone.required()
            txtName.required()
            txtLastName.required()
            txtEmail.required()
            controlsValidar = mutableListOf(txtName, txtLastName, txtPhone, txtEmail)
            txtName.setText(getSharedPreferences("db", MODE_PRIVATE)!!.getString("nombre", ""))
            txtLastName.setText(
                getSharedPreferences("db", MODE_PRIVATE)!!.getString(
                    "apellido",
                    ""
                )
            )
            txtPhone.setText(getSharedPreferences("db", MODE_PRIVATE)!!.getString("celular", ""))
            txtEmail.setText(getSharedPreferences("db", MODE_PRIVATE)!!.getString("correo", ""))

        }
    }
}