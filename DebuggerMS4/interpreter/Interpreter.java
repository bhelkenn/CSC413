package interpreter;

import interpreter.debugger.DebugVirtualMachine;
import java.io.*;

/**
 * <pre>
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 * </pre>
 */
public class Interpreter {

    private ByteCodeLoader bcl;
    private static boolean debugging = false;
    private Program program;
    private String sourceFile;

    public Interpreter(String codeFile) {
        CodeTable.init();
        if (debugging) {
            CodeTable.debugInit();
            sourceFile = codeFile + ".x";
            bcl = new ByteCodeLoader(codeFile + ".x.cod");
        }
        else {
            bcl = new ByteCodeLoader(codeFile);
        }
    }

    void run() throws FileNotFoundException, IOException, 
                        ClassNotFoundException, 
                        InstantiationException, 
                        IllegalAccessException {
        program = bcl.loadCodes();
        program.setSourceFile(sourceFile);
        VirtualMachine vm;
        if (debugging) {
            System.out.println("***Debugging " + sourceFile + "***");
            vm = new DebugVirtualMachine(program);
            vm.debugMenu();
            vm.executeProgram();
        }
        else {
            vm = new VirtualMachine(program);
            vm.executeProgram();
        }
    }

    public static void main(String args[]) throws ClassNotFoundException, 
                                                    InstantiationException, 
                                                    IllegalAccessException,
                                                    IOException {
        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        }
        else if (args[0].equals("-d")) {
            debugging = true;
            (new Interpreter(args[1])).run();
        }
        else {
            (new Interpreter(args[0])).run();
        }
    }
}