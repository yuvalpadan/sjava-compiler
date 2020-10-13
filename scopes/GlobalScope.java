package oop.ex6.scopes;

import oop.ex6.main.Regex;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SJavaException;
import oop.ex6.main.SJavaException.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * this class represents a global scope of s-java file
 * this class represents Scope class
 */
public class GlobalScope extends Scope {

    private HashMap<String, MethodScope> allMethods;
    private static Matcher methodDeclared;
    private static final String DUPLICATE_METHOD = "'%s()' is already defined";

    /**
     * Constructor - creates new global scope
     *
     * @param content String array list of the lines of the global scope.
     */
    public GlobalScope(ArrayList<String> content) {
        this.allMethods = new HashMap<>();
        this.scopeVariables = new HashMap<>();
        this.upperScope = null;
        this.content = content;
    }

    /**
     * checks if the scope is valid according to the settings of s-java.
     *
     * @throws SJavaException if this scope is not valid
     */
    public void scopeIsValid() throws SJavaException {
        while (lineCounter < content.size()) {
            updateMatchers(content.get(lineCounter));
            //checks matches
            if (methodDeclared.matches()) methodDeclarationCheck();
            else if (variableDeclare.matches()) variableDeclarationCheck();
            else if (variableAssign.matches()) variableAssignmentCheck();
            else throw new SyntaxException(INVALID_SYNTAX + content.get(lineCounter));
            lineCounter++;
        }
        checkMethodsAreValid();
    }

    /**
     * adds a method to collection represents all the methods in the scope
     *
     * @param methodName  the method name
     * @param methodScope the scope of this method
     * @return the previous method with the correspond name if it already exist, null otherwise
     */
    private MethodScope addMethod(String methodName, MethodScope methodScope) {
        return allMethods.put(methodName, methodScope);
    }

    /**
     * this function returns the method compatible the given name if it exist in this scope
     *
     * @param methodToSearch the name of the method to search
     * @return the method compatible the given name if it exist, null otherwise
     */
    public MethodScope getMethodByName(String methodToSearch) {
        return allMethods.get(methodToSearch);
    }

    /**
     * updates the matchers can be found in the scope, according to specific line
     *
     * @param currentLine the line needs to be updated the matchers
     */
    void updateMatchers(String currentLine) {
        super.updateMatchers(currentLine);
        methodDeclared = Regex.METHOD_DECLARATION.matcher(currentLine);
    }

    /**
     * this function checks if a method declaration statement is valid.
     *
     * @throws SJavaException if the method declaration statement is invalid
     */
    private void methodDeclarationCheck() throws SJavaException {
        String methodName = methodDeclared.group(1);
        String[] arguments = methodDeclared.group(2).split(RegexPatterns.COMMA);
        ArrayList<String> methodContent = subScopeContent(content);

        MethodScope methodScope = new MethodScope(arguments, methodContent, this, this);
        //if there is already method with the same name
        if (addMethod(methodName, methodScope) != null)
            throw new MethodException(String.format(DUPLICATE_METHOD, methodName));
    }

    /**
     * checks that all the method in this scope are valid
     *
     * @throws SJavaException if one of the methods isn't valid
     */
    private void checkMethodsAreValid() throws SJavaException {
        for (MethodScope methodScope : allMethods.values()) methodScope.scopeIsValid();
    }
}
