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
public class Program {
    private Vector<ByteCode> bc;
    private HashMap<String, Integer> addresses;
    private HashMap<Integer, String> lines;
    Program() {
        bc = new Vector();
        addresses = new HashMap();
        lines = new HashMap();
    }
    public ByteCode getCode(int pc) {
        return bc.get(pc);
    }
    public ByteCode createByteCode(Vector<String> args, int pc) 
                                    throws ClassNotFoundException, 
                                            InstantiationException, 
                                            IllegalAccessException {
        Class c = Class.forName("interpreter." + args.get(0));
        bc.add((ByteCode)c.newInstance());
        args.removeElementAt(0);
        bc.get(bc.size() - 1).init(args);
        if (bc.get(bc.size() - 1).getName().equals("LabelCode")) {
            addresses.put(args.get(0), pc);
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
}
