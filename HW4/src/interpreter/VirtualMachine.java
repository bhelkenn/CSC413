/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.util.Stack;

/**
 *
 * @author Brady
 */
public class VirtualMachine {
    private RunTimeStack runStack;
    private int pc;
    private Stack<Integer> returnAddress;
    private boolean isRunning;
    private Program program;
    private boolean dumping;
    
    VirtualMachine(Program program) {
        this.program = program;
    }
    
    public void executeProgram() {
        pc = 0;
        runStack = new RunTimeStack();
        returnAddress = new Stack();
        isRunning = true;
        dumping = false;
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
}
