package com.metropolitan.prviprimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.metropolitan.prviprimer.baza.Baza
import com.metropolitan.prviprimer.databinding.ActivityMainBinding

//https://medium.com/@manuaravindpta/sqlite-integration-in-kotlin-3f30bb3047e4
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    internal var helper = Baza(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        registerForContextMenu(binding.slikaMeta)

        binding.btnDodajte.setOnClickListener {
            if (helper.insertData(
                    binding.etStudentIme.text.toString(),
                    binding.etStudentFakultet.text.toString(),
                    binding.etStudentSmer.text.toString()
                )
            ) {
                Toast.makeText(applicationContext, "Uspesno ste uneli studenta", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnObrisite.setOnClickListener {
            if (binding.etStudentid.text.toString() == "") {
                Toast.makeText(
                    applicationContext,
                    "Niste uneli broj indeksa",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                helper.deleteData(binding.etStudentid.text.toString())
                Toast.makeText(
                    applicationContext,
                    "Uspesno ste obrisali studenta",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            Log.d("Svi studenti: ", helper.getAllStudentData().toString())
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.zacontext, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {


        return when (item.itemId) {
            R.id.fit -> {
                binding.vebvju.loadUrl("https://www.metropolitan.ac.rs/osnovne-studije/fakultet-informacionih-tehnologija/")
                return true
            }
            R.id.fam -> {
                binding.vebvju.loadUrl("https://www.metropolitan.ac.rs/osnovne-studije/fakultet-za-menadzment/")
                return true
            }
            R.id.fdu -> {
                binding.vebvju.loadUrl("https://www.metropolitan.ac.rs/fakultet-digitalnih-umetnosti-2/")
                return true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (binding.etStudentid.text.toString() == "") {
            Toast.makeText(
                applicationContext,
                "Niste uneli id",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val podaciStudenta =
                helper.getParticularStudentData(binding.etStudentid.text.toString().toInt())
            if (id == R.id.prikazi_studenta) {
                binding.tvPodaci.text = podaciStudenta.uzmiIme()
                return true
            }
            if (id == R.id.prikazi_fakultet) {
                binding.tvPodaci.text = podaciStudenta.uzmiFakultet()
                return true
            }
            if (id == R.id.prikazi_smer) {
                binding.tvPodaci.text = podaciStudenta.uzmiSmer()
                return true
            }
        }


        return super.onOptionsItemSelected(item)
    }

}