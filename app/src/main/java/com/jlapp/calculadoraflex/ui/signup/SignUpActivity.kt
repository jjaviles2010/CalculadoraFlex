package com.jlapp.calculadoraflex.ui.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jlapp.calculadoraflex.R
import com.jlapp.calculadoraflex.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        btCreate.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(
                inputEmail.text.toString(),
                inputPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    saveInRealtimeDatabase()
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        it.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun saveInRealtimeDatabase() {
        val user = User(
            inputName.text.toString(),
            inputEmail.text.toString(),
            inputPhone.text.toString()
        )

        if(mAuth.currentUser != null) {

        } else {
            FirebaseDatabase
                .getInstance()
                .getReference("Users")
                .child(mAuth.currentUser!!.uid)
                .setValue(user)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val resultIntent = Intent()
                        resultIntent.putExtra("email",
                            inputEmail.text.toString())
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            it.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}
