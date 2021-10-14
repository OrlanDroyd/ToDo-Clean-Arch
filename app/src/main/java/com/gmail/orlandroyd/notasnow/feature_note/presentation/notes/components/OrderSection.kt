package com.gmail.orlandroyd.notasnow.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.gmail.orlandroyd.notasnow.feature_note.domain.util.NoteOrder
import com.gmail.orlandroyd.notasnow.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DefaultRadioButton(
                text = "Título",
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Fecha",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DefaultRadioButton(
                text = "Ascendente",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                },
                textStyle = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic,
                image = Icons.Default.ArrowUpward,
                imageDescription = "Asc"
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descendente",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                },
                textStyle = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic,
                image = Icons.Default.ArrowDownward,
                imageDescription = "Des"
            )
        }
    }
}