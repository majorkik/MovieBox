package com.majorik.library.base.common.lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyGridFor(
    items: List<T> = listOf(),
    rows: Int = 3,
    hPadding: Int = 8,
    itemContent: @Composable LazyItemScope.(T, Int) -> Unit
) {
    val chunkedList = items.chunked(rows)
    LazyColumn(modifier = Modifier.padding(horizontal = hPadding.dp)) {
        itemsIndexed(items = chunkedList, itemContent = { index, it ->
            if (index == 0) {
                Spacer(modifier = Modifier.size(8.dp))
            }

            Row {
                it.forEachIndexed { rowIndex, item ->
                    Box(
                        modifier = Modifier.weight(1F).align(Alignment.Top).padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        itemContent(item, index * rows + rowIndex)
                    }
                }

                // Добавляем пустые блоки, если количество элементов в строке меньше 3
                repeat(rows - it.size) {
                    Box(modifier = Modifier.weight(1F).padding(8.dp)) {}
                }
            }
        })
    }
}
