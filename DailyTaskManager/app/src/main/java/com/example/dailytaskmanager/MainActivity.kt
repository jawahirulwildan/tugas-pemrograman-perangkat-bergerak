package com.example.dailytaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import com.example.dailytaskmanager.ui.theme.DailyTaskManagerTheme

data class Task(
    val id: Int,
    val title: String,
    val deadline: String,
    val category: String,
    var isDone: MutableState<Boolean> = mutableStateOf(false)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyTaskManagerTheme {
                TaskScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("Kuliah", "Tugas", "Olahraga", "Hiburan", "Kegiatan kampus", "Lainnya")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,
            label = { Text("Kategori") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onCategorySelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen() {
    val originalTasks = remember { mutableStateListOf<Task>() }
    var sortOption by remember { mutableStateOf("Default") }
    var showDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) } // Add this state for deletion

    val toDoTasks = originalTasks.filter { !it.isDone.value }
    val doneTasks = originalTasks.filter { it.isDone.value }

    val sortedTasks = when (sortOption) {
        "Deadline" -> originalTasks.sortedBy { it.deadline }
        "Selesai" -> originalTasks.sortedByDescending { it.isDone.value }
        else -> originalTasks
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GARAPAN", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Sortir")
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Default") },
                                onClick = {
                                    sortOption = "Default"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Deadline") },
                                onClick = {
                                    sortOption = "Deadline"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Selesai") },
                                onClick = {
                                    sortOption = "Selesai"
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        TaskSection(
                            title = "To Do",
                            tasks = toDoTasks,
                            emptyMessage = "Kamu hebat! Tidak ada tugas tersisa 🎉",
                            showImage = true,
                            onCheckTask = { task, isChecked -> task.isDone.value = isChecked },
                            onDeleteTask = { task -> taskToDelete = task }
                        )

                        TaskSection(
                            title = "Done",
                            tasks = doneTasks,
                            emptyMessage = "Ayo Selesaikan Tugasmu Sekarang 🔥",
                            showImage = false,
                            onCheckTask = { task, isChecked -> task.isDone.value = isChecked },
                            onDeleteTask = { task -> taskToDelete = task }
                        )
                }

                if (showDialog) {
                    AddTaskDialog(
                        onAddTask = { title, deadline, category ->
                            originalTasks.add(
                                Task(
                                    id = originalTasks.size + 1,
                                    title = title,
                                    deadline = deadline,
                                    category = category
                                )
                            )
                            showDialog = false
                        },
                        onDismiss = { showDialog = false }
                    )
                }
                if (taskToDelete != null) {
                    AlertDialog(
                        onDismissRequest = { taskToDelete = null },
                        title = { Text("Konfirmasi Hapus") },
                        text = { Text("Apakah kamu yakin ingin menghapus tugas ini?") },
                        confirmButton = {
                            TextButton(onClick = {
                                originalTasks.remove(taskToDelete)
                                taskToDelete = null
                            }) {
                                Text("Hapus")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { taskToDelete = null }) {
                                Text("Batal")
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun TaskSection(
    title: String,
    tasks: List<Task>,
    emptyMessage: String,
    showImage: Boolean = false,
    onCheckTask: (Task, Boolean) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)

        if (tasks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showImage) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_state_image),
                        contentDescription = null,
                        modifier = Modifier.size(250.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(emptyMessage, color = Color.Gray)
            }
        } else {
            LazyColumn {
                items(tasks) { task ->
                    TaskCard(
                        task = task,
                        onCheckTask = onCheckTask,
                        onDeleteTask = onDeleteTask
                    )
                }
            }
        }
    }
}

@Composable
fun DateTimePicker(onDateTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    var calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val timePickerDialog = TimePickerDialog(
                context, { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedDate = "${dayOfMonth}/${month + 1}/$year $hourOfDay:$minute"
                    onDateTimeSelected(selectedDate)
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
            )
            timePickerDialog.show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Button(onClick = { datePickerDialog.show() }) {
        Text(text = if (selectedDate.isEmpty()) "Pilh Tanggal" else selectedDate)
    }
}


@Composable
fun TaskCard(task: Task, onCheckTask: (Task, Boolean) -> Unit, onDeleteTask: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = task.title, fontWeight = FontWeight.Bold)
                Text(text = "Deadline: ${task.deadline}", color = Color.Gray)
                Text(text = "Kategori: ${task.category}", color = Color.Gray, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = task.isDone.value,
                    onCheckedChange = { onCheckTask(task, it) }
                )
                IconButton(onClick = { onDeleteTask(task) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
        }
    }
}

@Composable
fun AddTaskDialog(onAddTask: (String, String, String) -> Unit, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotEmpty() && deadline.isNotEmpty() && category.isNotEmpty()) {
                        onAddTask(title, deadline, category)
                        onDismiss()
                    }
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal")
            }
        },
        title = { Text("Tambah Garapan") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Garapan") },
                    modifier = Modifier.fillMaxWidth()
                )

                CategoryDropdown(
                    selectedCategory = category,
                    onCategorySelected = { category = it }
                )

                DateTimePicker(onDateTimeSelected = { selectedDate -> deadline = selectedDate })
            }
        }
    )
}