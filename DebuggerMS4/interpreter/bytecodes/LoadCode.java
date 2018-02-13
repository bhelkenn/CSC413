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
public class LoadCode extends ByteCode {
    protected int offset;
    protected int value;
    protected String var;
    
    @Override
    public void execute(VirtualMachine vm) {
        value = vm.load(offset);
    }

    @Override
    public void init(Vector<String> args) {
        name = "LoadCode";
        offset = Integer.parseInt(args.get(0));
        try {
            var = args.get(1);
        } catch (ArrayIndexOutOfBoundsException ex) {}
    }

    @Override
    public String getLabel() {
        return var;
    }

    @Override
    public String printStatement() {
        return "<load " + var + ">";
    }
}
