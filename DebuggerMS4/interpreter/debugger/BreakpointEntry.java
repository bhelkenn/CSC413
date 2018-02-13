/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;

/**
 *
 * @author Brady
 */
public class BreakpointEntry {
    private String sourceLine;
    private boolean isBreakptSet;
    
    BreakpointEntry() {
        sourceLine = null;
        isBreakptSet = false;
    }
    BreakpointEntry(String line, boolean breakpt) {
        sourceLine = line;
        isBreakptSet = breakpt;
    }
    
    public void setBreakPoint() {
        isBreakptSet = true;
    }
    
    public void clearBreakPoint() {
        isBreakptSet = false;
    }
    
    public String getLine() {
        return sourceLine;
    }
    
    public boolean isSet() {
        return isBreakptSet;
    }
}
