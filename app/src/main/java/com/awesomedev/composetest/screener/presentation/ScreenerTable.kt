package com.awesomedev.composetest.screener.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.awesomedev.composetest.screener.domain.Instrument
import com.awesomedev.composetest.ui.table.LazyTable
import com.awesomedev.composetest.ui.theme.Typography

@Composable
fun ScreenerTable(
    instruments: LazyPagingItems<Instrument>,
) {
    LazyTable(
        rowCount = instruments.itemCount + 1,
        columnCount = 4,
        cellWidth = 250,
        cellHeight = 80,
        buffer = 2,
    ) { row, column, scrollX, scrollY ->

        when {
            row == 0 && column == 0 -> {
                Header(text = "Ticker")
            }

            row == 0 && column == 1 -> {
                Header(text = "Price")
            }

            row == 0 && column == 2 -> {
                Header(text = "Change")
            }

            row == 0 && column == 3 -> {
                Header(text = "Price(%)")
            }

            column == 0 -> {
                instruments[row - 1]?.let {
                    RegularCell(
                        text = it.symbol,
                        scrollX = scrollX,
                        column = column
                    )
                }
            }

            column == 1 -> {
                instruments[row - 1]?.let {
                    RegularCell(
                        text = String.format("%.2f$", it.price),
                        scrollX = scrollX,
                        column = column
                    )
                }
            }

            column == 2 -> {
                instruments[row - 1]?.let {
                    RegularCell(
                        text = String.format("%.2f$", it.change),
                        scrollX = scrollX,
                        column = column
                    )
                }
            }

            column == 3 -> {
                instruments[row - 1]?.let {
                    RegularCell(
                        text = String.format("%.2f", it.changePercent),
                        scrollX = scrollX,
                        column = column
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun RegularCell(column: Int, scrollX: Float, text: String) {
    Box(
        modifier = Modifier
            .shadow(if (column == 0 && scrollX > 0) 16.dp else 0.dp)
            .size(300.dp, 80.dp)
            .background(Color.White)
            .border(0.5.dp, Color.LightGray, shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
private fun Header(text: String) {
    Box(
        modifier = Modifier
            .size(250.dp, 80.dp)
            .background(Color.LightGray)
            .border(0.5.dp, Color.LightGray, shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun InstrumentHeader(text: String) {
    Box(
        modifier = Modifier
            .size(250.dp, 80.dp)
            .background(Color.LightGray)
            .border(1.dp, Color.Black, shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis
        )
    }
}