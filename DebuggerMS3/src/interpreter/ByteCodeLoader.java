/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.io.*;
import java.util.*;

/**
 *
 * @author Brady
 */
public class ByteCodeLoader {
    private Program program;
    private String filename;
    ByteCodeLoader(String programFile) {
        filename = programFile;
        program = new Program();
    }
    public Program loadCodes() throws IOException, 
                                ClassNotFoundException, 
                                InstantiationException, 
                                IllegalAccessException {
        Scanner scan = new Scanner(new File(filename));
        int pc = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            Vector<String> args = new Vector();
            
            //first token is always a call code
            args.add(CodeTable.get(st.nextToken()));
            
            //any more will be args
            while (st.hasMoreTokens()) {
                args.add(st.nextToken());
            }
            
            program.createByteCode(args, pc);
            program.addLine(pc, line);
            pc++;
        }
        scan.close();
        program.resolveAddresses();
        return program;
    }
    
        
}
