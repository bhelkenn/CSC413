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
    private int offset;
    private String var;
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.load(offset);
    }

    @Override
    public void init(Vector<String> args) {
        name = "LoadCode";
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
        return "<load " + var + ">";
    }
}
