package com.wrsft.learnkotlin

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.wrsft.learnkotlin.databinding.ActivitySecond2Binding
import java.util.*
import java.util.logging.Logger

class SecondActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecond2Binding
    private  lateinit var log: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log = Logger.getLogger(SecondActivity::class.java.name)

        log.info("SecondActivity::onCreate() start")

        val intentBundle = intent.getBundleExtra("key_bundle")
        log.info("SecondActivity::onCreate() intentBundle: $intentBundle")

        val bundleData = intentBundle?.getString("string_key")

        log.info("onCreate() bundleData: $bundleData")

        binding = ActivitySecond2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_second)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            log.info("onCreate() bundleData: $bundleData")

            Timer("SecondActivity-Timer", false).schedule(
                object : TimerTask() {
                    override fun run() {

                        log.info("SecondActivity-Timer::run() 1 second expired!")
                        this.cancel()

                        val intent =Intent().apply {
                            putExtra("string_key", Bundle().apply {
                                putInt("string_key", 555)
                            } )
                        }

                        log.info("SecondActivity-Timer::run()restul intent: $intent")
                        this@SecondActivity.setResult(7777, intent)

                        log.info("SecondActivity-Timer::run() ending activity")
                        this@SecondActivity.finish()
                    }
                },
                1000
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_second)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}