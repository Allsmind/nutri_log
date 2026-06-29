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