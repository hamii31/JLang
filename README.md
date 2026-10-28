# JLang

A custom programming language interpreter implemented in Java.

## About

JLang is a personal project implementing a language interpreter from scratch, wrapped in Java. It covers the core stages of interpretation—scanning source code into tokens and processing them for execution.

## Project Structure

| File | Description |
|------|-------------|
| `MyLang.java` | Entry point and main interpreter driver. |
| `Scanner.java` | Lexical scanner that reads source code and produces tokens. |
| `Token.java` | Represents an individual token (lexeme, type, literal, line). |
| `TokenType.java` | Enum defining the supported token types. |

## Getting Started

### Prerequisites

- JDK 25.0.5

### Build & Run

# Compile the sources
```
javac *.java
```

# Run the interpreter
```
java MyLang [script]
```

Author
Hami Ibriyamov
