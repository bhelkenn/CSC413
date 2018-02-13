/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.util.Vector;
/**
 *
 * @author Brady
 */
public class ArgsCode extends ByteCode {
    private int offset;
    @Override
    public void execute(VirtualMachine vm) {
        vm.newFrame(offset);
    }

    @Override
    public void init(Vector<String> args) {
        offset = Integer.parseInt(args.get(0));
        name = "ArgsCode";
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
