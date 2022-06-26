package com.seiko.imageloader.component.fetcher

import androidx.compose.ui.graphics.painter.Painter
import okio.BufferedSource

sealed interface FetchResult

class FetchSourceResult(
    val source: BufferedSource,
    val mimeType: String?,
    val metadata: Any? = null,
) : FetchResult

class FetchPainterResult(
    val painter: Painter
) : FetchResult
