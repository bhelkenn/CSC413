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
public class ReturnCode extends ByteCode {
    protected String label;
    protected int address;
    protected boolean debug = true;
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.popFrame();
        address = vm.popAddress().getLocation();
        vm.jumpToAddress(address);
    }

    @Override
    public void init(Vector<String> args) {
        name = "ReturnCode";
        if (args.size() > 0) label = args.get(0);
        else label = "";
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String printStatement() {
        return "";
    }
}
