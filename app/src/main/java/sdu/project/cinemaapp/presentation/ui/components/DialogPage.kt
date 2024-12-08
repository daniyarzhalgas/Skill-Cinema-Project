package sdu.project.cinemaapp.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogPage(
    text: String,
    onTextChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onCreateRequest: ()-> Unit
){
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Новая коллекция",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 10.dp, start = 15.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )


            TextField(
                value = text,
                onValueChange = { newText ->
                    onTextChange(newText) },
                placeholder = {Text("название коллекций")},
                maxLines = 1,
                modifier = Modifier
                    .padding(10.dp)
            )

            TextButton(
                onCreateRequest,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
                    .align(Alignment.End),

            ) {
                Text("Создать")
            }
        }
    }
}