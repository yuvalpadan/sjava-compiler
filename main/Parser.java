package oop.ex6.main;

import oop.ex6.scopes.*;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static Pattern commentOrBlank = Pattern.compile(RegexPatterns.COMMENT_OR_BLANK);

    /**
     * The main parser of the given s-java file.
     *
     * @param sJavaFile s-java file to parse
     * @throws IOException    IOE exception.
     * @throws SJavaException if the file's code is not valid
     */
    public static void parseFile(File sJavaFile) throws IOException, SJavaException {
        FileReader reader = new FileReader(sJavaFile);
        BufferedReader buffer = new BufferedReader(reader);
        String firsLine = buffer.readLine();
        ArrayList<String> fileContent = goOver(buffer, firsLine);
        GlobalScope globalScope = new GlobalScope(fileContent);
        globalScope.scopeIsValid();
    }

    /**
     * this function goes over all the lines of the given file and filter non-relevant lines.
     *
     * @param buffer buffered reader
     * @param line   the first line of the s-java file
     * @return a filtered String array list of all the relevant lines in the file
     * @throws IOException if a problem with the file has been found
     */
    private static ArrayList<String> goOver(BufferedReader buffer, String line) throws IOException {
        ArrayList<String> content = new ArrayList<>();
        while (line != null) { //filter non-relevant lines
            Matcher curMatcher = commentOrBlank.matcher(line);
            if (!curMatcher.matches())
                content.add(line.trim());
            line = buffer.readLine();
        }
        return content;
    }
}
