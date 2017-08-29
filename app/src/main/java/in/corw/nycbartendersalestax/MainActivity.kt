package `in`.corw.nycbartendersalestax

import `in`.corw.nycbartendersalestax.databinding.ActivityMainBinding
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupHandlers()
    }

    fun allFields() = listOf(binding.beer, binding.cocktails, binding.food, binding.wine)

    fun setupHandlers() {
        val listener = object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = updateTotals()
        }

        allFields().forEach { it.addTextChangedListener(listener) }
    }

    fun updateTotals() {
        val subtotal = allFields().map { floatOrZero(it.text.toString()) }.sum()
        val tax = subtotal * .08875f
        binding.subtotal.text = asPrice(subtotal)
        binding.tax.text = asPrice(tax)
        binding.total.text = asPrice(subtotal + tax)
    }

    fun floatOrZero(s: String) : Float {
        return try {
            s.toFloat()
        } catch (e: NumberFormatException) {
            0f
        }
    }

    fun asPrice(amount: Float) = String.format("%.2f", amount)
}
