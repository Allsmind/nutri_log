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