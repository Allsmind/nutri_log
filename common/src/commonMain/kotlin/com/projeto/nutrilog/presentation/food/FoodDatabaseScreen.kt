package com.projeto.nutrilog.presentation.food

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projeto.nutrilog.database.FoodEntity
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDatabaseScreen(
    date: String,
    onNavigateBack: () -> Unit,
    onNavigateToCreateRecipe: () -> Unit
) {
    val viewModel: FoodViewModel = koinViewModel()
    val foods by viewModel.foods.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var showConsumeDialogForFood by remember { mutableStateOf<FoodEntity?>(null) }
    var showCreateFoodDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.consumptionSuccess.collect {
            onNavigateBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        Color(0xFF0F172A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Back Button
            TopAppBar(
                title = {
                    Text(
                        text = "Alimentos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.search(it) },
                label = { Text("Buscar Alimentos") },
                placeholder = { Text("Ex: Arroz, Frango...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Foods List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(foods, key = { it.id }) { food ->
                    FoodListItem(
                        food = food,
                        onClick = { showConsumeDialogForFood = food }
                    )
                }
            }
        }

        // Bottom right buttons: Nova Receita and Novo Alimento
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExtendedFloatingActionButton(
                onClick = onNavigateToCreateRecipe,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                text = { Text("Nova Receita") }
            )

            FloatingActionButton(
                onClick = { showCreateFoodDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Novo Alimento")
            }
        }

        // Consume Dialog
        showConsumeDialogForFood?.let { food ->
            ConsumeFoodDialog(
                food = food,
                onDismiss = { showConsumeDialogForFood = null },
                onConfirm = { weight, mealName ->
                    viewModel.consumeFood(food, weight, mealName, date)
                    showConsumeDialogForFood = null
                }
            )
        }

        // Create Custom Food Dialog
        if (showCreateFoodDialog) {
            CreateFoodDialog(
                onDismiss = { showCreateFoodDialog = false },
                onConfirm = { name, calories, protein, carbs, fat ->
                    viewModel.saveCustomFood(name, calories, protein, carbs, fat)
                    showCreateFoodDialog = false
                }
            )
        }
    }
}

@Composable
fun FoodListItem(
    food: FoodEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = food.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${food.caloriesPer100g} kcal / 100g",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "P: ${food.proteinPer100g.toInt()}g  C: ${food.carbPer100g.toInt()}g  G: ${food.fatPer100g.toInt()}g",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun ConsumeFoodDialog(
    food: FoodEntity,
    onDismiss: () -> Unit,
    onConfirm: (weightGrams: Int, mealName: String) -> Unit
) {
    // ponytail: Appending the total recipe weight to the name string allows skipping the weight input dialog and logging the exact original weight without database schema changes.
    val parsedWeight = remember(food.name) { parseRecipeWeight(food.name) }
    var weightText by remember { mutableStateOf(parsedWeight?.toString() ?: "100") }
    val meals = listOf("Café da Manhã", "Almoço", "Café da Tarde", "Jantar")
    var selectedMeal by remember { mutableStateOf(meals.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Registrar Consumo",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Alimento: ${food.name}",
                    fontWeight = FontWeight.SemiBold
                )

                if (parsedWeight == null) {
                    OutlinedTextField(
                        value = weightText,
                        onValueChange = { weightText = it },
                        label = { Text("Quantidade (gramas)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = "Quantidade da receita: ${parsedWeight}g",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Text(
                    text = "Refeição",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    meals.forEach { meal ->
                        val isSelected = selectedMeal == meal
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedMeal = meal },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = meal.replace("Café da ", ""),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val weight = weightText.toIntOrNull() ?: 100
                    onConfirm(weight, selectedMeal)
                },
                enabled = weightText.isNotBlank()
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun CreateFoodDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, calories: Int, protein: Double, carbs: Double, fat: Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Novo Alimento (por 100g)",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Alimento") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text("Calorias (kcal)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = protein,
                    onValueChange = { protein = it },
                    label = { Text("Proteínas (g)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = carbs,
                    onValueChange = { carbs = it },
                    label = { Text("Carboidratos (g)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = fat,
                    onValueChange = { fat = it },
                    label = { Text("Gorduras (g)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val cal = calories.toIntOrNull() ?: 0
                    val prot = protein.toDoubleOrNull() ?: 0.0
                    val carb = carbs.toDoubleOrNull() ?: 0.0
                    val f = fat.toDoubleOrNull() ?: 0.0
                    onConfirm(name, cal, prot, carb, f)
                },
                enabled = name.isNotBlank() && calories.isNotBlank() && protein.isNotBlank() && carbs.isNotBlank() && fat.isNotBlank()
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

private fun parseRecipeWeight(name: String): Int? {
    if (!name.contains(" (Receita - ") || !name.endsWith("g)")) return null
    val startIndex = name.lastIndexOf(" (Receita - ") + " (Receita - ".length
    val endIndex = name.length - 2
    if (startIndex >= endIndex) return null
    return name.substring(startIndex, endIndex).toIntOrNull()
}
