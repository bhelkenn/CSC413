/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.bytecodes.FunctionCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugFunctionCode extends FunctionCode {
    public void execute(DebugVirtualMachine vm) {
        vm.loadFunction(funcName, start, end);
    }

    @Override
    public void init(Vector<String> args) {
        name = "DebugFunctionCode";
        funcName = args.get(0);
        start = Integer.parseInt(args.get(1));
        end = Integer.parseInt(args.get(2));
    }
}
