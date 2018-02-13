/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.debugger.DebugVirtualMachine;
import interpreter.bytecodes.LineCode;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugLineCode extends LineCode {
    public void execute(DebugVirtualMachine vm) {
        vm.setCurr(n);
    }

    @Override
    public void init(Vector<String> args) {
        name = "DebugLineCode";
        n = Integer.parseInt(args.get(0));
    }
    
    @Override
    public String getLabel() {
        return Integer.toString(n);
    }
}
