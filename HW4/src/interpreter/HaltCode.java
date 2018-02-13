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
public class HaltCode extends ByteCode {
    @Override
    public void execute(VirtualMachine vm) {
        System.out.println("System halted!");
        System.exit(0);
    }

    @Override
    public void init(Vector<String> args) {
        name = "HaltCode";
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
