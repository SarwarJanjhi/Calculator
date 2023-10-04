package Cac.calculator

import Cac.calculator.databinding.ActivityMainBinding
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    var  lastnumeric = false
    var  stateError = false
    var lastDot = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


    }


    fun OnDigitClick(view: android.view.View) {
        if (stateError){
            binding.tvCalcualte.text = (view as Button).text
            stateError = false

        }else{
            binding.tvCalcualte.append((view as Button).text)
        }

        lastnumeric =true
        onEqual()


    }

    fun OnOperateClick(view: View) {
        if (!stateError && lastnumeric){
            binding.tvCalcualte.append((view as Button).text)
            lastDot= false
            lastnumeric = false
            onEqual()


        }
    }

    fun OnClearClick(view: View) {
        binding.tvCalcualte.text= ""
        lastnumeric = false

    }



    fun OnBackClick(view: View) {
        binding.tvCalcualte.text = binding.tvCalcualte.text.toString().dropLast(1)
        try {
            var lastchar = binding . tvCalcualte.text.toString().last()

            if(lastchar.isDigit()){
                onEqual()
            }
        }catch (e: Exception){
            binding.tvResult.text =""
            binding.tvResult.visibility = View.GONE
            Log.e("last char error", e.toString())
        }


    }



    fun OnEqualClick(view: View) {

        onEqual()
        binding.tvCalcualte.text = binding.tvResult.text.toString()
    }




    fun OnAllCClearlcik(view: View) {

        binding.tvCalcualte.text = ""
        binding.tvResult.text = ""
        lastnumeric = false
        stateError = false
        lastDot = false
        binding.tvResult.visibility = View.GONE
    }



    fun onEqual (){
        if(lastnumeric && !stateError){
            val txt = binding.tvCalcualte.text.toString()

            expression = ExpressionBuilder(txt).build()


            try {
                var result = expression.evaluate()

                binding .tvResult.visibility = View.VISIBLE

                binding.tvResult.text = result.toString()

            }catch (ex:ArithmeticException){

                Log.e("evaluate error", ex.toString())
                binding.tvResult.text = "Error"
                stateError = true
                lastnumeric = false
            }
        }



    }

}