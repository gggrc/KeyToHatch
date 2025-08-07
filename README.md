# 🐣 KeytoHatch – Java Typing Game

**KeytoHatch** is a simple, fun, and educational Java-based typing game that challenges your typing speed and accuracy. It features a basic GUI using Java Swing and offers three different game modes for players of all skill levels.

![Java](https://img.shields.io/badge/Built%20with-Java%20Swing-orange?style=flat-square)
![Platform](https://img.shields.io/badge/Platform-Desktop-blue?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## 🎮 Features

- 🏁 **Game Menu** – Simple and intuitive interface to choose game modes.
- 🐣 **Easy Mode** – Slower word fall speed, ideal for beginners.
- 🐥 **Medium Mode** – Faster-paced typing challenge.
- 🔥 **Survival Mode** – Endless words, increasing difficulty over time.
- 💀 **Game Over Screen** – See your result when you fail to keep up.
- ⌨️ **Custom Key Listener** – Responsive typing mechanics using a custom `KeyAdapter`.

---

## 📁 Project Structure

```text
KeytoHatch/
│
├── src/
│   ├── Main.java
│   ├── GameMenu.java
│   ├── GameOver.java
│   ├── TypingGameEasy.java
│   ├── TypingGameMedium.java
│   ├── TypingGameSurvival.java
│   └── KeyAdapter.java
│
├── bin/
├── .vscode/
└── README.md

---

## 🚀 Getting Started

### ✅ Requirements

- Java JDK 8 or higher
- Any Java IDE (VS Code, IntelliJ, Eclipse, etc.)
- Or command-line terminal

### 🛠️ Running the Project

#### 🔧 Compile via Terminal

```bash
# Navigate to project root folder
javac -d bin src/*.java

▶️ Run the Game
java -cp bin Main

🧠 Game Overview
Words fall from the top of the screen.
You must type each word before it hits the bottom.
Each mode offers different difficulty:
- Easy: Slower pace, basic words
- Medium: Faster and slightly harder words
- Survival: Endless mode, increasing difficulty
Fail to type words fast enough, and the game ends.

