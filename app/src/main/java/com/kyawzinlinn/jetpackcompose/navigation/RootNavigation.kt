@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.kyawzinlinn.jetpackcompose.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyawzinlinn.jetpackcompose.shared_element_transition.sharedElementNavigationGraph


object RootNavigation {
    const val ROOT = "root_graph"
    const val SHARED_ELEMENT_TRANSITION = "shared_element_transition_graph"
}

interface NavigationRoute {
    val route: String
}

@Composable
fun RootNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    SharedTransitionLayout {
        val scope by remember { derivedStateOf { this@SharedTransitionLayout }}
        NavHost(
            navController = navController,
            startDestination = RootNavigation.SHARED_ELEMENT_TRANSITION,
            route = RootNavigation.ROOT,
            modifier = modifier
        ) {
            sharedElementNavigationGraph(navController = navController, sharedTransitionScope = scope)
        }
    }
}