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
    private boolean stepoutTripped = false;
    private boolean stepinTripped = false;
    private boolean stepinActive = false;
    private int envStackLocation;
    private boolean tracing;
    private ByteCode code;
    
    public DebugVirtualMachine(Program program) {
        super(program);
        bpLines = new Vector();
        envStack = new Stack();
        envStack.push(new FunctionEnvironmentRecord());
        envSize = envStackLocation = 0;
        Set<Integer> set = program.getLinesBreakptsCanBeSet();
        for (int n : set) {
            bpLines.add((new BreakpointEntry(Integer.toString(n), false)));
        }
        sourceFile = program.getSourceFile();
    }
    
    private void executeLoop() {
        code = program.getCode(pc);
        int currLine = getCurrentLineNumber();
        if (code.getName().contains("LineCode")) bpTripped = false;
        for (BreakpointEntry be : bpLines) {
            if (be.isSet() && be.getLine().equals(Integer.toString(currLine)) && !bpTripped) {
                bpTripped = true;
                debugMenu();
            }
        }
        code.execute(this);
        if (stepoutTripped) {
            stepoutTripped = false;
            debugMenu();
        }
        if (stepinTripped) {
            stepinTripped = stepinActive = false;
            debugMenu();
        }
        pc++;
    }
    
    @Override
    public void executeProgram() {
        isRunning = true;
        while (isRunning) {
            executeLoop();
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
    public int getCurrentLineNumber() {return envStack.peek().getCurrentLineNumber();}
    @Override
    public String getCurrentFunctionName() {return envStack.peek().getName();}
    @Override
    public int getStartLineNumber() {return envStack.peek().getStartLineNumber();}
    @Override
    public int getEndLineNumber() {return envStack.peek().getEndLineNumber();}
    @Override
    public int getCurrFrame() {return envSize;}
    @Override
    public int getStepOutFrame() {return envStackLocation;}
    @Override
    public void triggerStepOut() {stepoutTripped = true;}
    @Override
    public void triggerStepIn() {stepinTripped = true;}
    @Override
    public boolean getTracing() {return tracing;}
    @Override
    public boolean getStepInActive() {return stepinActive;}
    
    private void clearAllBPs() {
        for (BreakpointEntry be : bpLines) {
            be.clearBreakPoint();
        }
        System.out.println("All breakpoints cleared");
    }
    private void clearSpecificBPs(String[] split) {
        for (int i = 1; i < split.length; i++) {
            boolean successful = false;
            int currLine = Integer.parseInt(split[i]);
            for (BreakpointEntry be : bpLines) {
                if (be.getLine().equals(Integer.toString(currLine))) {
                    be.clearBreakPoint();
                    System.out.println("Breakpoint cleared: " + split[i]);
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
    
    private Vector<Integer> collectActiveBPs() {
        Vector<Integer> lineNos = new Vector();
        for (BreakpointEntry be : bpLines) {
            int currLine = Integer.parseInt(be.getLine());
            if (be.isSet()) lineNos.add(currLine);
        }
        return lineNos;
    }
    private void printActiveBPs(Vector<Integer> lineNos) {
        if (!lineNos.isEmpty()) {
            System.out.print("Breakpoints: ");
            for (int i = 0; i < lineNos.size(); i++) {
                System.out.print(lineNos.get(i) + " ");
            }
            System.out.println();
        }
        else System.out.println("No breakpoints set");
    }
    
    private boolean setBP(String arg) {
        int currLine = Integer.parseInt(arg);
        for (BreakpointEntry be : bpLines) {
            if (be.getLine().equals(Integer.toString(currLine))) {
                be.setBreakPoint();
                System.out.println("Breakpoint set: " + arg);
                return true;
            }
        }
        return false;
    }
    
    private void helpCmd() {DebugUI.displayCommands();}
    private void setCmd(String input) {
        String split[] = input.split("\\s+");
        for (int i = 1; i < split.length; i++) {
            if (!setBP(split[i])) System.out.println("Unable to set breakpoint: " + split[i]);
        }
    }
    private void clearCmd(String input) {
        String split[] = input.split("\\s+");
        
        if (split.length == 1) clearAllBPs();
        else clearSpecificBPs(split);
    }
    private void functionCmd() {
        System.out.println("Function name: " + getCurrentFunctionName() + " [lines " + 
                            getStartLineNumber() + " - " + getEndLineNumber() + "]");
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
    private void listBpCmd() {printActiveBPs(collectActiveBPs());}
    private String stepOutCmd() {
        envStackLocation = envSize;
        return "c";
    }
    private String stepInCmd() {
        stepinActive = true;
        return "c";
    }
    private void stepOverCmd() {
        while (getCurrentLineNumber() - 1 < getEndLineNumber() && !code.getName().contains("ReturnCode")) {
            executeLoop();
        }
        printCmd();
    }
    private void printCmd() {
        try {
            DebugUI.displaySourceFile(sourceFile, this);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found! [processCommand()]");
        }
    }
    private void traceCmd(String input) {
        String split[] = input.split("\\s+");
        if (split[1].equals("on")) {
            tracing = true;
            System.out.println("Function tracing turned on");
        }
        else if (split[1].equals("off")) {
            tracing = false;
            System.out.println("Function tracing turned off ");
        }
        else System.out.println("incorrect entry");
    }
    private void printCallStackCmd() {DebugUI.printCallStack(stackTrace);}
    
    @Override
    public void processCommand(String input) {
        while (!input.equals("c")) {
            if (input.equals("?")) helpCmd();
            else if (!input.equals("b") && input.startsWith("b")) setCmd(input);
            else if (input.startsWith("cb")) clearCmd(input);
            else if (input.equals("df")) functionCmd();
            else if (input.equals("c")) /* nothing happens */;
            else if (input.equals("q")) quitCmd();
            else if (input.startsWith("dv")) varCmd(input);
            else if (input.equals("so")) input = stepOutCmd();
            else if (input.equals("si")) input = stepInCmd();
            else if (input.equals("sx")) stepOverCmd();
            else if (input.equals("lb")) listBpCmd();
            else if (input.equals("p")) printCmd();
            else if (input.equals("pc")) printCallStackCmd();
            else if (!input.equals("tr") && input.startsWith("tr")) traceCmd(input);
            else System.out.println("Incorrect entry, please re-enter: ");
            
            if (!input.equals("c")) input = DebugUI.getInput();
        }
    }
}
