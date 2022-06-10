package com.novandi.pengirimanpaket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.novandi.pengirimanpaket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var x: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        onPaketChanged()
        onBeratPaketTyping()
        onResetClick()
    }

    private fun onPaketChanged() {
        binding.listPaket.setOnCheckedChangeListener { _, checkedId ->
            x = findViewById(checkedId)
            val paket: String = x.text.toString()
            binding.tarif.setText(if (paket == "Reguler") "10000" else if (paket == "Kilat") "20000" else "25000")
        }
    }

    private fun onBeratPaketTyping() {
        binding.beratPaket.doAfterTextChanged {
            val tarif: String = binding.tarif.text.toString()
            val beratPaket: String = binding.beratPaket.text.toString()
            if (tarif != "" && beratPaket != "") {
                binding.beratPaket.error = null
                countHarga(beratPaket.toInt(), tarif.toInt())
            } else {
                if (beratPaket == "") binding.beratPaket.error = "Berat paket harus diisi"
                if (tarif == "") binding.beratPaket.error = "pilih paket terlebih dahulu"
                binding.total.setText("")
            }
        }
    }

    private fun countHarga(berat: Int, tarif: Int) {
        val total: Int = tarif * berat
        val totals: String = total.toString()
        binding.total.setText(totals)
    }

    private fun onResetClick() {
        binding.btnReset.setOnClickListener {
            binding.listPaket.setOnCheckedChangeListener(null)
            binding.listPaket.clearCheck()
            onPaketChanged()

            binding.tarif.setText("")
            binding.beratPaket.setText("")
            binding.total.setText("")
        }
    }
}