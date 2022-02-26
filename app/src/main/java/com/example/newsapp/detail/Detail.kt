package com.example.newsapp.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.example.data.repository.model.GetItemResponse
import org.jsoup.Jsoup
import org.jsoup.select.Elements

@Composable
fun Detail(
    navController: NavController,
    itemId: String,
    viewModel: DetailViewModel,
    configuration: Configuration
) {
    viewModel.setItemId(itemId.replace("*>", "/"))

    val response by viewModel.getItem.collectAsState(initial = GetItemResponse("initial", null))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        response.item?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it.fields.thumbnail?.let { it1 ->
                    Image(
                        painter = rememberImagePainter(data = it1,
                            builder = {
                                size(OriginalSize)
                            }),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)
                            .padding(bottom = 10.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                }
                Text(
                    text = it.fields.headline,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)

                )
                Text(
                    text = "Published in ${it.webPublicationDate.substringBefore("T")}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)

                )
                it.fields.body?.let { it2 ->
                    var doc = Jsoup.parse(it2)
                    var divs = doc.select("div")
                    var elements: Elements
                    if (divs.size != 0) {
                        elements = divs.elementAt(0).allElements
                    } else {
                        elements = doc.allElements
                    }
                    for (e in elements) {
                        when (e.tagName()) {
                            "p" -> {
                                Text(
                                    text = buildAnnotatedString {
                                        var allText = e.text()
                                        var children = e.children()
                                        if (children.size != 0) {
                                            children.forEachIndexed { index, child ->

                                                append(
                                                    if (index == 0) {
                                                        if (child.text() != "") allText.substringBefore(
                                                            child.text()
                                                        ) else ""
                                                    } else {
                                                        if (child.text() != "") allText.substringAfter(
                                                            children.elementAt(index - 1).text()
                                                        )
                                                            .substringBefore(child.text()) else allText.substringAfter(
                                                            children.elementAt(index - 1).text()
                                                        )
                                                    }
                                                )
                                                if (child.tagName() == "strong") {

                                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append(child.text())
                                                    }
                                                } else {
                                                    append(child.text())
                                                }
                                                if (index == children.size - 1) append(
                                                    allText.substringAfter(
                                                        child.text()
                                                    )
                                                )
                                            }
                                        } else {
                                            append(allText)
                                        }

                                    },
                                    style = MaterialTheme.typography.body1.plus(TextStyle(lineHeight = 20.sp)),
                                    modifier = Modifier
                                        .padding(bottom = 5.dp)
                                        .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)

                                )
                            }
                            "img" -> Image(
                                painter = rememberImagePainter(data = e.attr("src"),
                                    builder = {
                                        size(OriginalSize)
                                    }),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)
                                    .padding(top = 5.dp, bottom = 10.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.FillWidth
                            )
                            "h2" -> Text(
                                text = e.text(),
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                                    .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)

                            )
                            "h3" -> Text(
                                text = e.text(),
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                                    .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.5f else 1f)
                            )
                        }
                    }
                }
            }
        }
    }
}