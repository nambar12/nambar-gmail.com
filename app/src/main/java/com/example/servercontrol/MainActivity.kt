package com.example.servercontrol

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory
import com.amazonaws.regions.Regions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var cognitoProvider : CognitoCachingCredentialsProvider? = null
    private var lambdaFactory : LambdaInvokerFactory? = null
    private var awsInterface : AWSInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        configureAWS()
    }

    fun startMNFT() {
        thread {
            try {
                awsInterface?.startMNFT(StartServerRequest())
            } catch (lfe: LambdaFunctionException) {
                Log.e("Tag", "Failed to invoke echo", lfe)
                null
            }
            runOnUiThread{
                Toast.makeText(this@MainActivity, "Server Started", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun stopMNFT() {
        thread {
            try {
                awsInterface?.stopMNFT(StartServerRequest())
            } catch (lfe: LambdaFunctionException) {
                Log.e("Tag", "Failed to invoke echo", lfe)
                null
            }
            runOnUiThread{
                Toast.makeText(this@MainActivity, "Server Stopped", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun configureAWS() {
        cognitoProvider = CognitoCachingCredentialsProvider(
            this.applicationContext,
            "eu-central-1:7931ce4a-509d-4e28-8055-a2ab5e709cd5",
            Regions.EU_CENTRAL_1
        )

        lambdaFactory = LambdaInvokerFactory.builder()
            .context(applicationContext)
            .region(Regions.EU_CENTRAL_1)
            .credentialsProvider(cognitoProvider).build()

        awsInterface = lambdaFactory?.build(AWSInterface::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
