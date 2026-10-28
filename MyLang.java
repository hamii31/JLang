import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class MyLang {
    static boolean hadError = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    /** 
     * Reads a file
     * FilePath given as argument from command-line
     */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        
        if (hadError) System.exit(65);
    }

    /**
     * Fire up jlox without arguments, and it drops you into a prompt
     * where you can enter and execute code one line at a time
     */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            // We need to reset the error flag in the interactive loop
            // If the user makes a mistake, it shouldn't kill the entire session
            hadError = false;
        }
    }

    /** 
     * Both the prompt and file runner are thin wrappers around this core function
     */
    private static void run (String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // for now, just print tokens
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    /**
     * This error() function and its report() helper tells the user 
     * some syntax error occurred on a given line. This is the bare minimum
     * to be able to claim you even have error reporting
     */
    static void error(int line, String message) {
        report(line, "", message);
    }
    private static void report(int line, String where, String message) {
        System.err.println(
            "[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }
}
