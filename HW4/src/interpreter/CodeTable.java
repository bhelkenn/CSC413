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
public class CodeTable {
    private static HashMap<String, String> table;
    public static void init() {
        table = new HashMap();
        table.put("LIT", "LitCode");
        table.put("LABEL", "LabelCode");
        table.put("BOP", "BopCode");
        table.put("FALSEBRANCH", "FalseBranchCode");
        table.put("ARGS", "ArgsCode");
        table.put("DUMP", "DumpCode");
        table.put("CALL", "CallCode");
        table.put("STORE", "StoreCode");
        table.put("GOTO", "GotoCode");
        table.put("HALT", "HaltCode");
        table.put("LOAD", "LoadCode");
        table.put("RETURN", "ReturnCode");
        table.put("READ", "ReadCode");
        table.put("WRITE", "WriteCode");
    }
    public static String get(String code) {
        if (table.containsKey(code)) {
            return table.get(code);
        }
        else {
            System.out.println("Code not found in CodeTable!");
            return null;
        }
    }
}
