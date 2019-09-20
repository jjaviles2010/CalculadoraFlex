package com.jlapp.calculadoraflex.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.jlapp.calculadoraflex.R
import com.jlapp.calculadoraflex.ui.form.FormActivity
import com.jlapp.calculadoraflex.ui.signup.SignUpActivity
import com.jlapp.calculadoraflex.utils.DatabaseUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if(mAuth.currentUser != null) {
            goToHome()
        }

        btLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(
                inputLoginEmail.text.toString(),
                inputLoginPassword.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful) {
                    goToHome()
                } else {
                    Toast.makeText(
                        this,
                        it.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btSignup.setOnClickListener {
            startActivityForResult(Intent(this,
                SignUpActivity::class.java), 100)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == Activity.RESULT_OK) {
            inputLoginEmail.setText(data?.getStringExtra("email"))
        }
    }

    private fun goToHome() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) {
                instanceIdResult ->
            val newToken = instanceIdResult.token
            DatabaseUtil.saveToken(newToken)
        }

        val intent = Intent(this, FormActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}
