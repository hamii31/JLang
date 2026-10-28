/**
 * Each token has a type, a lexeme, a literal value, and a variable that
 * tells us on which line it appears
 */

class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line; 
    
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
