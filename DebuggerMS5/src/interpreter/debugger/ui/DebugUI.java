/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.ui;

import interpreter.CallStack;
import interpreter.VirtualMachine;
import interpreter.debugger.BreakpointEntry;
import java.io.*;
import java.util.*;

/**
 *
 * @author Brady
 */
public class DebugUI {
    public static String collectInput() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    public static String getInput() {
        System.out.println("Type ? for help");
        System.out.print("> ");
        return collectInput();
    }
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (java.lang.NumberFormatException ex) {
            return false;
        }
    }
    public static void displayCommands() {
        System.out.println("Enter one of the following commands at any time "
                + "(type ? to display this again):");
        System.out.println(" b - Set breakpoint(args required)");
        System.out.println("cb - Clear breakpoint (args optional, no args means clear all)");
        System.out.println("df - Display the current function");
        System.out.println(" c - Continue execution");
        System.out.println(" q - Quit execution");
        System.out.println("dv - Display variable(s) (args optional)");
        System.out.println("so - Step out of the current function");
        System.out.println("si - Step in");
        System.out.println("sx - Step over");
        System.out.println("lb - List breakpoints");
        System.out.println(" p - Print source file");
        System.out.println("pc - Print call stack");
        System.out.println("tr - turn trace on or off (args are on/off)");
        System.out.println();
    }
    private static void displayBPLogic(boolean bpExists, boolean bpAboveTenExists, 
                                        boolean bpSet, int lineNo) {
        if (bpExists) System.out.print(" ");
        if (!bpAboveTenExists) {
            if (lineNo < 10 && !bpSet) System.out.print(" ");
            if (bpSet) System.out.print("*");
        }
        else {
            if (lineNo < 10) System.out.print(" ");
            if (!bpSet) System.out.print(" ");
            else System.out.print("*");
        }
    }
    private static boolean isBPSet(Vector<BreakpointEntry> bpLines, int lineNo) {
        for (BreakpointEntry be : bpLines) {
            if (be.getLine().equals(Integer.toString(lineNo)) && be.isSet()) return true;
        }
        return false;
    }
    private static boolean inFunction(VirtualMachine vm) {
        return vm.getCurrentFunctionName() != null;
    }
    private static int scanToStartOfFunction(Scanner scan, int lineNo, int startLine) {
        while (lineNo < startLine) {
            scan.nextLine();
            lineNo++;
        }
        return lineNo;
    }
    private static void scanToEndOfFile(Scanner scan) {
        while (scan.hasNextLine()) {
            scan.nextLine();
        }
    }
    private static void printLine(VirtualMachine vm, int lineNo, Scanner scan) {
        Vector<BreakpointEntry> bpLines = vm.getBpLines();
        displayBPLogic(bpExists(bpLines), bpAboveTenExists(bpLines), isBPSet(bpLines, lineNo), lineNo);
        System.out.print(lineNo + ". " + scan.nextLine());
        if (lineNo > 1 && lineNo == vm.getCurrentLineNumber()) {
            System.out.print("   <===");
        }
        System.out.println();
    }
    private static boolean bpExists(Vector<BreakpointEntry> bpLines) {
        for (BreakpointEntry be : bpLines) {
            if (be.isSet()) return true;
        }
        return false;
    }
    private static boolean bpAboveTenExists(Vector<BreakpointEntry> bpLines) {
        for (BreakpointEntry be : bpLines) {
            if (be.isSet() && Integer.parseInt(be.getLine()) >= 10) return true;
        }
        return false;
    }
    public static void displaySourceFile(String sourceFile, VirtualMachine vm) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(sourceFile))) {
            int lineNo = 1;
            if (inFunction(vm)) lineNo = scanToStartOfFunction(scan, lineNo, vm.getStartLineNumber());
            while (scan.hasNextLine()) {
                if (inFunction(vm)) {
                    if (lineNo - 1 < vm.getEndLineNumber()) printLine(vm, lineNo, scan);
                    else scanToEndOfFile(scan);
                }
                else printLine(vm, lineNo, scan);
                lineNo++;
            }
            scan.close();
        } catch (FileNotFoundException ex) {}
    }
    public static void printCallStack(Stack<CallStack> stackTrace) {
        Stack<CallStack> temp = new Stack();
        while (!stackTrace.isEmpty()) {
            temp.push(stackTrace.pop());
        }
        int count = 0;
        while (!temp.isEmpty()) {
            stackTrace.push(temp.pop());
            for (int i = 0; i < count; i++) {
                System.out.print("  ");
            }
            System.out.println("fib(" + stackTrace.peek().getCallArg() + ")");
            count++;
        }
    }
}
