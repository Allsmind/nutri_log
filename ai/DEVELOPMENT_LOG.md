# Diário de Desenvolvimento

## Sessão 001

Data

2026-06-29

Feature

Estrutura inicial.

Implementado

- projeto criado
- módulos
- Gradle
- Koin
- SQLDelight

Arquivos alterados

- build.gradle.kts (raiz)
- settings.gradle.kts
- gradle.properties
- gradle/libs.versions.toml
- common/build.gradle.kts
- common/src/commonMain/kotlin/com/projeto/nutrilog/App.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt
- common/src/androidMain/kotlin/com/projeto/nutrilog/di/Koin.android.kt
- common/src/iosMain/kotlin/com/projeto/nutrilog/di/Koin.ios.kt
- common/src/iosMain/kotlin/com/projeto/nutrilog/MainViewController.kt
- common/src/commonMain/sqldelight/com/projeto/nutrilog/database/NutriLogDatabase.sq
- androidApp/build.gradle.kts
- androidApp/src/main/AndroidManifest.xml
- androidApp/src/main/kotlin/com/projeto/nutrilog/android/NutriLogApplication.kt
- androidApp/src/main/kotlin/com/projeto/nutrilog/android/MainActivity.kt

Problemas

Nenhum.

Próximo passo

Criar tema e estruturar navegação.

## Sessão 002

Data

2026-06-29

Feature

Tema e navegação.

Implementado

- Base de cores (Dark/Light) em Color.kt
- Tipografia em Type.kt
- Customização do MaterialTheme em Theme.kt
- WelcomeScreen com design premium
- RegisterScreen com formulário para perfil
- Grafo de navegação em NavGraph.kt
- Integração da navegação em App.kt

Arquivos alterados

- common/src/commonMain/kotlin/com/projeto/nutrilog/theme/Color.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/theme/Type.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/theme/Theme.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/welcome/WelcomeScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/register/RegisterScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/App.kt

Problemas

Nenhum.

Próximo passo

Implementar lógica de salvar usuário (Fase 2 - Cadastro do usuário).

## Sessão 003

Data

2026-06-29

Feature

Cadastro do usuário (Fase 2).

Implementado

- Criação da query clearUser em NutriLogDatabase.sq
- UserRepository e UserRepositoryImpl atualizados com clearUser e saveUser no Dispatchers.IO
- Criados GetUserUseCase, SaveUserUseCase e ClearUserUseCase
- Criado RegisterViewModel e MainViewModel para controle de estado do cadastro
- Criado ProfileDashboardPlaceholderScreen como tela inicial após cadastro concluído
- Configurada injeção de dependência dos novos componentes no Koin
- App.kt atualizado para ler o estado do usuário e decidir a navegação/fluxo inicial

Arquivos alterados

- common/src/commonMain/sqldelight/com/projeto/nutrilog/database/NutriLogDatabase.sq
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/repository/UserRepository.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/data/repository/UserRepositoryImpl.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/GetUserUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/SaveUserUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/ClearUserUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/register/RegisterViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/register/RegisterScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/main/MainViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/main/ProfileDashboardPlaceholderScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/App.kt

Problemas

Nenhum.

Próximo passo

Implementar o Dashboard completo (Fase 3).

## Sessão 004

Data

2026-06-29

Feature

Dashboard (Fase 3).

Implementado

- Criação da tabela DailyProgressEntity e queries getProgressForDate, insertProgress, clearProgress em SQLDelight
- Criação das interfaces de repositório ProgressRepository e ProgressRepositoryImpl
- Criados GetProgressUseCase e AddConsumptionUseCase para o fluxo do Dashboard
- Atualizado o ClearUserUseCase para resetar também o progresso do usuário
- Criado o DateUtils (expect/actual) para obter a data atual de forma nativa e offline em KMP
- Criado o DashboardViewModel para gerenciar dados do dia e registrar consumo rápido (Quick Add)
- Criada a tela DashboardScreen com visualização de calorias consumidas/restantes e barras de progresso de macronutrientes
- Injetadas novas dependências no Koin
- Atualizado o App.kt para renderizar a tela de Dashboard quando o usuário estiver registrado

Arquivos alterados

- common/src/commonMain/sqldelight/com/projeto/nutrilog/database/NutriLogDatabase.sq
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/repository/ProgressRepository.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/data/repository/ProgressRepositoryImpl.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/GetProgressUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/AddConsumptionUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/ClearUserUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/utils/DateUtils.kt
- common/src/androidMain/kotlin/com/projeto/nutrilog/utils/DateUtils.android.kt
- common/src/iosMain/kotlin/com/projeto/nutrilog/utils/DateUtils.ios.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/App.kt

Problemas

Nenhum.

Próximo passo

Implementar o Banco de alimentos (Fase 4).

## Sessão 005

Data

2026-06-29

Feature

Banco de alimentos (Fase 4).

Implementado

- Criação da tabela FoodEntity no SQLDelight com dados padrão pré-cadastrados (Arroz, Frango, Ovo, Aveia, Azeite, etc.)
- Criadas as queries searchFoods e insertFood
- Criação das interfaces de repositório FoodRepository e FoodRepositoryImpl
- Criados os Casos de Uso SearchFoodsUseCase e SaveCustomFoodUseCase
- Atualizado o ClearUserUseCase para resetar as tabelas sem excluir os alimentos padrão globais
- Criado o FoodViewModel para buscar alimentos, salvar alimentos customizados e calcular consumo por gramas consumido
- Criada a tela FoodDatabaseScreen com listagem de alimentos, diálogo para definir peso consumido e formulário para novos alimentos
- Configurada injeção de dependência dos novos componentes no Koin
- Adicionado botão "Buscar no Banco de Alimentos" no DashboardScreen
- Configurada rota do Banco de Alimentos no NavGraph e App.kt
- Remoção do arquivo obsoleto ProfileDashboardPlaceholderScreen.kt

Arquivos alterados

- common/src/commonMain/sqldelight/com/projeto/nutrilog/database/NutriLogDatabase.sq
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/repository/FoodRepository.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/data/repository/FoodRepositoryImpl.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/SearchFoodsUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/SaveCustomFoodUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/ClearUserUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodDatabaseScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/App.kt
- Deletado: common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/main/ProfileDashboardPlaceholderScreen.kt

Problemas

Nenhum.

Próximo passo

Implementar o Registro de refeições (Fase 5).

## Sessão 006

Data

2026-06-29

Feature

Registro de refeições (Fase 5).

Implementado

- Remoção da tabela DailyProgressEntity e migração de toda a lógica para cálculo dinâmico a partir dos logs de refeição, evitando dados redundantes e inconsistentes (YAGNI/ponytail)
- Criação da tabela MealLogEntity no SQLDelight com queries insertMealLog, getMealLogsForDate, deleteMealLog, clearAllMealLogs
- Criação das interfaces de repositório MealLogRepository e MealLogRepositoryImpl
- Criados os Casos de Uso GetMealLogsUseCase, AddMealLogUseCase, DeleteMealLogUseCase
- Atualizado o ClearUserUseCase para limpar a tabela de refeições consumidas
- Atualizado o DashboardViewModel para computar os totais de calorias/macros consumidos dinamicamente e agrupar registros por refeição
- Atualizada a tela DashboardScreen para exibir a lista de alimentos consumidos no dia agrupados por tipo de refeição (Café da Manhã, Almoço, Café da Tarde, Jantar, Consumo Rápido), com a opção de excluir itens individualmente
- Atualizada a tela FoodDatabaseScreen para exibir um seletor visual em chips permitindo escolher a refeição ao consumir um alimento
- Removidos arquivos obsoletos de rastreamento de progresso anterior (ProgressRepository.kt, ProgressRepositoryImpl.kt, GetProgressUseCase.kt, AddConsumptionUseCase.kt)
- Ajustada a injeção do Koin e a fiação de rotas de navegação no NavGraph e App.kt

Arquivos alterados

- common/src/commonMain/sqldelight/com/projeto/nutrilog/database/NutriLogDatabase.sq
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/repository/MealLogRepository.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/data/repository/MealLogRepositoryImpl.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/GetMealLogsUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/AddMealLogUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/DeleteMealLogUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/ClearUserUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodDatabaseScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/App.kt
- Deletados: ProgressRepository.kt, ProgressRepositoryImpl.kt, GetProgressUseCase.kt, AddConsumptionUseCase.kt

Problemas

Nenhum.

Próximo passo

Implementar as Estatísticas (Fase 6).