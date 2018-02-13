/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;
import java.util.Scanner;

/**
 *
 * @author Brady
 */
public class ReadCode extends ByteCode {
    @Override
    public void execute(VirtualMachine vm) {
        System.out.print("Enter an integer: ");
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        try {
            vm.pushRunStack(Integer.parseInt(line));
        } catch(java.lang.NumberFormatException e) {
            execute(vm);
        }
    }

    @Override
    public void init(Vector<String> args) {
        name = "ReadCode";
    }

    @Override
    public String getLabel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String printStatement() {
        return "";
    }
}
