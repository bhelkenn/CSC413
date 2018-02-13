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
public class FunctionCode extends ByteCode {
    protected String funcName;
    protected int start, end;
    
    @Override
    public void execute(VirtualMachine vm) {
        //nothing happens
    }

    @Override
    public void init(Vector<String> args) {
        name = "FunctionCode";
        funcName = args.get(0);
        start = Integer.parseInt(args.get(1));
        end = Integer.parseInt(args.get(2));
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
