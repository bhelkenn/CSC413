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
public class DumpCode extends ByteCode {
    private String state;
    
    @Override
    public void execute(VirtualMachine vm) {
        if (state.equals("ON")) vm.toggleDump(true);
        else vm.toggleDump(false);
    }

    @Override
    public void init(Vector<String> args) {
        name = "DumpCode";
        state = args.get(0);
    }

    @Override
    public String getLabel() {
        return state;
    }

    @Override
    public String printStatement() {
        return "";
    }
}
