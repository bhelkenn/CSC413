/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import java.util.*;
import interpreter.debugger.ui.DebugUI;
import java.io.FileNotFoundException;
/**
 *
 * @author Brady
 */
public class DebugVirtualMachine extends VirtualMachine {
    private Vector<BreakpointEntry> bpLines;
    private Stack<FunctionEnvironmentRecord> envStack;
    private int envSize;
    private String sourceFile;
    private boolean bpTripped = false;
    
    public DebugVirtualMachine(Program program) {
        super(program);
        bpLines = new Vector();
        envStack = new Stack();
        envStack.push(new FunctionEnvironmentRecord());
        envSize = 0;
        Set<Integer> set = program.getLinesBreakptsCanBeSet();
        for (int n : set) {
            bpLines.add((new BreakpointEntry(Integer.toString(n), false)));
        }
        sourceFile = program.getSourceFile();
    }
    
    @Override
    public void executeProgram() {
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            int currLine = getCurrentLineNumber();
            if (code.getName().contains("LineCode")) bpTripped = false;
            for (BreakpointEntry be : bpLines) {
                if (be.isSet() && be.getLine().equals(Integer.toString(currLine)) && !bpTripped) {
                    bpTripped = true;
                    debugMenu();
                }
            }
            code.execute(this);
            if (dumping) dump(code);
            pc++;
        }
    }
    
    @Override
    public void popSymTab(int n) {envStack.peek().doPop(n);}
    @Override
    public void enter(String str, int n) {envStack.peek().setVarVal(str, n);}
    @Override
    public void setCurr(int n) {envStack.peek().setCurrentLineNumber(n);}
    @Override
    public void loadFunction(String funcName, int start, int end) {
        int currLine = getCurrentLineNumber();
        Table table = envStack.peek().getTable();
        envStack.push(new FunctionEnvironmentRecord(funcName, start, end));
        envSize++;
        setCurr(currLine);
        if (envSize == 0) envStack.peek().setTable(table);
    }
    @Override
    public void popEnvRecord() {
        if (!envStack.isEmpty()) {
            FunctionEnvironmentRecord record = envStack.pop();
            envSize--;
            if (!envStack.isEmpty()) {
                envStack.peek().setTable(record.getTable());
                envStack.peek().setCurrentLineNumber(record.getCurrentLineNumber());
            }
        }
    }
    @Override
    public void debugMenu() {
        try {
            DebugUI.displaySourceFile(sourceFile, this);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found! [debugMenu()]");
        }
        processCommand(DebugUI.getInput());
    }
    @Override
    public Vector<BreakpointEntry> getBpLines() {return bpLines;}
    @Override
    public int getCurrentLineNumber() {
        if (!envStack.isEmpty()) return envStack.peek().getCurrentLineNumber();
        else return -1;
    }

    private void clearAllBPs() {
        for (BreakpointEntry be : bpLines) {
            be.clearBreakPoint();
        }
        System.out.println("All breakpoints cleared");
    }
    private void clearSpecificBPs(String[] split) {
        for (int i = 1; i < split.length; i++) {
            boolean successful = false;
            for (BreakpointEntry be : bpLines) {
                if (be.getLine().equals(split[i])) {
                    be.clearBreakPoint();
                    System.out.println("Breakpoint cleared: " + split[i]);
                    System.out.println();
                    successful = true;
                }
            }
            if (!successful) System.out.println("Unable to clear breakpoint: " + split[i]);
        }
    }
    
    private void listAllVars() {
        String[] vars = envStack.peek().getVars();
        for (int i = 0; i < vars.length; i++) {
            System.out.print(vars[i] + " = " + envStack.peek().getVar(vars[i]) + "; ");
        }
        System.out.println();
    }
    private void listSpecificVars(String[] split) {
        for (int i = 1; i < split.length; i++) {
            if (envStack.peek().containsVar(split[i])) {
                int value = envStack.peek().getVar(split[i]);
                System.out.println(split[i] + " = " + value);
            }
            else System.out.println(split[i] + " is not a valid variable");
        }
    }

    private void stepUntilEndOfFunction(int currLine) {
        while (currLine < envStack.peek().getEndLineNumber() - 1) {
            ByteCode code = program.getCode(pc);
            if (code.getName().contains("LineCode")) bpTripped = false;
            for (BreakpointEntry be : bpLines) {
                if (be.isSet() && be.getLine().equals(Integer.toString(currLine)) && !bpTripped) {
                    bpTripped = true;
                    debugMenu();
                }
            }
            code.execute(this);
            currLine = getCurrentLineNumber();
            pc++;
        }
    }
    private void stepPastEndOfFunction(int currLine) {
        while (currLine == envStack.peek().getEndLineNumber() - 1) {
            stepCmd();
            currLine = getCurrentLineNumber();
        }
    }
    
    private Vector<String> collectActiveBPs() {
        Vector<String> lineNos = new Vector();
        for (BreakpointEntry be : bpLines) {
            if (be.isSet()) lineNos.add(be.getLine());
        }
        return lineNos;
    }
    private void printActiveBPs(Vector<String> lineNos) {
        if (!lineNos.isEmpty()) {
            System.out.print("Breakpoints: ");
            for (int i = 0; i < lineNos.size(); i++) {
                System.out.print(lineNos.get(i) + " ");
            }
            System.out.println();
        }
        else System.out.println("No breakpoints set");
    }
    
    private void helpCmd() {DebugUI.displayCommands();}
    private void setCmd(String input) {
        String split[] = input.split("\\s+");
        
        for (int i = 1; i < split.length; i++) {
            boolean successful = false;
            for (BreakpointEntry be : bpLines) {
                if (be.getLine().equals(split[i])) {
                    be.setBreakPoint();
                    System.out.println("Breakpoint set: " + split[i]);
                    System.out.println();
                    successful = true;
                }
            }
            if (!successful) System.out.println("Unable to set breakpoint: " + split[i]);
        }
    }
    private void clearCmd(String input) {
        String split[] = input.split("\\s+");
        
        if (split.length == 1) clearAllBPs();
        else clearSpecificBPs(split);
    }
    private void functionCmd() {
        System.out.print("Function name: ");
        System.out.println(envStack.peek().getName());
        System.out.println("envSize: " + envSize);
    }
    private void quitCmd() {
        System.out.println("Exiting program by user request");
        System.exit(0);
    }
    private void varCmd(String input) {
        String[] vars = envStack.peek().getVars();
        if (vars.length == 0) {
            System.out.println("No variables defined");
            return;
        }
        String split[] = input.split("\\s+");
        
        if (split.length ==  1) listAllVars();
        else listSpecificVars(split);
    }
    private void stepoutCmd() {
        stepPastEndOfFunction(getCurrentLineNumber());
        stepUntilEndOfFunction(getCurrentLineNumber());
    }
    private void listBpCmd() {printActiveBPs(collectActiveBPs());}
    private void stepCmd() {
        int currLine = getCurrentLineNumber();
        while (currLine == getCurrentLineNumber()) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
        }
        functionCmd();
        varCmd("dv");
    }
    @Override
    public void processCommand(String input) {
        while (!input.equals("c")) {
            if (input.equals("?")) helpCmd();
            else if (!input.equals("b") && input.startsWith("b")) setCmd(input);
            else if (input.startsWith("cb")) clearCmd(input);
            else if (input.equals("df")) functionCmd();
            else if (input.equals("c")) stepCmd();
            else if (input.equals("q")) quitCmd();
            else if (input.startsWith("dv")) varCmd(input);
            else if (input.equals("so")) stepoutCmd();
            else if (input.equals("lb")) listBpCmd();
            else if (input.equals("s")) stepCmd();
            else System.out.println("Incorrect entry, please re-enter: ");
            try {
                DebugUI.displaySourceFile(sourceFile, this);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found! [processCommand()]");
            }
            input = DebugUI.getInput();
        }
    }
}
