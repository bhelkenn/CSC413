/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.bytecodes.FormalCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugFormalCode extends FormalCode {
    
    public void execute(DebugVirtualMachine vm) {
        vm.enter(str, n);
    }

    @Override
    public void init(Vector<String> args) {
        name = "DebugFormalCode";
        str = args.get(0);
        n = Integer.parseInt(args.get(1));
    }
}
