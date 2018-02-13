/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.LineCode;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugLineCode extends LineCode {
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.setCurr(n);
    }

    @Override
    public void init(Vector<String> args) {
        super.init(args);
        name = "DebugLineCode";
    }
    
    @Override
    public String getLabel() {
        return Integer.toString(n);
    }
}
