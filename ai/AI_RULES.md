# Regras para o Agente

Antes de escrever qualquer código:

1. Leia PROJECT.md.
2. Leia ARCHITECTURE.md.
3. Leia DECISIONS.md.
4. Leia ROADMAP.md.
5. Leia CURRENT_TASK.md.

Nunca implemente funcionalidades fora da CURRENT_TASK.

Nunca altere decisões arquiteturais sem atualizar DECISIONS.md.

Nunca faça refatorações não solicitadas.

Nunca mude bibliotecas sem justificativa técnica.

Nunca remova funcionalidades existentes.

Ao finalizar qualquer tarefa:

- Atualize DEVELOPMENT_LOG.md.
- Atualize CURRENT_TASK.md.
- Atualize ROADMAP.md (quando aplicável).
- Atualize CHANGELOG.md.
- Gere um commit seguindo Conventional Commits.

Se houver dúvida entre o prompt inicial e os documentos do projeto, considere a seguinte ordem de prioridade:

1. CURRENT_TASK.md
2. DECISIONS.md
3. ARCHITECTURE.md
4. ROADMAP.md
5. Código existente
6. Prompt original

Caso identifique inconsistências, pare a implementação e explique o problema em vez de tomar uma decisão automaticamente.