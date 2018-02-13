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
public class LitCode extends ByteCode {
    protected int value;
    protected String var;
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.pushRunStack(value);
    }
    
    @Override
    public void init(Vector<String> args) {
        name = "LitCode";
        value = Integer.parseInt(args.get(0));
        if (args.size() > 1) var = args.get(1);
        else var = null;
    }

    @Override
    public String getLabel() {
        return var;
    }

    @Override
    public String printStatement() {
        String statement = "";
        if (var != null) {
            statement = "int " + var;
        }
        return statement;
    }
}