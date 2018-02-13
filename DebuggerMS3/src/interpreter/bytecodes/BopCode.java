/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class BopCode extends ByteCode {
    private String operator;

    @Override
    public void execute(VirtualMachine vm) {
        int i = vm.popRunStack();
        int j = vm.popRunStack();
        if (operator.equals("==")) {
            if (j == i) vm.pushRunStack(1);
            else vm.pushRunStack(0);
            
        }
        if (operator.equals("<")) {
            if (j < i) vm.pushRunStack(1);
            else vm.pushRunStack(0);
        }
        if (operator.equals("-")) {
            vm.pushRunStack(j - i);
        }
        if (operator.equals("+")) {
            vm.pushRunStack(j + i);
        }
        if (operator.equals("*")) {
            vm.pushRunStack(j * i);
        }
        
    }

    @Override
    public void init(Vector<String> args) {
        //only one argument
        name = "BopCode";
        operator = args.get(0);
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
