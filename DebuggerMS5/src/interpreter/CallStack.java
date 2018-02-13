/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author Brady
 */
public class CallStack {
    private int callArg;
    private int location;
    CallStack(int call, int pc) {
        callArg = call;
        location = pc;
    }
    public int getCallArg() {return callArg;}
    public int getLocation() {return location;}
}
