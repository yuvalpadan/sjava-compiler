package oop.ex6.scopes;

import oop.ex6.main.SJavaException.*;
import oop.ex6.main.Regex;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SJavaException;
import oop.ex6.variable.VariableType;
import oop.ex6.variable.Variable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * this class is an abstract class represents an inner scope in s-java file.
 * this class extends Scope class
 */
abstract class InnerScope extends Scope {

    private GlobalScope globalScope;
    private static Matcher methodCall, blockStatement, returnStatement, endBlock;

    private static final String METHOD_NOT_FOUND = "this method does not exist";
    private static final String NUM_ARGS_ERROR = "number of arguments doesn't match";
    private static final String PARAM_NOT_INITIALIZED = "the param is not initialized";
    static final String TYPE_NOT_MATCH = "the vars type are not type-match";

    InnerScope(ArrayList<String> content, Scope upperScope, GlobalScope globalScope) {
        this.upperScope = upperScope;
        this.scopeVariables = new HashMap<>();
        this.content = content;
        this.globalScope = globalScope;
    }

    /**
     * checks if the content of this scope is valid
     *
     * @throws SJavaException if the scope isn't valid
     */
    void checkScopeContent() throws SJavaException {
        String lastLine = content.get(content.size() - 1);
        if (!Regex.END_BLOCK.matcher(lastLine).matches())
            throw new SyntaxException(INVALID_SYNTAX);

        while (lineCounter < content.size()) {
            updateMatchers(content.get(lineCounter));
            if (variableDeclare.matches()) variableDeclarationCheck();
            else if (variableAssign.matches()) variableAssignmentCheck();
            else if (blockStatement.matches()) blockCheck();
            else if (methodCall.matches()) methodCallCheck();
            else if (!returnStatement.matches() && !endBlock.matches())
                throw new SyntaxException(INVALID_SYNTAX + content.get(lineCounter));
            lineCounter++;
        }
    }

    /**
     * checks if the scope signature is valid
     *
     * @throws SJavaException if the scope signature is not valid
     */
    abstract void checkScopeSignature() throws SJavaException;

    /**
     * updates the matchers can be found in the scope, according to specific line
     *
     * @param currentLine the line needs to be updated the matchers
     */
    void updateMatchers(String currentLine) {
        super.updateMatchers(currentLine);
        blockStatement = Regex.BLOCK_STATEMENT.matcher(currentLine);
        returnStatement = Regex.RETURN_STATEMENT.matcher(currentLine);
        endBlock = Regex.END_BLOCK.matcher(currentLine);
        methodCall = Regex.METHOD_CALL.matcher(currentLine);
    }

    /**
     * this function checks if a block is valid.
     *
     * @throws VariableException      variable exception.
     * @throws SyntaxException invalid syntax exception.
     * @throws MethodException        method exception.
     */
    private void blockCheck() throws SJavaException {
        String[] condition = blockStatement.group(1).split(RegexPatterns.CONDITIONAL_OPERATORS);
        ArrayList<String> blockContent = this.subScopeContent(content);
        BlockScope blockScope = new BlockScope(condition, blockContent, this, globalScope);
        blockScope.scopeIsValid();
    }

    /**
     * this functions checks if a method call statement is valid.
     *
     * @throws VariableException variable exception.
     * @throws MethodException   method exception.
     */
    private void methodCallCheck() throws SJavaException {
        String methodName = methodCall.group(1);
        String argumentsString = methodCall.group(2);
        String[] argumentsForCheck = argumentsString.split(RegexPatterns.COMMA);
        if (argumentsForCheck[0].equals("")) argumentsForCheck = new String[0];
        MethodScope method = globalScope.getMethodByName(methodName);
        if (method == null) throw new MethodException(METHOD_NOT_FOUND);
        if (method.numOfArgument(method.arguments) != argumentsForCheck.length)
            throw new MethodException(NUM_ARGS_ERROR);
        //there are actually parameters in the method call
        for (int j = 0; j < argumentsForCheck.length; j++) {
            String curArgument = argumentsForCheck[j].trim();
            String[] assignment = method.arguments[j].split(RegexPatterns.WHITE_SPACE);
            VariableType realVariableType = VariableType.getNewType(assignment[0]);
            Variable var = getVariable(curArgument);
            if (var != null) {
                if (!var.isInitialized()) throw new VariableException(PARAM_NOT_INITIALIZED);
                if (realVariableType != var.getVariableType()) throw new VariableException(TYPE_NOT_MATCH);
            } else if (realVariableType != null && !realVariableType.checkMatch(curArgument))
                throw new VariableException(TYPE_NOT_MATCH);
        }
    }
}
