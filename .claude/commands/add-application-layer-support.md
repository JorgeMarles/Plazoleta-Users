---
name: add-application-layer-support
description: Workflow command scaffold for add-application-layer-support in Plazoleta-Users.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /add-application-layer-support

Use this workflow when working on **add-application-layer-support** in `Plazoleta-Users`.

## Goal

Adds DTOs, mappers, and handlers to expose domain features to the application layer.

## Common Files

- `src/main/java/com/jamarlesf/plazoletausers/application/dto/request/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/application/dto/response/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/application/mapper/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/application/handler/*.java`
- `src/main/java/com/jamarlesf/plazoletausers/application/handler/impl/*.java`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Create or update DTOs in application/dto/request/ and application/dto/response/
- Create or update mappers in application/mapper/
- Add or update handlers in application/handler/ and application/handler/impl/

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.