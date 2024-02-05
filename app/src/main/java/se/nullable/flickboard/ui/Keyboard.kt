package se.nullable.flickboard.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import se.nullable.flickboard.model.Action
import se.nullable.flickboard.model.Layout
import se.nullable.flickboard.model.layouts.SV_MESSAGEASE

@Composable
fun Keyboard(
    layout: Layout,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier,
    enterKeyLabel: String? = null,
) {
    val shiftLayer = layout.shiftLayer.mergeFallback(layout.numericLayer)
    val mainLayer = layout.mainLayer.mergeFallback(layout.numericLayer).mergeShift(shiftLayer)
    var layer = layout.numericLayer ?: mainLayer
    layout.controlLayer?.let { layer = layer.chain(it.mergeShift(it.autoShift())) }
    if (layout.numericLayer != null) {
        layer = layer.chain(mainLayer.mergeFallback(layout.numericLayer))
    }
    val columns = layer.keyRows.maxOf { row -> row.sumOf { it.colspan } }
    BoxWithConstraints(modifier) {
        val columnWidth = this.maxWidth / columns
        Column {
            layer.keyRows.forEach { row ->
                Row {
                    row.forEach { key ->
                        Key(
                            key,
                            onAction = onAction,
                            modifier = Modifier.width(columnWidth * key.colspan),
                            enterKeyLabel = enterKeyLabel
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun KeyboardPreview() {
    var lastAction by remember { mutableStateOf<Action?>(null) }
    Column {
        Row {
            Text(text = "Tapped: $lastAction")
        }
        Keyboard(layout = SV_MESSAGEASE, onAction = { lastAction = it })
    }
}