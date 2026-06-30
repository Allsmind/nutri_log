package com.projeto.nutrilog.presentation.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
fun CreateRecipeScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: CreateRecipeViewModel = koinViewModel()
    val recipeName by viewModel.recipeName.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    var showSearchDialog by remember { mutableStateOf(false) }
    var selectedFoodForWeight by remember { mutableStateOf<FoodEntity?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.saveSuccess.collect {
            onNavigateBack()
        }
    }

    // Live calculation totals
    val totalWeight = ingredients.sumOf { it.weightGrams }
    val totalCalories = ingredients.sumOf { (it.food.caloriesPer100g.toDouble() * it.weightGrams) / 100.0 }.toInt()
    val totalProtein = ingredients.sumOf { (it.food.proteinPer100g * it.weightGrams) / 100.0 }
    val totalCarbs = ingredients.sumOf { (it.food.carbPer100g * it.weightGrams) / 100.0 }
    val totalFat = ingredients.sumOf { (it.food.fatPer100g * it.weightGrams) / 100.0 }

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
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with Back Button
            TopAppBar(
                title = {
                    Text(
                        text = "Nova Receita",
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Recipe Name
                item {
                    OutlinedTextField(
                        value = recipeName,
                        onValueChange = { viewModel.setRecipeName(it) },
                        label = { Text("Nome da Receita") },
                        placeholder = { Text("Ex: Shake Proteico, Salada de Frutas...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }

                // Dynamic Totals Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Totais da Receita (${totalWeight}g)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "$totalCalories kcal", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Text(text = "Calorias", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "${totalProtein.toInt()}g", fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                                    Text(text = "Prot", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "${totalCarbs.toInt()}g", fontWeight = FontWeight.Bold, color = Color(0xFFF1C40F))
                                    Text(text = "Carb", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "${totalFat.toInt()}g", fontWeight = FontWeight.Bold, color = Color(0xFF3498DB))
                                    Text(text = "Gord", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                                }
                            }
                        }
                    }
                }

                // Add Ingredient Button
                item {
                    Button(
                        onClick = {
                            viewModel.searchIngredients("")
                            showSearchDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(text = "Adicionar Ingrediente", fontWeight = FontWeight.Bold)
                    }
                }

                // Ingredients Header
                item {
                    Text(
                        text = "Ingredientes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Ingredients List
                if (ingredients.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum ingrediente adicionado.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                } else {
                    items(ingredients, key = { it.food.id }) { item ->
                        IngredientListItem(
                            ingredient = item,
                            onDelete = { viewModel.removeIngredient(item.food.id) }
                        )
                    }
                }

                // Save Button
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.saveRecipe() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = recipeName.isNotBlank() && ingredients.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "Salvar Receita", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Ingredient Search Dialog
        if (showSearchDialog) {
            AlertDialog(
                onDismissRequest = { showSearchDialog = false },
                title = { Text("Buscar Ingrediente", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.searchIngredients(it) },
                            label = { Text("Buscar Alimento") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(searchResults, key = { it.id }) { food ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedFoodForWeight = food },
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(text = food.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(text = "${food.caloriesPer100g} kcal / 100g", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showSearchDialog = false }) {
                        Text("Fechar")
                    }
                }
            )
        }

        // Weight Dialog
        selectedFoodForWeight?.let { food ->
            var weightText by remember { mutableStateOf("100") }

            AlertDialog(
                onDismissRequest = { selectedFoodForWeight = null },
                title = { Text("Definir Peso", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = "Ingrediente: ${food.name}")
                        OutlinedTextField(
                            value = weightText,
                            onValueChange = { weightText = it },
                            label = { Text("Peso (gramas)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val weight = weightText.toIntOrNull() ?: 100
                            viewModel.addIngredient(food, weight)
                            selectedFoodForWeight = null
                            showSearchDialog = false
                        },
                        enabled = weightText.isNotBlank()
                    ) {
                        Text("Adicionar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedFoodForWeight = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun IngredientListItem(
    ingredient: RecipeIngredient,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = ingredient.food.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    text = "${ingredient.weightGrams}g - ${((ingredient.food.caloriesPer100g.toDouble() * ingredient.weightGrams) / 100.0).toInt()} kcal",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
