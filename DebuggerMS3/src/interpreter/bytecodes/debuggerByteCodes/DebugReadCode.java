/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ReadCode;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugReadCode extends ReadCode {
    @Override
    public void execute(VirtualMachine vm) {
        System.out.print("Enter an integer: ");
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        
        try {
            vm.pushRunStack(Integer.parseInt(line));
        } catch (java.lang.NumberFormatException ex) {
            vm.jumpToAddress(vm.getPC() - 1); //pc--;
            vm.processCommand(line);
        }
        
    }
    
    @Override
    public void init(Vector<String> args) {
        name = "DebugReadCode";
    }
}
