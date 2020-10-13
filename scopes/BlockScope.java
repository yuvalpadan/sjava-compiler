package oop.ex6.scopes;

import oop.ex6.main.Regex;
import oop.ex6.main.SJavaException;
import oop.ex6.main.SJavaException.*;
import oop.ex6.variable.VariableType;
import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * this class represents a scope of a block (while / if blocks) in s-java file.
 * the class extends InnerScope class.
 */
class BlockScope extends InnerScope {

    private String[] conditions;

    /**
     * Constructor - creates new block scope with the given condition.
     *
     * @param condition   the condition statement of the block.
     * @param upperScope  the upper scope that wraps this scope.
     * @param content     String array list of the lines of the block.
     * @param globalScope the global scope of this block.
     */
    BlockScope(String[] condition, ArrayList<String> content, Scope upperScope, GlobalScope globalScope) {
        super(content, upperScope, globalScope);
        this.conditions = condition;
    }

    @Override
    public void scopeIsValid() throws SJavaException {
        checkScopeSignature();
        checkScopeContent();
    }

    @Override
    void checkScopeSignature() throws SJavaException {
        for (String condition : this.conditions) {
            condition = condition.trim();
            Matcher booleanMatcher = Regex.BOOLEAN.matcher(condition);
            //if it's not a boolean it should be an initialized boolean variable
            if (!booleanMatcher.matches()) {
                Variable variable = getVariable(condition);
                if (variable == null || !variable.isInitialized())
                    throw new SyntaxException(String.format(VAR_NOT_DEFINED, condition));
                VariableType varVariableType = variable.getVariableType();
                if (varVariableType != VariableType.BOOLEAN && varVariableType != VariableType.DOUBLE && varVariableType != VariableType.INTEGER)
                    throw new SyntaxException(TYPE_NOT_MATCH);
            }
        }
    }

}
