/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Vector;

/**
 *
 * @author Brady
 */
public class FalseBranchCode extends ByteCode {
    private String label;

    @Override
    public void execute(VirtualMachine vm) {
        int condition = vm.popRunStack();
        if (condition == 0) {
            int address = Integer.parseInt(label);
            vm.jumpToAddress(address);
        }
    }

    @Override
    public void init(Vector<String> args) {
        //only one arg
        label = args.get(0);
        name = "FalseBranchCode";
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
