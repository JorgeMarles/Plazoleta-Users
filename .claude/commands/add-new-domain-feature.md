---
name: add-new-domain-feature
description: Workflow command scaffold for add-new-domain-feature in Plazoleta-Users.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /add-new-domain-feature

Use this workflow when working on **add-new-domain-feature** in `Plazoleta-Users`.

## Goal

Adds a new domain feature or capability, including models, exceptions, ports, and use cases.

## Common Files

- `src/main/java/com/jamarlesf/plazoletausers/domain/model/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/domain/exception/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/domain/api/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/domain/spi/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/domain/usecase/*.java`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Create or update domain models in domain/model/
- Add or update exceptions in domain/exception/
- Define service and/or persistence ports in domain/api/ and domain/spi/
- Implement use case logic in domain/usecase/

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.