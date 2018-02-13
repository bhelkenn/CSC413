/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.bytecodes.ReadCode;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class DebugReadCode extends ReadCode {
    @Override
    public void init(Vector<String> args) {
        name = "DebugReadCode";
    }
}
