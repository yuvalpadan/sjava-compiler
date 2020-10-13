package oop.ex6.scopes;

import oop.ex6.main.Regex;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SJavaException;
import oop.ex6.main.SJavaException.*;
import oop.ex6.variable.Variable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;

/**
 * This class is an abstract class representing a scope in s-java file.
 */
public abstract class Scope {

    int lineCounter;
    Scope upperScope;
    ArrayList<String> content;
    HashMap<String, Variable> scopeVariables;
    static Matcher variableDeclare, variableAssign;
    private static final String SCOPE_OPENER = "{";
    private static final String SCOPE_CLOSER = "}";

    static final String INVALID_SYNTAX = "invalid syntax";
    static final String VAR_ALREADY_DEFINED = "Variable '%s' already defined in the scope";
    static final String VAR_NOT_DEFINED = "Cannot resolve symbol '%s'";
    private static final String FINAL_VAR_ASSIGN = "Cannot assign a value to final variable %s";

    /**
     * checks if the scope is valid according to the settings of s-java.
     *
     * @throws SJavaException if this scope is not valid
     */
    void scopeIsValid() throws SJavaException {
        updateMatchers(content.get(lineCounter));
        if (variableDeclare.matches()) this.variableDeclarationCheck();
        if (variableAssign.matches()) this.variableAssignmentCheck();
    }

    /**
     * updates the matchers can be found in the scope, according to specific line
     *
     * @param currentLine the line needs to be updated the matchers
     */
    void updateMatchers(String currentLine) {
        variableDeclare = Regex.VARIABLE_DECLARATION.matcher(currentLine);
        variableAssign = Regex.VARIABLE_ASSIGNMENT.matcher(currentLine);
    }

    /**
     * this function returns a variable with the given name inside and outside the scope
     *
     * @param varName the name of the variable to search
     * @return the variable with the same name if it was found, null otherwise.
     */
    public Variable getVariable(String varName) {
        Variable variable = scopeVariables.get(varName);
        if (variable != null) return variable;
        Scope curScope = getUpperScope();
        if (curScope != null) return curScope.getVariable(varName);
        return null;
    }

    /**
     * adds a variable to the scope variable
     *
     * @param varName  the name of the variable need to be added
     * @param variable the variable need to be added to the scope
     */
    void addVariable(String varName, Variable variable) {
        scopeVariables.put(varName, variable);
    }

    /**
     * returns the upper scope of this scope
     *
     * @return the upper scope of this scope
     */
    private Scope getUpperScope() {
        return upperScope;
    }

    /**
     * this method goes over the lines of this scope and returns its content
     *
     * @param content the content of the upper scope
     * @return String array list of the lines of this scope
     * @throws SJavaException if brackets are missing
     */
    ArrayList<String> subScopeContent(ArrayList<String> content) throws SJavaException {
        ArrayList<String> newContent = new ArrayList<>();
        int bracketsCounter = 1;
        while (bracketsCounter != 0 && lineCounter < content.size() - 1) {
            this.lineCounter++;
            String currentLine = content.get(this.lineCounter);
            if (currentLine.endsWith(SCOPE_OPENER)) bracketsCounter++;
            else if (currentLine.endsWith(SCOPE_CLOSER)) bracketsCounter--;
            newContent.add(currentLine);
        }
        if (bracketsCounter != 0)
            throw new SyntaxException(INVALID_SYNTAX);
        return newContent;
    }

    /**
     * this function checks if a variable declaration statement is valid.
     *
     * @throws VariableException variable exception.
     */
    void variableDeclarationCheck() throws SJavaException {
        String isFinal = variableDeclare.group(1);
        String varType = variableDeclare.group(2);
        String subVars[] = variableDeclare.group(3).split(RegexPatterns.COMMA);
        String varName, varValue;
        HashSet<String> allNames = new HashSet<>();
        for (String subVar : subVars) {
            String assignment[] = subVar.split(RegexPatterns.ASSIGNMENT_SIGN);
            varName = assignment[0].trim();
            if (allNames.contains(varName))
                throw new VariableException(String.format(VAR_ALREADY_DEFINED, varName));
            else allNames.add(varName);
            if (assignment.length > 1) varValue = assignment[1].trim();
            else varValue = null;
            Variable newVar = new Variable(isFinal, varType, varName, varValue, this, false);
            this.addVariable(varName, newVar);
        }
    }

    /**
     * this function checks if a variable assignment is valid.
     *
     * @throws VariableException if the variable is not found or it is final.
     */
    void variableAssignmentCheck() throws SJavaException {
        String varName = variableAssign.group(1);
        String assignment = variableAssign.group(2);
        Variable variable = getVariable(varName);
        if (variable == null) throw new VariableException(String.format(VAR_NOT_DEFINED, varName));
        if (variable.getName().equals(varName))
            if (variable.isFinal()) throw new VariableException(String.format(FINAL_VAR_ASSIGN, varName));
        variable.checkVariableAssignment(assignment);
        variable.initialize();
    }
}
