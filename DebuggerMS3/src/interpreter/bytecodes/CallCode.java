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
public class CallCode extends ByteCode {
    private String label;
    private int arg;
    @Override
    public void execute(VirtualMachine vm) {
        arg = vm.peekRunStack();
        vm.pushAddress(vm.getPC());
        vm.jumpToAddress(Integer.parseInt(label));
    }

    @Override
    public void init(Vector<String> args) {
        name = "CallCode";
        label = args.get(0);
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String printStatement() {
        return label + "(" + arg + ")";
    }
}
