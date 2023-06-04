package com.example.easyshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import com.example.easyshop.databinding.ActivityDeleteBinding
import com.example.easyshop.databinding.ActivityMainBinding
import com.example.easyshop.helper.IsValid
import com.example.easyshop.helper.required

class DeleteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeleteBinding
    lateinit var controlsValidar: MutableList<View>
    lateinit var listaQuincenal: MutableList<String>
    lateinit var listaMensual: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Init()
        events()
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this@DeleteActivity)
            .setMessage("¿Desea salir?")
            .setPositiveButton("Si") { _, _ ->
                super.onBackPressed()

            }.setNegativeButton("No") { _, _ ->
                Toast.makeText(this@DeleteActivity, "se ha cancelado ", Toast.LENGTH_SHORT)
                    .show()
            }.create().show()

    }

    private fun events() {
        binding.apply {
            btnLogo2.setOnClickListener {
                startActivity(Intent(this@DeleteActivity, PerfilActivity::class.java))
            }
            btnAtras.setOnClickListener {
                onBackPressed()
            }
            rbQuincenalShow.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    txtTitle3.text = "Lista de compra Quincenal"
                    LlenarQuincenal()
                } else {
                    txtTitle3.text = "Lista de compra Mensual"
                    LlenarMensual()
                }
            }
            btnEliminar.setOnClickListener {
                AlertDialog.Builder(this@DeleteActivity)
                    .setMessage("¿Desea eliminar el elemento?")
                    .setPositiveButton("Si") { _, _ ->
                        Eliminar()

                    }.setNegativeButton("No") { _, _ ->
                        Toast.makeText(this@DeleteActivity, "se ha cancelado ", Toast.LENGTH_SHORT)
                            .show()
                    }.create().show()
            }
        }
    }

    private fun Init() {

        binding.apply {
            txtItem2.required()
            controlsValidar = mutableListOf(txtItem2)
        }
        listaQuincenal =
            this.getSharedPreferences("db", MODE_PRIVATE)
                .getString(getString(R.string.quincenal), "")!!.split("\n")
                .toMutableList()
        listaMensual =
            this.getSharedPreferences("db", MODE_PRIVATE)
                .getString(getString(R.string.mensual), "")!!.split("\n")
                .toMutableList()

        LlenarQuincenal()

    }

    private fun LlenarQuincenal() {
        var index = 0
        var lista: MutableList<String> = listaQuincenal
        if (lista.first().isEmpty())
            lista.removeAt(0)
        lista = lista.map { x -> "${++index} $x" } as MutableList<String>
        binding.txtListaCompra.text = lista.joinToString("\n")
    }

    private fun LlenarMensual() {
        var index = 0
        var lista: MutableList<String> = listaMensual
        if (lista.first().isEmpty())
            lista.removeAt(0)
        lista = lista.map { x -> "${++index} $x" } as MutableList<String>
        binding.txtListaCompra.text = lista.joinToString("\n")
    }

    private fun Eliminar() {
        binding.apply {


            if (controlsValidar.IsValid()) {
                if (rbQuincenalShow.isChecked) {
                    if (txtItem2.text.isDigitsOnly()) {
                        var index = txtItem2.text.toString().toInt()
                        if (listaQuincenal.size > index && index > 0) {
                            listaQuincenal.removeAt(index - 1)

                        } else {
                            Toast.makeText(
                                this@DeleteActivity,
                                "No se encontro el elemento a eliminar",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                    } else {
                        var index = listaQuincenal.map { x -> x.lowercase() }
                            .indexOf(txtItem2.text.toString().lowercase())
                        if (index != -1) {
                            listaQuincenal.removeAt(index)
                        } else {
                            Toast.makeText(
                                this@DeleteActivity,
                                "No se encontro el elemento a eliminar",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                    }
                    getSharedPreferences("db", MODE_PRIVATE).edit()
                        .putString(
                            getString(R.string.quincenal),
                            listaQuincenal.joinToString("\n")
                        )
                        .apply()
                    rbQuincenalShow.isChecked = true
                    LlenarQuincenal()
                    txtItem2.setText("")
                    Toast.makeText(
                        this@DeleteActivity,
                        "Se eliminado correctamente el item quincenal",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (txtItem2.text.isDigitsOnly()) {
                        var index = txtItem2.text.toString().toInt()
                        if (listaMensual.size > index && index > 0) {
                            listaMensual.removeAt(index)

                        } else {
                            Toast.makeText(
                                this@DeleteActivity,
                                "No se encontro el elemento a eliminar",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                    } else {
                        var index = listaMensual.map { x -> x.lowercase() }
                            .indexOf(txtItem2.text.toString().lowercase())
                        if (index != -1) {
                            listaMensual.removeAt(index - 1)
                        } else {
                            Toast.makeText(
                                this@DeleteActivity,
                                "No se encontro el elemento a eliminar",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                    }
                    getSharedPreferences("db", MODE_PRIVATE).edit()
                        .putString(
                            getString(R.string.mensual),
                            listaMensual.joinToString("\n")
                        )
                        .apply()
                    rbMensualShow.isChecked = true
                    LlenarMensual()
                    txtItem2.setText("")
                    txtItem2.clearFocus()
                    Toast.makeText(
                        this@DeleteActivity,
                        "Se eliminado correctamente el item mensual",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}