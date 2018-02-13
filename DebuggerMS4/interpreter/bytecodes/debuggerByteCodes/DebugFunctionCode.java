/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.bytecodes.FunctionCode;
import interpreter.VirtualMachine;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugFunctionCode extends FunctionCode {
    
    @Override
    public void execute(VirtualMachine vm) {
        vm.loadFunction(funcName, start, end);
    }

    @Override
    public void init(Vector<String> args) {
        super.init(args);
        name = "DebugFunctionCode";
    }
}
