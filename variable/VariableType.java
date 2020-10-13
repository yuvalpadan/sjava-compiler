package oop.ex6.variable;

import oop.ex6.main.RegexPatterns;
import java.util.regex.Pattern;

/**
 * this enum represents all possible types of variable in s-java.
 */
public enum VariableType {

    STRING(RegexPatterns.STRING),
    DOUBLE(RegexPatterns.DOUBLE),
    BOOLEAN(RegexPatterns.BOOLEAN),
    INTEGER(RegexPatterns.INTEGER),
    CHARACTER(RegexPatterns.CHARACTER);

    private Pattern pattern;

    /**
     * constructor of this enum
     *
     * @param regex regex pattern compatible to the specific type.
     */
    VariableType(String regex) {
        this.pattern = Pattern.compile(regex);
    }


    /**
     * The function returns a VariableType compatible with the given string
     *
     * @param str the string of the type's name.
     * @return the type compatible with the given string, null otherwise.
     */
    public static VariableType getNewType(String str) {

        switch (str) {
            case "String":  return STRING;
            case "int":     return INTEGER;
            case "double":  return DOUBLE;
            case "boolean": return BOOLEAN;
            case "char":    return CHARACTER;
        }
        return null;
    }

    /**
     * @param str the string needs to be checked if compatible to the type
     * @return true if the string is match to the type, false otherwise
     */
    public boolean checkMatch(String str) {
        return this.pattern.matcher(str).matches();
    }
}
