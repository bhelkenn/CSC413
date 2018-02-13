/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.bytecodes.FormalCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugFormalCode extends FormalCode {
    
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        vm.enter(str, n);
    }

    @Override
    public void init(Vector<String> args) {
        super.init(args);
        name = "DebugFormalCode";
    }
}
