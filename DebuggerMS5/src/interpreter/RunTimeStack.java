/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.util.*;

/**
 *
 * @author Brady
 */
public class RunTimeStack {
    private Vector<Integer> v;
    private Stack<Vector> frames;
    int currFrame;
    boolean debug = false;
    
    public RunTimeStack() {
        v = new Vector();
        frames = new Stack();
        currFrame = 0;
    }
    public int peek() {
        if (!v.isEmpty()) return v.lastElement();
        else return -1;
    }
    public int pop() {
        if (v.size() > 0) return v.remove(v.size() - 1);
        else return -1;
    }
    public int push(int n) {
        v.add(n);
        return n;
    }
    
    private void printVector(Vector<Integer> v) {
        for (int i = 0; i < v.size(); i++) {
            System.out.print(v.get(i));
        }
        System.out.println();
    }
    
    public void newFrameAt(int offset) {
        if (debug) {
            System.out.println("\nnewFrameAt()");
            System.out.print("v: ");
            printVector(v); 
        }
        Vector<Integer> temp = new Vector();
        for (int i = 0; i < offset; i++) {
            temp.add(v.remove(v.size() - offset));
        }
        frames.push((Vector<Integer>)v.clone());
        v.clear();
        if (debug) {
            System.out.print("pushed vector: ");
            printVector(frames.peek());
        }
        for (int i = 0; i < temp.size(); i++) {
            v.add(temp.get(i));
        }
        if (debug) {
            System.out.print("v: ");
            printVector(v);
        }
        
        currFrame++;
    }
    public void popFrame() {
        if (!v.isEmpty()) {
            if (debug) {
                System.out.println("\npopFrame()");
                System.out.print("v: ");
                printVector(v);
            }
            int n = v.lastElement();
            Vector<Integer> temp = (Vector<Integer>)frames.peek().clone();
            frames.pop();
            v.clear();
            for (int i = 0; i < temp.size(); i++) {
                v.add(temp.get(i));
            }
            push(n);
            if (debug) {
                System.out.print("v: ");
                printVector(v);
            }
        }
        else v = frames.pop();
        currFrame--;
    }
    public int store(int offset) {
        v.set(offset, pop());
        return v.get(offset);
    }
    public int load(int offset) {return push(v.get(offset));}
    public Integer push(Integer n) {
        v.add(n);
        return n;
    }
    public int find(int offset) {return v.get(offset);}
}
