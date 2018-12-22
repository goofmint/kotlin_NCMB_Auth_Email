package jp.moongift.ncmbauthemail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import jp.moongift.ncmbauthemail.BuildConfig
import com.nifcloud.mbaas.core.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NCMB.initialize(applicationContext, BuildConfig.APPLICATION_KEY, BuildConfig.CLIENT_KEY)
        var btnRegister : Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            this.signUp()
        }

        var btnSignIn : Button = findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            this.btnSignIn()
        }

        var user = NCMBUser.getCurrentUser()
        var lblStats = findViewById<TextView>(R.id.lblStats)
        if (user.isAuthenticated) {
            lblStats.text = "Login from ${user.mailAddress}"
        }

    }

    fun signUp() {
        var email = findViewById<TextView>(R.id.txtEmail).text.toString()
        var lblStats = findViewById<TextView>(R.id.lblStats)
        NCMBUser.requestAuthenticationMailInBackground(email, { e ->
            if (e != null) {
                lblStats.text = e.localizedMessage
            } else {
                lblStats.text = "Sent email to ${email}."
            }
        })

    }

    fun btnSignIn() {
        var email = findViewById<TextView>(R.id.txtEmail).text.toString()
        var password = findViewById<TextView>(R.id.txtPassword).text.toString()
        var lblStats = findViewById<TextView>(R.id.lblStats)
        NCMBUser.loginWithMailAddressInBackground(email, password, { user, e ->
            if (e != null) {
                lblStats.text = e.localizedMessage
            } else {
                lblStats.text = "Login successful by ${email}"
            }
        })
    }
}
