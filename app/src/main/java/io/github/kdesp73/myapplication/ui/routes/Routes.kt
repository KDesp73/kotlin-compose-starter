package io.github.kdesp73.myapplication.ui.routes

import androidx.annotation.StringRes
import io.github.kdesp73.myapplication.R

sealed class Route (val route: String, @StringRes val resId: Int){
    data object Home : Route(route = "home", resId = R.string.route_home)
}
