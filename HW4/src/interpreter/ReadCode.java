/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Vector;
import java.io.*;

/**
 *
 * @author Brady
 */
public class ReadCode extends ByteCode {
    @Override
    public void execute(VirtualMachine vm) {
        try {
            System.out.print("Enter an integer: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = in.readLine();
            vm.pushRunStack(Integer.parseInt(line));
        } catch(java.io.IOException ex) {}
    }

    @Override
    public void init(Vector<String> args) {
        name = "ReadCode";
    }

    @Override
    public String getLabel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String printStatement() {
        return "";
    }
}
