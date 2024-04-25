import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        Race newRace = new Race(20);

        newRace.addHorse(new Horse( '\u265E' ,  "CHARLES"  ,0.4) , 1);
        newRace.addHorse(new Horse( '\u2658' ,  "SMOKEY "  ,0.7) , 2);
        newRace.addHorse(new Horse( '\u265B' ,  "BLISS"  ,0.5) , 3);

        newRace.startRace();
    }

}
