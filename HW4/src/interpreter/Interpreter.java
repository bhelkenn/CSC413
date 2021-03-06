package interpreter;

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

    ByteCodeLoader bcl;

    public Interpreter(String codeFile) {
        CodeTable.init();
        bcl = new ByteCodeLoader(codeFile);
    }

    void run() throws FileNotFoundException, IOException, 
                        ClassNotFoundException, 
                        InstantiationException, 
                        IllegalAccessException {
        Program program = bcl.loadCodes();
        VirtualMachine vm = new VirtualMachine(program);
        vm.executeProgram();
    }

    public static void main(String args[]) throws ClassNotFoundException, 
                                                    InstantiationException, 
                                                    IllegalAccessException,
                                                    IOException {
        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        }
        (new Interpreter(args[0])).run();
    }
}