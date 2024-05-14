package io.github.kdesp73.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.google.firebase.FirebaseApp
import io.github.kdesp73.myapplication.backend.NotificationService
import io.github.kdesp73.myapplication.backend.room.AppDatabase
import io.github.kdesp73.myapplication.ui.routes.Home
import io.github.kdesp73.myapplication.ui.routes.Route
import io.github.kdesp73.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var appContext: Context
        lateinit var room: AppDatabase
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContext = applicationContext

        FirebaseApp.initializeApp(this)

        room = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "local-db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val notificationService = NotificationService(applicationContext)
        notificationService.createNotificationChannel("debug", "Debug", NotificationManager.IMPORTANCE_DEFAULT)
        notificationService.createNotificationChannel("main", "Main", NotificationManager.IMPORTANCE_HIGH)

        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController, startDestination = Route.Home.route + "?msg=Hello World") {
                        composable(
                            route = Route.Home.route + "?msg={msg}",
                            arguments = listOf(
                                navArgument(
                                    name = "msg"
                                ) { defaultValue = "" },
                            )
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getString("msg")
                                ?.let { Home(it) }
                        }
                    }
                }
            }
        }
    }
}
