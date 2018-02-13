/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.LoadCode;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugLoadCode extends LoadCode {
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        if (var != null) vm.enter(var, value);
    }
    
    @Override
    public void init(Vector<String> args) {
        super.init(args);
        name = "DebugLoadCode";
    }
}
