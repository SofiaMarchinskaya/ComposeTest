package com.awesomedev.composetest.ui.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Constraints

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyTable(
    rowCount: Int,
    columnCount: Int,
    cellWidth: Int,
    cellHeight: Int,
    buffer: Int,
    modifier: Modifier = Modifier,
    content: @Composable (row: Int, column: Int, scrollX: Float, scrollY: Float) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val viewportWidth = constraints.maxWidth
        val viewportHeight = constraints.maxHeight

        val scrollX = remember { mutableFloatStateOf(0f) }
        val scrollY = remember { mutableFloatStateOf(0f) }

        val maxScrollX = (columnCount * cellWidth - viewportWidth).coerceAtLeast(0)
        val maxScrollY = (rowCount * cellHeight - viewportHeight).coerceAtLeast(0)

        val horizontalScrollState = rememberScrollableState { delta ->
            val newValue = (scrollX.floatValue - delta)
                .coerceIn(0f, maxScrollX.toFloat())
            val consumed = scrollX.floatValue - newValue
            scrollX.floatValue = newValue
            consumed
        }

        val verticalScrollState = rememberScrollableState { delta ->
            val newValue = (scrollY.floatValue - delta)
                .coerceIn(0f, maxScrollY.toFloat())
            val consumed = scrollY.floatValue - newValue
            scrollY.floatValue = newValue
            consumed
        }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {}
        }

        LazyLayout(
            modifier = Modifier
                .clipToBounds()
                .nestedScroll(nestedScrollConnection)
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = horizontalScrollState,
                    flingBehavior = ScrollableDefaults.flingBehavior(),
                    reverseDirection = false,
                    enabled = true
                )
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = verticalScrollState,
                    flingBehavior = ScrollableDefaults.flingBehavior(),
                    reverseDirection = false,
                    enabled = true
                ),
            itemProvider = {
                object : LazyLayoutItemProvider {
                    override val itemCount = rowCount * columnCount
                    override fun getKey(index: Int): Any = index

                    @Composable
                    override fun Item(index: Int, key: Any) {
                        key(key) {
                            val row = index / columnCount
                            val col = index % columnCount
                            content(row, col, scrollX.floatValue, scrollY.floatValue)
                        }
                    }
                }
            },
            measurePolicy = { constraints ->
                val visibleCols = constraints.maxWidth / cellWidth + buffer
                val visibleRows = constraints.maxHeight / cellHeight + buffer

                val startCol = (scrollX.floatValue.toInt() / cellWidth).coerceIn(0, columnCount)
                val startRow = (scrollY.floatValue.toInt() / cellHeight).coerceIn(0, rowCount)

                val indexesToCompose = buildList {
                    val maxRow = (startRow + visibleRows).coerceAtMost(rowCount)
                    val maxCol = (startCol + visibleCols).coerceAtMost(columnCount)

                    for (row in startRow until maxRow) {
                        if (row != 0) {
                            add(row * columnCount)
                        }
                    }

                    for (col in startCol until maxCol) {
                        if (col != 0) {
                            add(col)
                        }
                    }

                    add(0)

                    for (row in startRow until maxRow) {
                        for (col in startCol until maxCol) {
                            if (col != 0 && row != 0) {
                                val index = row * columnCount + col
                                add(index)
                            }
                        }
                    }
                }

                val placeables = indexesToCompose.flatMap { index ->
                    val row = index / columnCount
                    val col = index % columnCount
                    measure(index, Constraints.fixed(cellWidth, cellHeight)).map {
                        Triple(row, col, it)
                    }
                }

                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeables.forEach { (row, col, placeable) ->
                        val isVerticalHeader = col == 0
                        val isHorizontalHeader = row == 0

                        val x =
                            if (isVerticalHeader) 0 else col * cellWidth - scrollX.floatValue.toInt()
                        val y =
                            if (isHorizontalHeader) 0 else row * cellHeight - scrollY.floatValue.toInt()

                        val z = when {
                            isVerticalHeader && isHorizontalHeader -> 2f
                            isVerticalHeader || isHorizontalHeader -> 1f
                            else -> 0f
                        }

                        val isVisible =
                            (isVerticalHeader || (x + cellWidth >= 0 && x < constraints.maxWidth)) &&
                                    (isHorizontalHeader || (y + cellHeight >= 0 && y < constraints.maxHeight))

                        if (isVisible) {
                            placeable.placeRelative(x, y, z)
                        }
                    }
                }
            }
        )
    }
}

