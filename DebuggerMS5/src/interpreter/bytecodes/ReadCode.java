/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes;

import interpreter.VirtualMachine;
import interpreter.debugger.ui.DebugUI;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class ReadCode extends ByteCode {
    protected String entry;
    @Override
    public void execute(VirtualMachine vm) {
        do {
            System.out.print("Enter an integer: ");
            entry = DebugUI.collectInput();
        } while (!DebugUI.isInt(entry));
        vm.pushRunStack(Integer.parseInt(entry));
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
