package com.wrsft.learnkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.wrsft.learnkotlin.databinding.ActivitySecondBinding
import java.util.logging.Logger

//import kotlinx.android
//https://developer.android.com/topic/libraries/view-binding


private lateinit var binding : ActivitySecondBinding
private lateinit var log: Logger

class MainActivity : AppCompatActivity() {

    var counter = 22

    object Contract : ActivityResultContract<String, Int>() {

        lateinit var payload: Bundle

        override fun createIntent(context: Context, input: String?): Intent {

            log.info("returning intent from ActivituResultContract with bundle $payload")
            return Intent(context, SecondActivity::class.java ).apply {
                putExtra("key_bundle", payload)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Int {
            log.info("parseResultCode called--- resultCode: $resultCode \nintent: $intent")
            val bundle = intent?.getBundleExtra("string_key")
            log.info("parseResult -- bundle : $bundle")
            val resultFromBundle = bundle?.getInt("string_key") ?: 0
            log.info("parseResult -- resultFromBundle : $resultFromBundle")
            return resultFromBundle
        }
    }

    private val resultRegistration =
        registerForActivityResult(
           Contract,
            object: ActivityResultCallback<Int>{
                override fun onActivityResult(result: Int?) {
                    log.info("ActivityResultCallback:onActivityResult()result : $result")
                }
            }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log = Logger.getLogger(this.javaClass.name)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root) //R.layout.activity_second

        binding.btnCounter.setOnClickListener {
            binding.textView.text = "Bonjour mon ami $counter"
            counter++

            //val intent = Intent(this@MainActivity, SecondActivity::class.java )
            val bundle = Bundle()
            bundle.putString("string_key", "bonjouar friend")

            log.info("created bundle: $bundle")
            //val activityForResult = object : ActivityResultContract {}
            //startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE, bundle)

            Contract.payload = bundle

            resultRegistration.launch("Starting second Activity")

        }
    }
}