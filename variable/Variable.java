package oop.ex6.variable;

import oop.ex6.scopes.*;
import oop.ex6.main.SJavaException.*;

public class Variable {
    private VariableType variableType;
    private String name;
    private Scope scope;
    private boolean isFinal, isInitialized, inMethodSignature;
    private static final String INCOMPATIBLE_TYPES = "Incompatible types";
    private static final String VAR_NOT_DEFINED = "Cannot resolve symbol '%s'";
    private static final String VAR_ALREADY_DEFINED = "Variable '%s' already defined in the scope";
    private static final String VAR_NOT_INITIALIZED = "Variable '%s' might not have been initialized";

    /**
     * this class represent a variable in s-java file
     *
     * @param isFinal           "final" string, if the variable is final
     * @param varType           String of the variableType of the variable
     * @param varName           String of the name of the variable
     * @param varValue          String of the value of the variable, null if it isn't initialize
     * @param scope             the scope in which the sting was found
     * @param inMethodSignature true if the variable in a method signature, false otherwise
     * @throws VariableException Variable exception
     */
    public Variable(String isFinal, String varType, String varName, String varValue,
                    Scope scope, boolean inMethodSignature) throws VariableException {

        this.name = varName;
        this.scope = scope;
        this.isFinal = isFinal != null;
        this.isInitialized = varValue != null;
        this.variableType = VariableType.getNewType(varType);
        this.inMethodSignature = inMethodSignature;

        //checks the variable declaration and assignment if necessary
        checkVariableDeclaration();
        if (isInitialized)
            checkVariableAssignment(varValue);
    }

    /**
     * this function checks if a variable declaration statement is valid
     *
     * @throws VariableException if the statement is not valid
     */
    private void checkVariableDeclaration() throws VariableException {
        Variable foundVar = scope.getVariable(name);
        if (!inMethodSignature && foundVar != null &&
                (!(foundVar.scope instanceof GlobalScope) || foundVar.scope == scope))
            throw new VariableException(String.format(VAR_ALREADY_DEFINED, name));
        if (isFinal && !isInitialized)
            throw new VariableException(String.format(VAR_NOT_INITIALIZED, name));
    }

    /**
     * this function checks if a variable assignment is valid
     *
     * @param newValue the new value to assign to the variable
     * @throws VariableException if the assignment is not valid
     */
    public void checkVariableAssignment(String newValue) throws VariableException {
        if (!variableType.checkMatch(newValue)) {
            Variable foundVariable = scope.getVariable(newValue);
            if (foundVariable == null)
                throw new VariableException(String.format(VAR_NOT_DEFINED, name));
            if (!foundVariable.inMethodSignature && !foundVariable.isInitialized())
                throw new VariableException(String.format(VAR_NOT_INITIALIZED, foundVariable.name));
            if (!(foundVariable.getVariableType() == VariableType.INTEGER && (variableType == VariableType.DOUBLE ||
                    variableType == VariableType.BOOLEAN)) && !(foundVariable.getVariableType() == VariableType.DOUBLE &&
                    variableType == VariableType.BOOLEAN) && !(foundVariable.getVariableType() == variableType))
                throw new VariableException(INCOMPATIBLE_TYPES);
        }
    }

    /**
     * returns the variableType of this variable.
     *
     * @return the variableType of this variable.
     */
    public VariableType getVariableType() {
        return variableType;
    }

    /**
     * returns true if this variable is initialized, false otherwise.
     *
     * @return true if this variable is initialized, false otherwise.
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * returns true if this variable is final, false otherwise
     *
     * @return true if this variable is final, false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * returns the name of this variable
     *
     * @return the name of this variable
     */
    public String getName() {
        return name;
    }

    /**
     * changes the boolean data member "isInitialized" to be true.
     */
    public void initialize() {
        isInitialized = true;
    }

}
