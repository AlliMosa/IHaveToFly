package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRecipe : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etAuthor: EditText
    private lateinit var etIngredients: EditText
    private lateinit var etInstructions: EditText
    private lateinit var btSubmit: Button
    private lateinit var btCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        etTitle = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)
        etIngredients = findViewById(R.id.etIngredients)
        etInstructions = findViewById(R.id.etInstructions)
        btSubmit = findViewById(R.id.btSubmit)
        btSubmit.setOnClickListener {
            if(etTitle.text.isNotEmpty() && etAuthor.text.isNotEmpty()){
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

                apiInterface?.addRecipe(
                    Recipe(
                        0,
                        etTitle.text.toString(),
                        etAuthor.text.toString(),
                        etIngredients.text.toString(),
                        etInstructions.text.toString()
                    )
                )!!.enqueue(object: Callback<Recipe> {
                    override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                        val intent = Intent(this@AddRecipe, MainActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<Recipe>, t: Throwable) {
                        Toast.makeText(this@AddRecipe, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                })
            }else{
                Toast.makeText(this@AddRecipe, "Please enter title and author", Toast.LENGTH_LONG).show()
            }
        }
        btCancel = findViewById(R.id.btCancel)
        btCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}