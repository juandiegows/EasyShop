package com.example.easyshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.easyshop.databinding.ActivityMainBinding
import com.example.easyshop.helper.IsValid
import com.example.easyshop.helper.required

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var controlsValidar: MutableList<View>
    lateinit var listaQuincenal: MutableList<String>
    lateinit var listaMensual: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Init()
        events()

    }

    private fun Init() {

        binding.apply {
            txtItem.required()
            controlsValidar = mutableListOf(txtItem)
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

    private fun events() {
        binding.apply {
            btnAgregar.setOnClickListener {
                Save()
            }

            btnLogo.setOnClickListener {
                startActivity(Intent(this@MainActivity, PerfilActivity::class.java))
            }
            rbQuincenalShow.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    txtTitle.text = "Lista de compra Quincenal"
                    LlenarQuincenal()
                } else {
                    txtTitle.text = "Lista de compra Mensual"
                    LlenarMensual()
                }
            }
            btnEliminar.setOnClickListener {
                startActivity(Intent(this@MainActivity, DeleteActivity::class.java))
            }
        }
    }


    private fun Save() {
        binding.apply {


            if (controlsValidar.IsValid()) {
                if (rbQuincenal.isChecked) {
                    listaQuincenal.add(txtItem.text.toString())
                    getSharedPreferences("db", MODE_PRIVATE).edit()
                        .putString(
                            getString(R.string.quincenal),
                            listaQuincenal.joinToString("\n")
                        )
                        .apply()
                    rbQuincenalShow.isChecked = true
                    LlenarQuincenal()
                    txtItem.setText("")
                    Toast.makeText(
                        this@MainActivity,
                        "Se agregado correctamen el item quincenal",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    listaMensual.add(txtItem.text.toString())
                    getSharedPreferences("db", MODE_PRIVATE).edit()
                        .putString(
                            getString(R.string.mensual),
                            listaMensual.joinToString("\n")
                        )
                        .apply()
                    rbMensualShow.isChecked = true
                    LlenarMensual()
                    txtItem.setText("")
                    txtItem.clearFocus()
                    Toast.makeText(
                        this@MainActivity,
                        "Se agregado correctamen el item mensual",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}