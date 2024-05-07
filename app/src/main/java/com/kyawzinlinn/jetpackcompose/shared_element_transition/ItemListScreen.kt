@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalSharedTransitionApi::class
)

package com.kyawzinlinn.jetpackcompose.shared_element_transition

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kyawzinlinn.jetpackcompose.R
import com.kyawzinlinn.jetpackcompose.navigation.NavigationRoute

val SharedElementItemListRoute: NavigationRoute = object : NavigationRoute {
    override val route: String = "shared_element_item_list"
}

val images = listOf(
    R.drawable.dog_1,
    R.drawable.dog_2,
    R.drawable.dog_3,
    R.drawable.dog_4,
    R.drawable.dog_5,
    R.drawable.dog_6
)

const val LOREM = "\n" +
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n" +
        "Translation:\n" +
        "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.\n" +
        "Additional Lorem Ipsum Text:\n" +
        "Paragraph 2:\n" +
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas mattis arcu ac neque tempor convallis. Cras justo odio, ultricies in malesuada vitae, suscipit a nunc. Donec elementum ligula et tortor tempor, eget aliquam nisl elementum. Mauris euismod lectus eget lacus ultricies, ac tincidunt nunc ultricies. Nulla facilisi. Cras euismod, massa vitae ultricies vehicula, nisi nisi tincidunt neque, vitae aliquam nunc odio eget massa."

fun NavGraphBuilder.sharedElementItemList(
    sharedTransitionScope: SharedTransitionScope,
    onItemClick: (Int, Int, String) -> Unit
) {
    composable(route = SharedElementItemListRoute.route) {
        val scope by remember{ derivedStateOf { sharedTransitionScope }}
        scope.ItemListScreen(
            onItemClick = onItemClick,
            animatedVisibilityScope = this
        )
    }
}

@Composable
private fun SharedTransitionScope.ItemListScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (Int, Int, String) -> Unit
) {
    val scope by remember{ derivedStateOf { animatedVisibilityScope }}

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(images) { index, image ->
            key (image) {
                ImageItem(
                    image = image,
                    index = index,
                    animatedVisibilityScope = scope,
                    onClick = { onItemClick(image, index, LOREM) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    modifier = Modifier.height(1.dp),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun SharedTransitionScope.ImageItem(
    image: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    index: Int,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .weight(1f)
                .sharedElement(
                    state = rememberSharedContentState(key = "image/$index"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 400, easing = FastOutSlowInEasing)
                    }
                ),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            maxLines = 4,
            text = LOREM,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .sharedElement(
                    state = rememberSharedContentState(key = "text/$index"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 400, easing = FastOutSlowInEasing)
                    }
                )
        )
    }
}
