# Vibe Coding Architecture

This document outlines the standard architecture for our mobile project, ensuring clean separation of concerns and high maintainability.

## Directory Structure

```text
lib/
├── core/               # App-wide constants, theme, and utility functions
│   ├── constants/
│   ├── theme/
│   └── utils/
├── data/               # Data layer: Models, Repositories implementations, API services
│   ├── models/
│   ├── repositories/
│   └── services/
├── domain/             # Domain layer: Entities, Repository interfaces
│   ├── entities/
│   └── repositories/
├── presentation/       # UI layer: Screens, reusable widgets, and state management (Bloc/Provider)
│   ├── blocs/
│   ├── screens/
│   └── widgets/
└── main.dart           # App entry point
```

## Rules
1. **Clean Architecture**: The UI (`presentation`) never communicates directly with `data`. It goes through `domain` (or business logic blocs).
2. **Naming**: Use `snake_case` for files/folders, `PascalCase` for classes, `camelCase` for variables/methods.
3. **Performance**: Avoid monolithic widgets. Break them down into smaller stateless widgets when possible.
