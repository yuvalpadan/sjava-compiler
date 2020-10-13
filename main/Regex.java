package oop.ex6.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this enum represents all necessary regex for this program.
 */
public enum Regex {

    BOOLEAN(RegexPatterns.BOOLEAN),
    END_BLOCK(RegexPatterns.END_BLOCK),
    METHOD_CALL(RegexPatterns.METHOD_CALL),
    BLOCK_STATEMENT(RegexPatterns.BLOCK_STATEMENT),
    RETURN_STATEMENT(RegexPatterns.RETURN_STATEMENT),
    METHOD_DECLARATION(RegexPatterns.METHOD_DECLARATION),
    VARIABLE_ASSIGNMENT(RegexPatterns.VARIABLE_ASSIGNMENT),
    VARIABLE_DECLARATION(RegexPatterns.VARIABLE_DECLARATION);

    private Pattern pattern;


    /**
     * constructor of this enum
     *
     * @param regex the compatible regex pattern
     */
    Regex(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    /**
     * matches the given string to this regex.
     *
     * @param str the string to match.
     * @return Matcher of the given string to the pattern of this regex.
     */
    public Matcher matcher(String str) {
        return pattern.matcher(str);
    }


}
