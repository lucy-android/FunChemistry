package site.budanitskaya.chemistryquiz.fine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import site.budanitskaya.chemistryquiz.fine.ui.login.LoginActivity
import site.budanitskaya.chemistryquiz.fine.ui.notifications.NotificationUtil

class MainActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance().currentUser

    private lateinit var navView: BottomNavigationView

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navController by lazy {
        this.findNavController(R.id.nav_host_fragment_activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        actionBar?.show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navView = findViewById(R.id.nav_view)
        navView.itemRippleColor = getColorStateList(R.color.colorPrimary)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.quizListFragment, R.id.navigation_game, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.quizListFragment -> navView.visibility = View.VISIBLE
                R.id.navigation_game -> navView.visibility = View.VISIBLE
                R.id.navigation_notifications -> navView.visibility = View.VISIBLE
                R.id.questionFragment -> navView.visibility = View.GONE
                R.id.gameOverFragment -> navView.visibility = View.GONE
                R.id.chemChipsQuestionFragment -> navView.visibility = View.GONE
                R.id.crosswordFragment -> navView.visibility = View.GONE
                R.id.chipsOverFragment -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signout -> {
                signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
                Toast.makeText(this, "Successfully Log Out", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        if (auth != null && intent != null) {
            createUI()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        }
    }

    fun createUI() {
        val list = auth?.providerData
        var providerData: String = ""
        list?.let {
            for (provider in list) {
                providerData = providerData + " " + provider.providerId
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        return when (navController.currentDestination?.id) {
            R.id.chipsOverFragment -> {
                // custom behavior here
                navController.navigate(R.id.action_chipsOverFragment_to_navigation_game)
                true
            }
            R.id.gameOverFragment -> {
                // custom behavior here
                navController.navigate(R.id.action_gameOverFragment_to_quizListFragment)
                true
            }
            R.id.chemChipsQuestionFragment -> {
                // custom behavior here
                navController.navigate(R.id.action_chemChipsQuestionFragment_to_navigation_game)
                true
            }

            R.id.questionFragment -> {
                // custom behavior here
                navController.navigate(R.id.action_questionFragment_to_quizListFragment)
                true
            }


            else -> NavigationUI.navigateUp(navController, appBarConfiguration)
        }
    }
}