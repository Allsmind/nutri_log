# Arquitetura

Padrão

MVVM

+

Clean Architecture

## Camadas

Presentation

↓

ViewModel

↓

UseCases

↓

Repository

↓

Local Data Source

↓

SQLDelight

## Shared

Toda regra de negócio permanece no módulo common.

Toda interface deve ser construída utilizando Compose Multiplatform.

## Offline First

Toda funcionalidade deve funcionar sem internet.

A sincronização será adicionada futuramente.

## Dependências

DI

Koin

Persistência

SQLDelight

Serialização

Kotlin Serialization

Navegação

Navigation Compose
