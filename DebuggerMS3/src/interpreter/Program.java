/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import interpreter.bytecodes.ByteCode;
import java.util.*;

/**
 *
 * @author Brady
 */
public class Program {
    private Vector<ByteCode> bc;
    private HashMap<String, Integer> addresses;
    private HashMap<Integer, String> lines;
    private Set<Integer> linesBreakptsCanBeSet;
    private String sourceFile;
    
    Program() {
        bc = new Vector();
        addresses = new HashMap();
        lines = new HashMap();
        linesBreakptsCanBeSet = new HashSet<Integer>();
        sourceFile = null;
    }
    public ByteCode getCode(int pc) {
        return bc.get(pc);
    }
    public ByteCode createByteCode(Vector<String> args, int pc) 
                                    throws ClassNotFoundException, 
                                            InstantiationException, 
                                            IllegalAccessException {
        Class c;
        try {
            c = Class.forName("interpreter.bytecodes." + args.get(0));
        } catch (ClassNotFoundException e) {
            c = Class.forName("interpreter.bytecodes.debuggerByteCodes." + args.get(0));
        }
        
        bc.add((ByteCode)c.newInstance());
        args.removeElementAt(0);
        bc.get(bc.size() - 1).init(args);
        if (bc.get(bc.size() - 1).getName().equals("LabelCode")) {
            addresses.put(args.get(0), pc);
        }
        if (bc.get(bc.size() - 1).getName().equals("DebugLineCode")) {
            linesBreakptsCanBeSet.add(Integer.parseInt(bc.get(bc.size() - 1).getLabel()));
        }
        return bc.get(bc.size() -1);
    }
    
    private String resolveAddress(ByteCode code) {
        if (code.getName().equals("FalseBranchCode") ||
            code.getName().equals("CallCode") ||
            code.getName().equals("GotoCode") || 
            code.getName().equals("ReturnCode")) {
            String label = code.getLabel();
            int address = -1;
            if (addresses.get(label) != null) {
                address = addresses.get(label);
                Vector<String> v = new Vector();
                v.addElement(Integer.toString(address)); 
                code.init(v);
            }
       }
        
        //placeholder
        return "";
    }
    
    public void resolveAddresses() {
        for (int i = 0; i < bc.size(); i++) {
            resolveAddress(bc.get(i));
        }
    }
    
    public void addLine(int pc, String line) {
        lines.put(pc, line);
    }
    
    public HashMap getLines() {
        return lines;
    }
    public void setSourceFileName(String filename) {
        sourceFile = filename;
    }
    public String getSourceFileName() {
        return sourceFile;
    }
    public Set getLinesBreakptsCanBeSet() {
        return linesBreakptsCanBeSet;
    }
}
