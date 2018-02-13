/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;
import interpreter.debugger.DebugVirtualMachine;
import interpreter.bytecodes.LitCode;
import java.util.Vector;
/**
 *
 * @author Brady
 */
public class DebugLitCode extends LitCode {
    public void execute(DebugVirtualMachine vm) {
        vm.pushRunStack(value);
        try {
            vm.enter(var, value);
        } catch (UnsupportedOperationException e) {}
    }
    
    @Override
    public void init(Vector<String> args) {
        name = "DebugLitCode";
        value = Integer.parseInt(args.get(0));
        if (args.size() > 1) var = args.get(1);
        else var = null;
    }
}
