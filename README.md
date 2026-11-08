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
| `GeneerateAST.java` | Abstract Syntax Tree Generator. |

## Getting Started

### Prerequisites

- JDK 25.0.5

### Build & Run

## Compile the sources
```
javac *.java
```

## Run the interpreter
```
java MyLang <script>
```
## Generate an Abstract Syntax Tree (AST)
```
java GenerateAST <outputFolder path>
```
### Example output of GenerateAST.java:
```
import java.util.List;

abstract class Expr {
    interface Visitor<R> {
    R visitBinaryExpr(Binary expr);
    R visitGroupingExpr(Grouping expr);
    R visitLiteralExpr(Literal expr);
    R visitUnaryExpr(Unary expr);
    }
    static class Binary extends Expr {
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }
    static class Grouping extends Expr {
        Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        final Expr expression;
    }
    static class Literal extends Expr {
        Literal(Object value) {
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        final Object value;
    }
    static class Unary extends Expr {
        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        final Token operator;
        final Expr right;
    }

    abstract <R> R accept(Visitor<R> visitor);
}


```

Author
Hami Ibriyamov
