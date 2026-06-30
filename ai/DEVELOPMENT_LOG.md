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

## Sessão 007

Data

2026-06-30

Feature

Estatísticas (Fase 8).

Implementado

- Criação da query getDailySummaries no SQLDelight para consolidar calorias e macros agrupados por data nos últimos dias
- Atualização do repositório MealLogRepository e MealLogRepositoryImpl para carregar a consolidação diária
- Criação do Caso de Uso GetDailySummariesUseCase
- Criado o StatisticsViewModel para ler os dados consolidados do histórico e metas do usuário
- Criada a tela ScreenStatistics com:
  - Card de médias diárias dos últimos 7 dias comparando com as metas.
  - Gráfico de barras interativo e responsivo em Compose Layouts (sem complexidade de Canvas pixel-math).
  - Alerta visual destacando dias em que a meta calórica foi excedida.
  - Distribuição média de macronutrientes com barras de progresso lineares.
- Adicionado botão "Ver Estatísticas" no DashboardScreen, criando um fluxo em Row com os botões de ação principal do app
- Adicionadas rotas de navegação no NavGraph.kt e injeções de dependência no Koin.kt

Arquivos alterados

- common/src/commonMain/sqldelight/com/projeto/nutrilog/database/NutriLogDatabase.sq
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/repository/MealLogRepository.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/data/repository/MealLogRepositoryImpl.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/domain/usecase/GetDailySummariesUseCase.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/statistics/StatisticsViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/statistics/StatisticsScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt

Problemas

Nenhum.

Próximo passo

Implementar as Receitas (Fase 6).

## Sessão 008

Data

2026-06-30

Feature

Receitas (Fase 6).

Implementado

- Otimização YAGNI (ponytail): Em vez de criar um conjunto complexo de tabelas adicionais e joins de banco para receitas/ingredientes, as receitas são calculadas e escaladas para 100g no momento de sua criação e salvas diretamente na tabela global de alimentos (FoodEntity). Isso possibilita que receitas sejam listadas, pesquisadas e consumidas da mesma maneira que qualquer alimento básico (reuso de toda a pipeline).
- Criado o CreateRecipeViewModel para gerenciar a adição/remoção temporária de ingredientes, busca rápida de alimentos e cálculo matemático consolidado dos macronutrientes da receita escalado para 100g.
- Criada a tela CreateRecipeScreen contendo:
  - Input para nome da receita.
  - Card dinâmico atualizado em tempo real mostrando os macros consolidados (calorias, proteínas, carboidratos e gorduras) conforme novos ingredientes são adicionados.
  - Listagem dos ingredientes adicionados exibindo pesos individuais e opção para exclusão.
  - Diálogos popups elegantes para busca de ingrediente e seleção de quantidade (gramas).
- Adicionado o botão "Nova Receita" usando um ExtendedFloatingActionButton no FoodDatabaseScreen.
- Ajustadas as rotas de navegação no NavGraph.kt e injeções de dependência no Koin.kt.

Arquivos alterados

- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/recipe/CreateRecipeViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/recipe/CreateRecipeScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodDatabaseScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt

Problemas

Nenhum.

Próximo passo

Implementar o Histórico (Fase 7).

## Sessão 009

Data

2026-06-30

Feature

Histórico (Fase 7).

Implementado

- Otimização YAGNI (ponytail): Em vez de criar uma tela separada e complexa para histórico, integramos a navegação de datas diretamente no Dashboard principal. Isso permite que o usuário navegue dia a dia e visualize/modifique os consumos de qualquer data reusando toda a pipeline.
- Criada a função expect/actual getOffsetDateString no DateUtils para fazer somas e subtrações de dias de forma nativa e offline (zero dependências extras).
- Atualizado o DashboardViewModel para expor o selectedDate, nextDay() e previousDay(), carregando as refeições e estatísticas de forma dinâmica para a data selecionada.
- Adicionado cabeçalho de navegação por datas no DashboardScreen, exibindo "Hoje", "Ontem" ou a data formatada ("DD/MM/AAAA").
- Atualizada a tela FoodDatabaseScreen e o FoodViewModel para receberem a data da navegação como argumento, garantindo que alimentos consumidos historicamente sejam salvos sob o dia que estava ativo no Dashboard.
- Configurada a rota parametrizada food_database/{date} no NavGraph.kt.

Arquivos alterados

- common/src/commonMain/kotlin/com/projeto/nutrilog/utils/DateUtils.kt
- common/src/androidMain/kotlin/com/projeto/nutrilog/utils/DateUtils.android.kt
- common/src/iosMain/kotlin/com/projeto/nutrilog/utils/DateUtils.ios.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodDatabaseScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt

Problemas

Nenhum.

Próximo passo

Implementar as Configurações (Fase 9).

## Sessão 010

Data

2026-06-30

Feature

Configurações (Fase 9).

Implementado

- Otimização YAGNI (ponytail): Reusado o SaveUserUseCase já existente para realizar atualizações de perfil e metas, evitando duplicações desnecessárias de código/queries de persistência.
- Criado o SettingsViewModel que carrega o estado do usuário ativo e fornece o método saveSettings para salvar as atualizações encapsuladas em um UserEntity.
- Criada a tela SettingsScreen contendo formulários auto-preenchidos para alteração de Nome, Meta Calórica, Proteínas, Carboidratos e Gorduras.
- Atualizado o DashboardScreen para incluir o botão "Ajustes" lado a lado com "Reset Perfil" no rodapé de controles da dashboard.
- Configurada a rota settings no NavGraph.kt e injeções no Koin.kt.

Arquivos alterados

- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/settings/SettingsViewModel.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/settings/SettingsScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/dashboard/DashboardScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/navigation/NavGraph.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/di/Koin.kt

Problemas

Nenhum.

Próximo passo

Implementar as Refatorações (Fase 10).

## Sessão 011

Data

2026-06-30

Feature

Refatorações (Fase 10).

Implementado

- Limpeza de avisos de depreciação (Warnings) de compilação em todo o projeto.
- Substituída a importação e o uso de `Icons.Default.ArrowBack` (depreciada em Compose Multiplatform) por `Icons.AutoMirrored.Filled.ArrowBack` nas telas `RegisterScreen.kt` e `FoodDatabaseScreen.kt`.
- Validação completa da compilação e empacotamento em nível de metadata e plataforma Android (`assembleDebug`).
- Conclusão de todos os marcos planejados e fechamento das metas do roadmap do projeto NutriLog!

Arquivos alterados

- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/register/RegisterScreen.kt
- common/src/commonMain/kotlin/com/projeto/nutrilog/presentation/food/FoodDatabaseScreen.kt

Problemas

Nenhum.

Próximo passo

Nenhum. Projeto 100% concluído!