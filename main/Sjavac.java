package oop.ex6.main;

import java.io.File;
import java.io.IOException;

/**
 * The manager class of the program
 */
public class Sjavac {

    private static final int NUMBER_OF_ARGS = 1;
    private static final String LEGAL_CODE = "0";
    private static final String ILLEGAL_CODE = "1";
    private static final String IO_ERRORS = "2";
    private static final String FILE_NOT_EXIST = "The system cannot find the file specified";
    private static final String ILLEGAL_ARGUMENTS_MESSAGE = "Illegal number of arguments";


    /**
     * The main method of the program.
     *
     * @param args Strings of the arguments sent to the program.
     */
    public static void main(String[] args) {
        try {
            sJavaTester(args);
            System.out.println(LEGAL_CODE);
        } catch (SJavaException exception) {
            System.out.println(ILLEGAL_CODE);
            System.err.println(exception.getMessage());
        } catch (IOException exception) {
            System.out.println(IO_ERRORS);
            System.err.println(exception.getMessage());
        }
    }

    /**
     * This function checks if the given arguments is valid (should get s-java file path).
     * If so it send the it to the main parser for checking the file.
     *
     * @param args the arguments given to the program
     * @throws IOException    if the given arguments are not valid.
     * @throws SJavaException if the s-java is not valid.
     */
    private static void sJavaTester(String[] args) throws IOException, SJavaException {

        if (args.length != NUMBER_OF_ARGS) throw new IOException(ILLEGAL_ARGUMENTS_MESSAGE);
        String fileName = args[0];
        File sJavaFile = new File(fileName);
        if (!sJavaFile.exists()) throw new IOException(FILE_NOT_EXIST);
        Parser.parseFile(sJavaFile);
    }

}
