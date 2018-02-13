/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import interpreter.bytecodes.ByteCode;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author Brady
 */
public class VirtualMachine {
    protected RunTimeStack runStack;
    protected int pc;
    protected Stack<Integer> returnAddress;
    protected boolean isRunning;
    protected Program program;
    protected boolean dumping;
    
    public VirtualMachine(Program program) {
        this.program = program;
        dumping = false;
        pc = 0;
        runStack = new RunTimeStack();
        returnAddress = new Stack();
    }
    
    public void executeProgram() {
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            if (dumping) dump(code);
            pc++;
        }
    }
    
    public int popRunStack() {
        return runStack.pop();
    }
    public int pushRunStack(int i) {
        return runStack.push(i);
    }
    public int peekRunStack() {
        return runStack.peek();
    }
    public void newFrame(int offset) {
        runStack.newFrameAt(offset);
    }
    
    public void popFrame() {
        runStack.popFrame();
    }
    public int store(int offset) {
        return runStack.store(offset);
    }
    public int load(int offset) {
        return runStack.load(offset);
    }
    public Integer push(Integer i) {
        return runStack.push(i);
    }
    public void dump(ByteCode code) {
        System.out.print(program.getLines().get(pc) + "\t");
        System.out.print(code.printStatement());
        System.out.println();
        runStack.dump();
    }
    public void pushAddress(int address) {
        returnAddress.push(address);
    }
    public int popAddress() {
        return returnAddress.pop();
    }
    public void jumpToAddress(int address) {
        pc = address;
    }
    public void toggleDump(boolean state) {
        dumping = state;
    }
    public int getPC() {
        return pc;
    }
    public void stopRunning() {
        isRunning = false;
    }

    public void debugMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void processCommand(String line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void popEnvRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Vector getBpLines() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setCurr(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void loadFunction(String str, int start, int end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void popSymTab(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void enter(String str, int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public int getCurrentLineNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
