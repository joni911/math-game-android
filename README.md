# Math Fun! - Android Game

Aplikasi game matematika edukatif untuk anak usia 5 tahun.

## Features
- 2-player local multiplayer
- Split-screen layout dengan rotasi 180°
- 4 level progressive difficulty
- Soft competition scoring (no punishment)
- First-tap lock mechanic
- Penjumlahan dan pengurangan (1-12)
- Colorful UI dengan feedback visual

## Tech Stack
- **Language:** Kotlin 100%
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Architecture:** Native Android with View Binding
- **UI:** Material Design Components

## Build
```bash
./gradlew assembleDebug
```

## Project Structure
```
app/src/main/
├── java/com/mathfun/game/
│   ├── ui/              # Activities
│   ├── model/           # Data models
│   ├── game/            # Game logic
│   └── audio/           # Audio system (TBD)
├── res/
│   ├── layout/          # XML layouts
│   ├── values/          # Strings, colors, themes
│   └── drawable/        # Icons, graphics
└── AndroidManifest.xml
```

## License
Public Domain