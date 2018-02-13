/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.ui;

import interpreter.VirtualMachine;
import interpreter.debugger.BreakpointEntry;
import java.io.*;
import java.util.*;

/**
 *
 * @author Brady
 */
public class DebugUI {
    public static String getInput() {
        System.out.println("Type ? for help");
        System.out.print("> ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    public static void displayCommands() {
        System.out.println("Enter one of the following commands at any time "
                + "(type ? to display this again):");
        System.out.println("Set breakpoint: b [args optional, separated by spaces]");
        System.out.println("Clear breakpoint: cb [args optional, no args means clear all]");
        System.out.println("Display the current function: df");
        System.out.println("Continue execution: c");
        System.out.println("Quit execution: q");
        System.out.println("Display variable(s): dv");
        System.out.println("Step out of the current function: so");
        System.out.println("List breakpoints: lb");
        System.out.println();
    }
    public static void displaySourceFile(String sourceFile, VirtualMachine vm) throws FileNotFoundException {
        boolean bpExists = false; //any breakpoint is set
        boolean bpAboveTenExists = false; //breakpoint 10 or above is set
        Vector<BreakpointEntry> bpLines = vm.getBpLines();
        for (BreakpointEntry be : bpLines) {
            if (be.isSet()) bpExists = true;
            if (be.isSet() && Integer.parseInt(be.getLine()) >= 10) bpAboveTenExists = true;
        }
        try (Scanner scan = new Scanner(new File(sourceFile))) {
            int lineNo = 0;
            while (scan.hasNextLine()) {
                boolean bpSet = false;
                for (BreakpointEntry be : bpLines) {
                    if (be.getLine().equals(Integer.toString(lineNo))) {
                        if (be.isSet()) bpSet = true;
                    }
                }
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
                System.out.print(lineNo + ". " + scan.nextLine());
                if (lineNo > 0) {
                    if (lineNo == vm.getCurrentLineNumber()) System.out.print("   <====Current location");
                }
                System.out.println();
                lineNo++;
            }
        } catch (FileNotFoundException ex) {}
    }
    public static void executingLitCode() {System.out.println("LitCode executed");}
    public static void executingReturnCode() {System.out.println("ReturnCode executed");}
    public static void executingLoadCode() {System.out.println("LoadCode executed");}
    public static void executingStoreCode() {System.out.println("StoreCode executed");}
}
