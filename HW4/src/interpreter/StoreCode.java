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
public class StoreCode extends ByteCode {
    private int offset;
    private int value;
    private String var;
    @Override
    public void execute(VirtualMachine vm) {
        value = vm.peekRunStack();
        vm.store(offset);
    }

    @Override
    public void init(Vector<String> args) {
        name = "StoreCode";
        offset = Integer.parseInt(args.get(0));
        if (args.size() > 1) var = args.get(1);
        else var = null;
    }

    @Override
    public String getLabel() {
        return var;
    }

    @Override
    public String printStatement() {
        return var + " = " + value;
    }
}
