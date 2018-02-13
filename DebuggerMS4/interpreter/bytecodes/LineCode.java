/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes;

import java.util.Vector;
import interpreter.VirtualMachine;

/**
 *
 * @author Brady
 */
public class LineCode extends ByteCode {
    protected int n;
    
    @Override
    public void execute(VirtualMachine vm) {
        //nothing happens
    }

    @Override
    public void init(Vector<String> args) {
        name = "LineCode";
        n = Integer.parseInt(args.get(0));
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
