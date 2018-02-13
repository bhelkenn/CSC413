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
public abstract class ByteCode {
    protected String name;
    abstract public void execute(VirtualMachine vm);
    abstract public void init(Vector<String> args);
    abstract public String getLabel();
    abstract public String printStatement();
    public String getName() {return name;}
}

