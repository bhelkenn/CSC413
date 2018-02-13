/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.ui;

import java.util.Scanner;

/**
 *
 * @author Brady
 */
public class CommandMenu {
    public void displayCommands() {
        System.out.println("Enter one of the following commands at any time "
                + "(type ? to display this again):");
        System.out.println("Set breakpoint: set");
        System.out.println("Clear breakpoint: clear");
        System.out.println("Display the current function: function");
        System.out.println("Continue execution: continue");
        System.out.println("Quit execution: quit");
        System.out.println("Display variable(s): var");
    }
    public String collectInputRoot() {
        System.out.println("Choose an option ('continue' quits this menu):");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    public String collectInputSet() {
        System.out.print("Enter a line number to set a breakpoint for: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    public String collectInputClear() {
        System.out.print("Enter a line number to clear a breakpoint for: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    public String collectInputDuringExecution() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
