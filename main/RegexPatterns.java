package oop.ex6.main;

/**
 * This class represents all the necessary regex patterns for this program
 */
public class RegexPatterns {

    //types of variable
    public static final String STRING = "\".*\"";
    public static final String INTEGER = "-?\\d+";
    public static final String CHARACTER = "\'.\'";
    public static final String DOUBLE = "-?\\d*\\.?\\d+";
    public static final String BOOLEAN = "true|false|" + DOUBLE + "|" + INTEGER;

    //valid names in s-java
    private static final String METHOD_NAME = "[a-zA-Z]+\\w*";
    private static final String VAR_NAME = "_\\w+|[a-zA-Z]+\\w*";
    private static final String VAR_TYPE = "String|int|double|boolean|char";

    //chars in s-java
    public static final String COMMA = "\\s*,\\s*";
    public static final String WHITE_SPACE = "\\s+";
    public static final String END_BLOCK = "\\s*}\\s*";
    public static final String WHITE_SPACE_OR_NOT = "\\s*";
    public static final String ASSIGNMENT_SIGN = "\\s*=\\s*";
    public static final String COMMENT_OR_BLANK = "//.*\\s*|\\s*";
    public static final String CONDITIONAL_OPERATORS = "\\s*\\|\\|\\s*|\\s*&&\\s*";

    //valid statements in s-java
    public static final String RETURN_STATEMENT = "return\\s*;";
    public static final String BLOCK_STATEMENT = "\\s*(?:if|while)\\s*\\((.*)\\)\\s*\\{\\s*";
    private static final String ASSIGNMENT = STRING + "|" + DOUBLE + "|" + CHARACTER + "|" + BOOLEAN;
    private static String SUB_PARAM_COMMA = "(?:\\s*(?:(?:" + VAR_NAME + ")|(?:" + ASSIGNMENT + "))\\s*,)";
    private static String SUB_PARAM = "(?:\\s*(?:(?:" + VAR_NAME + ")|(?:" + ASSIGNMENT + ")))";
    public static final String METHOD_CALL = "(" + METHOD_NAME + ")\\s*\\(\\s*(\\s*|" +
            SUB_PARAM_COMMA + "*" + SUB_PARAM + "+)\\s*\\)\\s*;";
    private static final String SUB_DECLARE =
            "(?:" + VAR_NAME + "\\s*(?:=\\s*(" + ASSIGNMENT + "|" + VAR_NAME + "))?\\s*";
    public static final String VARIABLE_DECLARATION = "\\s*(final\\s+)?(" + VAR_TYPE + ")\\s+(" +
            SUB_DECLARE + ",\\s*)*" + SUB_DECLARE + "))\\s*;\\s*";
    public static final String VARIABLE_ASSIGNMENT =
            "(" + VAR_NAME + ")\\s*=\\s*(" + ASSIGNMENT + ")" + "\\s*;";
    public static final String METHOD_DECLARATION = "\\s*void\\s+(" + METHOD_NAME + ")\\s*\\((\\s*|" +
            "(\\s*(?:final)?\\s*(" + VAR_TYPE + ")\\s*(" + VAR_NAME + ")\\s*,\\s*)*(\\s*(?:final)?\\s*(" +
            VAR_TYPE + ")\\s*(" + VAR_NAME + ")\\s*)+)\\)\\s*\\{\\s*";
}