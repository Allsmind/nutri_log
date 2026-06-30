# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added
- Initial project structure for Kotlin Multiplatform (KMP) targeting Android and iOS.
- Compose Multiplatform support with dependencies configured in root and module Gradle files.
- Version catalog `gradle/libs.versions.toml` with latest dependencies.
- SQLDelight integration with a user entity table database schema.
- Koin dependency injection setup with expect/actual platform declarations for Android and iOS.
- Basic Material 3 Compose UI Welcome Screen.
- Android application entry points (`MainActivity` and `NutriLogApplication`) and Android manifest configuration.
- Gradle wrapper files for standard builds.
- Custom Material 3 light/dark mode color schemes in `Color.kt` and application typography in `Type.kt`.
- Custom `NutriLogTheme` composition provider.
- Navigation Compose setup with `NavGraph` and routes.
- Fully styled `WelcomeScreen` and profile form `RegisterScreen`.
- User profile data persistence using SQLDelight database and expected/actual platform SQLite drivers.
- Clean Architecture data layer setup (UserRepository, UserRepositoryImpl, GetUserUseCase, SaveUserUseCase, ClearUserUseCase).
- Koin DI dependency configurations for repositories, UseCases, RegisterViewModel and MainViewModel.
- Reactive app routing flow: automatically boot/route to onboarding or dashboard depending on database status.
- Premium ProfileDashboardPlaceholderScreen UI for registered users showing macro and calorie targets with a reset profile feature.
- Daily progress database schema (`DailyProgressEntity`) in SQLDelight and queries (getProgressForDate, insertProgress, clearProgress).
- Clean Architecture progress tracking layer (`ProgressRepository`, `ProgressRepositoryImpl`, `GetProgressUseCase`, `AddConsumptionUseCase`).
- Platform-native date helper (`DateUtils` expect/actual) to obtain today's date string.
- Dashboard UI (`DashboardScreen`) displaying calories budget, consumed and remaining amounts, progress bars for protein/carbs/fat, and reset profiles.
- Inline Quick Add form on the dashboard to dynamically add calories, protein, carbs, and fat consumption to today's entry.
- Full Koin dependency wiring for the progress repository, use cases and DashboardViewModel.
- Food database schema (`FoodEntity`) in SQLDelight pre-seeded with common default foods (rice, chicken, egg, oats, olive oil, etc.).
- Clean Architecture food database layer (`FoodRepository`, `FoodRepositoryImpl`, `SearchFoodsUseCase`, `SaveCustomFoodUseCase`).
- Food search and listing UI (`FoodDatabaseScreen`) with popups to log consumption of a selected item by custom weight and save custom foods.
- Updated Dashboard UI with a direct link button to browse the Food Database.
- Full Koin dependency wiring for the Food Database components.
- Cleanup: Deleted obsolete profile placeholder file `ProfileDashboardPlaceholderScreen.kt`.
- Meal logs database schema (`MealLogEntity`) in SQLDelight and removal of the redundant `DailyProgressEntity` table to implement dynamic calculations.
- Clean Architecture meal logging layer (`MealLogRepository`, `MealLogRepositoryImpl`, `GetMealLogsUseCase`, `AddMealLogUseCase`, `DeleteMealLogUseCase`).
- Grouped food consumption display by meal types (Café da Manhã, Almoço, Café da Tarde, Jantar, Consumo Rápido) on the Dashboard, with dynamic total sums.
- Visual delete options to remove individual food log items directly from the Dashboard.
- Meal selection selector chip inside the consumption dialog on the Food Database screen.
- Cleanup: Removed obsolete files from the previous progress tracking implementation.
- Daily summaries aggregation query (`getDailySummaries`) in SQLDelight to group calories/macros by date.
- Clean Architecture consolidation layer (`getDailySummaries` in `MealLogRepository` / `MealLogRepositoryImpl` and `GetDailySummariesUseCase`).
- Statistics UI (`ScreenStatistics`) with historical summaries of the last 7 days.
- Custom bar chart built using responsive Compose Layouts showing calorie limits and highlight coloring for exceeded targets.
- Average daily macronutrients progress rows (Protein, Carbs, Fat) compared to user goals.
- Action Buttons Row on the Dashboard containing search foods and ver estatísticas direct entries.
- Full Koin dependency wiring for the Statistics components.
- Recipe scaling logic to consolidate ingredients and automatically calculate macro/calorie values per 100g.
- Clean Architecture recipe layer (CreateRecipeViewModel reusing existing SaveCustomFoodUseCase).
- Recipe creation UI (CreateRecipeScreen) displaying dynamic preview of consolidated totals, ingredient list, delete controls, and dialog selectors.
- Extended Floating Action Button (FAB) on FoodDatabaseScreen for recipe creation.
- Full Koin dependency wiring for the Recipe creation components.
- Expect/actual date offset utility (`getOffsetDateString`) in DateUtils for KMP date calculations.
- Date navigation state inside `DashboardViewModel` allowing day-by-day increments and decrements.
- Day selector navigation header (IconButton arrows and labels: "Hoje", "Ontem", or "DD/MM/YYYY") in `DashboardScreen`.
- Date parameter forwarding in navigation to the Food Database (`food_database/{date}` route) to log entries under past historical dates.
- Reused SaveUserUseCase in Clean Architecture settings layer (SettingsViewModel).
- Settings UI (SettingsScreen) with pre-filled inputs for user name and targets (calories, protein, carbs, fat).
- Symmetrical controls row in DashboardScreen with direct navigation to "Ajustes" (Settings) and "Reset Perfil".
- Full Koin dependency wiring for the Settings components.
- Code Cleanup: Fixed all compiler deprecation warnings across screens (e.g. ArrowBack).
- Verified complete Gradle compilation and packaging for Android (assembleDebug task success).
- Bugfix: Added missing FoodRepository, SearchFoodsUseCase, SaveCustomFoodUseCase, and FoodViewModel registrations in Koin.kt to resolve runtime navigate-to-food-database crashes.
- Recipe UI update: Appended total recipe weight to the name and disabled manual weight input in ConsumeFoodDialog to log recipes directly with their original fixed weight.
