import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws IOException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        Race newRace = new Race( 7, 20);

        newRace.addHorse(new Horse( '\u265E' ,  "CHARLES"  ,0.4) , 1);
        newRace.addHorse(new Horse( '\u2658' ,  "SMOKEY "  ,0.7) , 3);
        newRace.addHorse(new Horse( '\u265B' ,  "BLISS"  ,0.5) , 4);
        newRace.addHorse(new Horse( '\u2655' ,  "STAMPER"  ,0.8) , 5);
        newRace.addHorse(new Horse( '\u265C' ,  "NIARA"  ,0.9) , 7);

//        newRace.addHorse(new Horse( '\u265E' ,  "CHARLES"  ,1) , 1);
//        newRace.addHorse(new Horse( '\u2658' ,  "SMOKEY "  ,1) , 2);
//        newRace.addHorse(new Horse( '\u265B' ,  "BLISS"  ,1) , 3);


        newRace.startRace();
    }

}
