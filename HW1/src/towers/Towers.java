package towers;
import java.util.Scanner;

/**
 *
 * @author Brady
 */
public class Towers {
    public static void displayMove(Peg from, Peg to) {
        System.out.print(from.getTop() + " from " + from.getName() + " to " + to.getName());
        System.out.print("\t\t");
    }
    public static void displayPegs(Peg[] pegs, int gameSize) {
        for (int i = 0; i < gameSize; i++) {
            pegs[i].listDiscs();
        }
        System.out.println();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //input gameSize
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the number of discs and pegs: ");
        int gameSize = reader.nextInt();
        
        System.out.println("Move\t\t\tPeg Configuration");
        System.out.println("    \t\t\tA\t\tB\t\tC");
        
        Peg[] pegs = new Peg[gameSize];
        for (int i = 0; i < gameSize; i++) {
            pegs[i] = new Peg(i, gameSize);
        }
        
        System.out.print("init\t\t\t");
        displayPegs(pegs, gameSize);
        
        //source to gameSize - 1, gameSize - 2... until reach source
        for (int i = gameSize - 1; i > 0; i--) {
            displayMove(pegs[0], pegs[i]);
            pegs[0].moveDisc(pegs[i]);
            displayPegs(pegs, gameSize);
        }
        //stack on f.u.
        //f.u. + 1, f.u. + 2... destination
        for (int i = 2; i < gameSize; i++) {
            displayMove(pegs[i], pegs[1]);
            pegs[i].moveDisc(pegs[1]);
            displayPegs(pegs, gameSize);
        }
        
        //source to destination
        displayMove(pegs[0], pegs[gameSize - 1]);
        pegs[0].moveDisc(pegs[gameSize - 1]);
        displayPegs(pegs, gameSize);
        
        //f.u. to source
        displayMove(pegs[1], pegs[0]);
        pegs[1].moveDisc(pegs[0]);
        displayPegs(pegs, gameSize);
        
        //from f.u.: gameSize - 2, gameSize - 3....until f.u.
        for (int i = gameSize - 2; i > 1; i--) {
            if (i != 1) {
                displayMove(pegs[1], pegs[i]);
                pegs[1].moveDisc(pegs[i]);
                displayPegs(pegs, gameSize);
            }
        }
        
        //to destination: f.u., f.u. + 1...until destination
        for (int i = 1; i < gameSize - 1; i++) {
            displayMove(pegs[i], pegs[gameSize - 1]);
            pegs[i].moveDisc(pegs[gameSize - 1]);
            displayPegs(pegs, gameSize);
        }
        
        //source to destination
        displayMove(pegs[0], pegs[gameSize - 1]);
        pegs[0].moveDisc(pegs[gameSize - 1]);
        displayPegs(pegs, gameSize);
    }
}