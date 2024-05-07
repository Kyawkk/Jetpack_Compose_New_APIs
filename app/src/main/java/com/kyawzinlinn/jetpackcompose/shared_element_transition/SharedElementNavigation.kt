package com.kyawzinlinn.jetpackcompose.shared_element_transition

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kyawzinlinn.jetpackcompose.navigation.RootNavigation

fun NavHostController.navigateSharedElementDetail(image: Int, index: Int, title: String) {
    navigate("${SharedElementDetailRoute.route}/$image/$index/$title")
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.sharedElementNavigationGraph(sharedTransitionScope: SharedTransitionScope, navController: NavHostController) {
    navigation(
        startDestination = SharedElementItemListRoute.route,
        route = RootNavigation.SHARED_ELEMENT_TRANSITION
    ) {
        sharedElementItemList (sharedTransitionScope = sharedTransitionScope, onItemClick = navController::navigateSharedElementDetail)

        sharedElementDetailScreen(sharedTransitionScope)
    }
}