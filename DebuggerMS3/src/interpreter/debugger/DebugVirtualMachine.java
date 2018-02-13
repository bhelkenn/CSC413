/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;
import java.util.Stack;
import java.util.Vector;
import interpreter.debugger.ui.CommandMenu;
import java.util.Set;
/**
 *
 * @author Brady
 */
public class DebugVirtualMachine extends VirtualMachine {
    private Vector<BreakpointEntries> entries;
    private Stack<FunctionEnvironmentRecord> environmentStack;
    private CommandMenu menu;
    
    public DebugVirtualMachine(Program program) {
        super(program);
        entries = new Vector();
        environmentStack = new Stack();
        menu = new CommandMenu();
        Set<Integer> set = program.getLinesBreakptsCanBeSet();
        for (int n : set) {
            entries.add((new BreakpointEntries(Integer.toString(n), false)));
        }
    }
    
    public void popSymTab(int n) {
        environmentStack.peek().doPop(n);
    }
    
    public void enter(String str, int n) {
        environmentStack.peek().setVarVal(str, n);
    }

    public void setCurr(int n) {
        environmentStack.peek().setCurrentLineNumber(n);
    }

    public void loadFunction(String funcName, int start, int end) {
        environmentStack.push(new FunctionEnvironmentRecord(funcName, start, end));
    }
    
    @Override
    public void debugMenu() {
        menu.displayCommands();
        processCommand(menu.collectInputRoot());
    }
    
    @Override
    public void processCommand(String input) {
        while (!input.equals("continue")) {
            switch (input) {
                case "?":
                case "help":
                    menu.displayCommands();
                    break;
                case "set":
                    String set = menu.collectInputSet();
                    boolean successful = false;
                    for (BreakpointEntries be : entries) {
                        if (be.getLine().equals(set)) {
                            be.setBreakPoint();
                            System.out.println("Breakpoint set at Line " + be.getLine());
                            System.out.println();
                            successful = true;
                        }
                    }
                    if (!successful) System.out.println("Couldn't set a breakpoint here! Try again later.");
                    break;
                case "clear":
                    String clear = menu.collectInputClear();
                    successful = false;
                    for (BreakpointEntries be : entries) {
                        if (be.getLine().equals(clear)) {
                            be.clearBreakPoint();
                            System.out.println("Breakpoint cleared at Line " + be.getLine());
                            System.out.println();
                            successful = true;
                        }
                    }
                    if (!successful) System.out.println("Couldn't set a breakpoint here! Try again later.");
                    break;
                case "function":
                    System.out.print("Function name: ");
                    if (environmentStack.isEmpty()) System.out.println("N/A");
                    else System.out.println(environmentStack.peek().getName());
                    break;
                case "continue":
                    //nothing needed, breaks out of while loop
                    break;
                case "quit":
                    System.exit(0);
                    break;
                case "var":
                    System.out.print("Variables: ");
                    if (environmentStack.isEmpty()) System.out.print("N/A");
                    else environmentStack.peek().displayVars();
                    System.out.println();
                    break;
                default:
                    System.out.println("Incorrect entry, please re-enter: ");
                    break;
            }
            menu.displayCommands();
            input = menu.collectInputRoot();
        }
    }
}
