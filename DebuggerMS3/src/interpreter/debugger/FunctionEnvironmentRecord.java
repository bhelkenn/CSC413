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
public class FunctionEnvironmentRecord {
    
    private Table symtab;
    private String name;
    private int startLine, endLine, currLine;
    
    FunctionEnvironmentRecord() {
        symtab = new Table();
        name = null;
        startLine = endLine = currLine = -1;
    }
    FunctionEnvironmentRecord(String funcName, int start, int end) {
        setFunctionInfo(funcName, start, end);
        beginScope();
    }
    public String getName() {
        return name;
    }
    
    public void beginScope() {
        symtab.beginScope();
    }
    public void setFunctionInfo(String str, int x, int y) {
        name = str;
        startLine = x;
        endLine = y;
    }
    public void setVarVal(String str, int n) {
        symtab.put(str, n);
    }
    public void doPop(int n) {
        symtab.doPop(n);
    }
    public void setCurrentLineNumber(int n) {
        currLine = n;
    }
    public void displayVars() {
        System.out.print("<");
        String[] keys = symtab.keys().toArray(new String[symtab.keys().size()]);
        if (keys.length > 0) System.out.print(keys[0] + "/" + symtab.get(keys[0]));
        for (int i = 1; i < keys.length; i++) {
            System.out.print("," + keys[i] + "/" + symtab.get(keys[i]));
        }
        System.out.print(">");
    }
    public void dump() {
        System.out.print("(");
        
        //table
        displayVars();
        System.out.print(",");
        
        //start
        if (startLine == -1) System.out.print("-");
        else System.out.print(startLine);
        System.out.print(",");
        
        //end
        if (endLine == -1) System.out.print("-");
        else System.out.print(endLine);
        System.out.print(",");
        
        //name
        if (name == null) System.out.print("-");
        else System.out.print(name);
        System.out.print(",");
        
        //current
        if (currLine == -1) System.out.print("-");
        else System.out.print(currLine);
        
        System.out.print(")");
        System.out.println();
    }
    
    public static void main(String[] args) {
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
        
        fctEnvRecord.dump();
        
        System.out.println("BS:");
        fctEnvRecord.beginScope();
        fctEnvRecord.dump();
        
        System.out.println("Function name start end");
        fctEnvRecord.setFunctionInfo("text", 3, 5);
        fctEnvRecord.dump();
        
        System.out.println("LINE n");
        fctEnvRecord.setCurrentLineNumber(3);
        fctEnvRecord.dump();
        
        System.out.println("Enter var value");
        fctEnvRecord.setVarVal("xyz", 2);
        fctEnvRecord.dump();
        fctEnvRecord.setVarVal("abc", 5);
        fctEnvRecord.dump();
        fctEnvRecord.setVarVal("def", 3);
        fctEnvRecord.dump();
        fctEnvRecord.setVarVal("xyz", 1);
        fctEnvRecord.dump();
        
        System.out.println("Pop 1");
        fctEnvRecord.doPop(1);
        fctEnvRecord.dump();
        System.out.println("Pop 2");
        fctEnvRecord.doPop(2);
        fctEnvRecord.dump();
    }
}