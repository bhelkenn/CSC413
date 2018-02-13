/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ArgsCode;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugArgsCode extends ArgsCode {
    @Override
    public void execute(VirtualMachine vm) {
        //vm.setCallArg(vm.find(offset));
        super.execute(vm);
    }
    
    @Override
    public void init(Vector<String> args) {
        super.init(args);
        name = "DebugArgsCode";
    }
}
