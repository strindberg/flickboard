package se.nullable.flickboard.model.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.nullable.flickboard.model.Action
import se.nullable.flickboard.model.Direction
import se.nullable.flickboard.model.KeyM
import se.nullable.flickboard.model.Layer
import se.nullable.flickboard.model.Layout
import se.nullable.flickboard.ui.Keyboard

val SPACE = KeyM(
    actions = mapOf(
        Direction.CENTER to Action.Text(" "),
        Direction.LEFT to Action.Jump(amount = -1, "<-"),
        Direction.RIGHT to Action.Jump(amount = 1, "->"),
    ),
    colspan = 3
)

val COMMON_MESSAGEASE_LAYER =
    Layer(
        keyRows = listOf(
            // pointer
            listOf(KeyM(actions = mapOf())),
            // clipboard
            listOf(KeyM(actions = mapOf())),
            // backspace
            listOf(
                KeyM(
                    actions = mapOf(
                        Direction.CENTER to Action.Delete(),
                        Direction.RIGHT to Action.Delete(direction = Action.Delete.Direction.Forwards)
                    )
                )
            ),
            // enter
            listOf(KeyM(actions = mapOf(Direction.CENTER to Action.Enter))),
        )
    )

@Composable
@Preview
fun CommonKeyboardPreview() {
    Box(Modifier.width(100.dp)) {
        Keyboard(layout = Layout(COMMON_MESSAGEASE_LAYER), onAction = {})
    }
}