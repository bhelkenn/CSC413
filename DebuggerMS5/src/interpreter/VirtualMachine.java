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
    protected boolean isRunning;
    protected Program program;
    protected boolean dumping;
    protected Stack<CallStack> stackTrace;
    
    public VirtualMachine(Program program) {
        this.program = program;
        dumping = false;
        pc = 0;
        runStack = new RunTimeStack();
        stackTrace = new Stack();
    }
    
    public void executeProgram() {
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
        }
    }
    
    public int popRunStack() {return runStack.pop();}
    public int pushRunStack(int i) {return runStack.push(i);}
    public int peekRunStack() {return runStack.peek();}
    public void newFrame(int offset) {runStack.newFrameAt(offset);}
    
    public void popFrame() {runStack.popFrame();}
    public int store(int offset) {return runStack.store(offset);}
    public int load(int offset) {return runStack.load(offset);}
    public int find(int offset) {return runStack.find(offset);}
    public Integer push(Integer i) {return runStack.push(i);}
    public void pushAddress(int arg, int location) {stackTrace.push(new CallStack(arg, location));}
    public CallStack popAddress() {return stackTrace.pop();}
    public CallStack peekCallStack() {return stackTrace.peek();}
    public boolean isCallStackEmpty() {return stackTrace.isEmpty();}
    public void jumpToAddress(int address) {pc = address;}
    public void toggleDump(boolean state) {dumping = state;}
    public int getPC() {return pc;}
    public void stopRunning() {isRunning = false;}

    public void debugMenu() {throw new UnsupportedOperationException("Not supported yet.");}
    public void processCommand(String line) {throw new UnsupportedOperationException("Not supported yet.");}
    public void popEnvRecord() {throw new UnsupportedOperationException("Not supported yet.");}
    public Vector getBpLines() {throw new UnsupportedOperationException("Not supported yet.");}
    public void setCurr(int n) {throw new UnsupportedOperationException("Not supported yet.");}
    public void loadFunction(String str, int start, int end) {throw new UnsupportedOperationException("Not supported yet.");}
    public void popSymTab(int n) {throw new UnsupportedOperationException("Not supported yet.");}
    public void enter(String str, int n) {throw new UnsupportedOperationException("Not supported yet.");}
    public int getCurrentLineNumber() {throw new UnsupportedOperationException("Not supported yet.");}
    public String getCurrentFunctionName() {throw new UnsupportedOperationException("Not supported yet.");}
    public int getStartLineNumber() {throw new UnsupportedOperationException("Not supported yet.");}
    public int getEndLineNumber() {throw new UnsupportedOperationException("Not supported yet.");}
    public int getCurrFrame() {throw new UnsupportedOperationException("Not supported yet.");}
    public int getStepOutFrame() {throw new UnsupportedOperationException("Not supported yet.");}
    public void triggerStepOut() {throw new UnsupportedOperationException("Not supported yet.");}
    public void triggerStepIn() {throw new UnsupportedOperationException("Not supported yet.");}
    public boolean getTracing() {throw new UnsupportedOperationException("Not supported yet.");}
    public boolean getStepInActive() {throw new UnsupportedOperationException("Not supported yet.");}
}
