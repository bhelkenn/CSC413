package towers;
import java.util.Stack;

/**
 *
 * @author Brady
 */
public class Peg {
    private Stack<Integer> discs;
    private char name;
    
    //0 = source
    Peg(int position, int gameSize) {
        this.discs = new Stack();
        switch (position) {
            case 0:
                name = 'A';
                for (int i = gameSize; i > 0; i--) {
                    addDisc(i);
                }
                break;
            case 1:
                name = 'B';
                break;
            case 2:
                name = 'C';
                break;
            case 3:
                name = 'D';
                break;
            case 4:
                name = 'E';
                break;
            default:
                name = 'X';
                break;
        }
    }
    
    public char getName() {
        return name;
    }
    public int getTop() {
        return discs.peek();
    }
    
    private void addDisc(int disc) {
        if (discs.isEmpty()) {
            discs.push(disc);
        }
        else if (disc < discs.peek()) {
            discs.push(disc);
        }
    }
    
    public void moveDisc(Peg destination) {
        if (!discs.isEmpty()) {
            if (destination.discs.isEmpty()) {
                int disc = discs.pop();
                destination.discs.push(disc);
            }
            else if (discs.peek() < destination.discs.peek()) {
                int disc = discs.pop();
                destination.discs.push(disc);
            }
        }
    }
    
    public void listDiscs() {
        Stack<Integer> tmp = new Stack();
        while (!discs.isEmpty()) {
            tmp.push(discs.pop());
        }
        while (!tmp.isEmpty()) {
            System.out.print(discs.push(tmp.pop())+ " ");
        }
        System.out.print("\t\t");
    }
}
