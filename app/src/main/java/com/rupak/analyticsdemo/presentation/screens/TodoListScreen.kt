package com.rupak.analyticsdemo.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rupak.analyticsdemo.R
import com.rupak.analyticsdemo.model.local.Todo
import com.rupak.analyticsdemo.presentation.intents.TodoIntents
import com.rupak.analyticsdemo.presentation.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListScreen(
    todoViewModel: TodoViewModel
) {
    val todoState by todoViewModel.todoUiState.collectAsState()
    var inputText by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        )

        {
            OutlinedTextField(value = inputText, onValueChange = {
                inputText = it
            })
            Button(onClick = {
                todoViewModel.handleIntent(TodoIntents.OnAddClick(title = inputText))
                inputText = ""
            }) {
                Text(text = "Add")
            }
        }
        todoState.todoList?.let { list ->
            if (list.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f), content = {
                    itemsIndexed(list) { index: Int, item: Todo ->
                        TodoItem(item, onDelete = {
                            todoViewModel.handleIntent(TodoIntents.OnDeleteClick(item.id))
                        })
                    }
                })
            } else NoItemUI()
        } ?: NoItemUI()
        Button(modifier = Modifier.fillMaxWidth(),
            colors = if (!todoState.isAnalyticsEnabled) ButtonDefaults.buttonColors(containerColor = Color.Red) else ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            ),
            onClick = {
                todoViewModel.handleIntent(TodoIntents.enableAnalytics(!todoState.isAnalyticsEnabled))
            }) {
            Text(text = if (todoState.isAnalyticsEnabled) "Disable Analytics" else "Enable Analytics")
        }
    }
}

@Composable
fun NoItemUI() {
    Text(
        modifier = Modifier.fillMaxHeight(0.9f).fillMaxWidth().wrapContentHeight(),
        text = "No item added yet",
        textAlign = TextAlign.Center,


    )

}


@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                color = Color.LightGray,
                fontSize = 10.sp,
                text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH).format(item.createdAt)
            )
            Text(color = Color.Companion.White, fontSize = 20.sp, text = item.title)

        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}