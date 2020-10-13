package oop.ex6.scopes;

import oop.ex6.main.Regex;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SJavaException.*;
import oop.ex6.main.SJavaException;

import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * this class represents a scope of a method in s-java file.
 * the class extends InnerScope class.
 */
public class MethodScope extends InnerScope {

    public String[] arguments;
    private static final String RETURN_STATEMENT_ERROR = "Missing return statement";

    /**
     * Constructor - creates new method scope with the corresponding name and arguments types.
     *
     * @param arguments   the arguments the method should get.
     * @param content     array list of the lines of this scope
     * @param upperScope  the upper scope that wraps this scope.
     * @param globalScope the global scope
     */
    MethodScope(String[] arguments, ArrayList<String> content, Scope upperScope, GlobalScope globalScope) {
        super(content, upperScope, globalScope);
        this.arguments = arguments;
    }

    @Override

    public void scopeIsValid() throws SJavaException {
        checkScopeSignature();
        checkScopeContent();
    }

    @Override
    void checkScopeSignature() throws SJavaException {
        ArrayList<String> allNamesInSignature = new ArrayList<>();
        Pattern p = Pattern.compile(RegexPatterns.WHITE_SPACE_OR_NOT);
        if (!p.matcher(arguments[0]).matches()) {
            for (String subArg : arguments) {
                String[] splitted = subArg.split(RegexPatterns.WHITE_SPACE);
                String argType = splitted[0];
                String argName = splitted[1];
                Variable newVar = new Variable(null, argType, argName, null, this, true);
                this.addVariable(argName, newVar);
                if (allNamesInSignature.contains(argName))
                    throw new SyntaxException(String.format(VAR_ALREADY_DEFINED, argName));
                else allNamesInSignature.add(argName);
            }
        }
    }

    @Override
    void checkScopeContent() throws SJavaException {
        String oneBeforeLastLine = content.get(content.size() - 2);
        if (!Regex.RETURN_STATEMENT.matcher(oneBeforeLastLine).matches())
            throw new SyntaxException(RETURN_STATEMENT_ERROR);
        super.checkScopeContent();
    }

    /**
     * returns the number of arguments this method got
     *
     * @param arguments String array of the arguments of this method
     * @return the number of arguments this method got
     */
    int numOfArgument(String[] arguments) {
        if (arguments[0].equals("")) {
            return 0;
        }
        return arguments.length;
    }
}