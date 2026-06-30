# NutriLog 🥑

O **NutriLog** é um aplicativo premium de acompanhamento calórico e nutricional desenvolvido em **Kotlin Multiplatform (KMP)** e **Compose Multiplatform**. O app oferece uma experiência moderna, fluida e elegante para gerenciar metas diárias de calorias e macronutrientes (Proteínas, Carboidratos e Gorduras) com banco de dados local persistente e funcionamento 100% offline.

---

## ✨ Funcionalidades

* 👤 **Cadastro de Perfil & Metas**: Registro inicial e configuração personalizada de metas calóricas e divisão de macronutrientes.
* 📅 **Navegação Histórica Inteligente**: Navegue dia a dia no Dashboard para visualizar refeições anteriores e registrar consumos em datas passadas.
* 🥗 **Banco de Alimentos Pré-carregado**: Busca rápida em uma base de dados local offline pré-semeada (arroz, frango, ovos, aveia, etc.).
* 🍳 **Criação de Receitas Personalizadas**: Combine múltiplos ingredientes informando as quantidades desejadas. O aplicativo consolida os macros automaticamente e salva o resultado final como um alimento com peso fixo.
* 📊 **Estatísticas Completas**: Gráfico de histórico de consumo dos últimos 7 dias renderizado de forma nativa e responsiva em Compose, com destaque para dias que excederam as metas, além de médias de consumo de macronutrientes.
* ⚙️ **Configurações Rápidas**: Edite o nome do seu perfil ou atualize suas metas calóricas e de macros a qualquer momento com salvamento persistente.

---

## 🛠️ Stack Tecnológica

O projeto foi construído utilizando as melhores práticas de desenvolvimento mobile multiplataforma:

* **Core**: Kotlin Multiplatform (KMP)
* **UI**: Compose Multiplatform (Interface declarativa unificada para Android/iOS)
* **Arquitetura**: Clean Architecture (Separação estrita de camadas: Presentation, Domain e Data)
* **Injeção de Dependência**: Koin (Gerenciamento de ViewModels, Repositórios e Casos de Uso)
* **Banco de Dados Local**: SQLDelight (Persistência local persistente e fortemente tipada usando SQLite nativo)
* **Design System**: Material Design 3 com paleta escura (Dark Theme) premium

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
* **Android Studio** instalado (Jellyfish ou superior)
* **Java JDK 21** configurado
* Dispositivo Android conectado ou Emulador ativo

### Instalação no Android
Abra o terminal na raiz do projeto e execute:

```powershell
# Compilar e instalar o app no emulador/dispositivo conectado
.\gradlew :androidApp:installDebug
```

Para gerar apenas o pacote de instalação do Android (APK):
```powershell
.\gradlew :androidApp:assembleDebug
```

---

## 📂 Estrutura do Projeto

O código está dividido em módulos bem definidos:

* `:common`: Módulo multiplataforma contendo toda a lógica compartilhada.
  * `data/`: Implementação dos repositórios e acesso a dados (SQLDelight).
  * `domain/`: Modelos de dados e Casos de Uso (Business Logic).
  * `presentation/`: ViewModels e as telas em Compose Multiplatform.
  * `navigation/`: Grafo de navegação reativa (`NavGraph`).
  * `di/`: Módulos de injeção de dependência (`Koin`).
* `:androidApp`: Wrapper nativo mínimo que inicializa o aplicativo na plataforma Android.
