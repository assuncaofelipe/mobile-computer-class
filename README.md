## Estrutura de pastas - MVVM não modularizado

App/
├── features/
│   ├── login/
│   │   ├── LoginActivity.kt
│   │   ├── LoginViewModel.kt
│   │   ├── LoginModel.kt
│   │   └── ui/
│   └── home/
│       ├── HomeActivity.kt
│       ├── HomeViewModel.kt
│       ├── HomeModel.kt
│       └── ui/
├── common/
│   ├── adapter/
│   ├── utils/
│   └── ui/
│       └── theme/
│           ├── Color.kt
│           ├── Theme.kt
│           └── Type.kt
├── MainActivity.kt
