package com.kyawzinlinn.jetpackcompose.shared_element_transition

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kyawzinlinn.jetpackcompose.R
import com.kyawzinlinn.jetpackcompose.navigation.NavigationRoute

val SharedElementDetailRoute: NavigationRoute = object : NavigationRoute {
    override val route: String = "shared_element_detail"
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.sharedElementDetailScreen(sharedTransitionScope: SharedTransitionScope) {
    composable(route = "${SharedElementDetailRoute.route}/{image}/{index}/{title}",
        arguments = listOf(
            navArgument("image") { type = NavType.IntType },
            navArgument("index") { type = NavType.IntType },
            navArgument("title") { type = NavType.StringType }
        )) {
        val image = it.arguments?.getInt("image") ?: 0
        val index = it.arguments?.getInt("index") ?: 0
        val title = it.arguments?.getString("title") ?: ""
        val scope by remember { derivedStateOf { sharedTransitionScope } }
        val img by remember { derivedStateOf { image } }
        scope.DetailScreen(
            image = img,
            index = index,
            title = title,
            animatedVisibilityScope = this
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    image: Int,
    title: String,
    modifier: Modifier = Modifier,
    index: Int
) {
    val scope by remember { derivedStateOf { animatedVisibilityScope } }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/$index"),
                        animatedVisibilityScope = scope,
                        boundsTransform = { _, _ ->
                            tween(500, easing = FastOutSlowInEasing)
                        }
                    )
                    .aspectRatio(16 / 9f))
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(text = title, modifier = Modifier.sharedElement(
                state = rememberSharedContentState(key = "text/$index"),
                animatedVisibilityScope = scope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 500, easing = FastOutSlowInEasing)
                }
            ))
        }
    }
}