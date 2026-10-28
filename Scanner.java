import java.util.*;

class Scanner {
    /**
     * We store the raw source code as a simple string, and we have a 
     * list ready to fill with tokens we're going to generate. 
     */
    private final String source;
    private final List<Token> tokens = new ArrayList<>(); // reserves pre-defined memory for the list
    private int start = 0; // points to the first char in the string
    private int current = 0; // points at the current char in the string
    private int line = 1; // track the location of the current char so we can produce tokens that know their location


    Scanner (String source) {
        this.source = source;
    }

    /** 
     * Builds a list of tokens
     */
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme
            start = current;
            scanToken();
        }

        // append a null pointer (like in C)
        tokens.add(new Token(TokenType.EDF, "", null, line));
        
        // return the token list
        return tokens;
    }

    /**
     * Map individual tokens
     */
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(' -> { addToken(TokenType.LEFT_PAREN); }
            case ')' -> { addToken(TokenType.RIGHT_PAREN); }
            case '{' -> { addToken(TokenType.LEFT_BRACE); }
            case '}' -> { addToken(TokenType.RIGHT_BRACE); }
            case ',' -> { addToken(TokenType.COMMA); }
            case '.' -> { addToken(TokenType.DOT); }
            case '-' -> { addToken(TokenType.MINUS); }
            case '+' -> { addToken(TokenType.PLUS); }
            case ';' -> { addToken(TokenType.SEMICOLON); }
            case '*' -> { addToken(TokenType.STAR); }
            // operators
            case '!' -> {
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
            }
            case '=' -> {
                addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
            }
            case '<' -> {
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
            }
            case '>' -> {
                addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
            }
            // Longer lexemes
            case '/' -> { 
                if (match('/')) {
                    // a comment goes until the end of the line so we skip ahead until that, without calling addToken()
                    while(peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
            }
            case ' ', '\r', '\t' -> {
                // Skip over whitespace
            }
            case '\n' -> {
                line++;
            }
            // String literals
            case '"' -> {
                string();
            }
            default -> {
                // Number literals
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    MyLang.error(line, "Unexpected character.");
                }
            }
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        addToken(TokenType.IDENTIFIER);
    }

    /*
     * Have we consumed all characters
     */
    private boolean isAtEnd() {
        return current >= source.length();
    }

    /**
     * Consumes the next character in the source file and returns it
     */
    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    /**
     * Grabs the text of the current lexeme and creates a new token for it
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    /**
     * Checks if the next character in the source code matches an expected character
     */
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    /**
     * Looks up the next character in the source code and returns it
     * This is called lookahead, the smaller the number of character it has to look forward to, 
     * the faster the scanner runs
     */
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    /**
     * Looks up two characters ahead
     */
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    /**
     * Consumes a string literal
     * Supports multi-line strings
     */
    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            MyLang.error(line, "Unterminated string.");
            return;
        }

        // the closing "
        advance();

        // Trim the surrounding quotes
        // If Lox supported escape sequences like \n, we would unescape those here
        String value = source.substring(start + 1, current - 1);

        // Add the string token
        addToken(TokenType.STRING, value);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Consumes digits of integer and floating-point values
     */
    private void number() {
        while(isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the '.' only if it is followed by a digit
            // .123 and 123. are invalid inputs in Lox
            advance();

            while(isDigit(peek())) advance();
        }

        addToken(TokenType.NUMBER,
            Double.valueOf(source.substring(start, current)));
    }

    private boolean isAlpha(char c) {
        Character.toLowerCase(c);
        return (c >= 'a' && c <= 'z') || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
