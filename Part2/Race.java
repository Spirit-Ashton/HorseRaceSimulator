import javax.print.DocFlavor;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.lang.Math;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * A multi-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @Editor Spirit-Ashton
 * @version 2.0
 */
public class Race extends Thread
{
    private int raceLength;
    private int lanes;
    private HashMap<Integer, Horse> horseMap;
    private Boolean allFallen;
    private Horse wonBy;


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race( int noLanes, int distance)
    {
        // initialise instance variables
        raceLength = distance;
        lanes = noLanes;
        horseMap = new HashMap<>();
        allFallen = false;
        wonBy = null;
    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if(laneNumber <= 0 || laneNumber > lanes){
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        } else {
            if (horseMap.get(laneNumber) == null) {
                horseMap.put(laneNumber, theHorse);
            } else {
                System.out.println("Cannot add horse to lane " + laneNumber + " because it is already occupied.");
            }
        }
//        horseArray.add(theHorse);
    }

    @Override
    public void run() {
        try {
            startRace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace() throws UnsupportedEncodingException
    {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        //declare a local variable to tell us when the race is finished
        boolean finished = false;

        //reset all the lanes (all horses not fallen and back to 0). 
//        lane1Horse.goBackToStart();
//        lane2Horse.goBackToStart();
//        lane3Horse.goBackToStart();
//        for(int i = 0; i < horseArray.size(); i++)
//        {
//            horseArray.get(i).goBackToStart();
//        }
        for (Horse H : horseMap.values()){
            H.goBackToStart();
        }

        while (!finished)
        {

            //move each horse
            for(Horse H : horseMap.values()){
                moveHorse(H);
            }

            //print the race positions
            printRace();
            try {
                GUI MainGUI = GUI.getInstance();
                Thread GUIThread = new Thread(MainGUI);
                GUIThread.start();
            }catch(Exception e){

            }

            //if any of the three horses has won the race is finished
//            if ( raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse) )
//            {
//                finished = true;
//            }
//            for(int i = 0; i < horseArray.size(); i++)
//            {
//                if (raceWonBy(horseArray.get(i))){
//                    finished = true;
//                }
//            }
            for(Horse H : horseMap.values()){
                if(raceWonBy(H)){
                    finished = true;
                }
            }
            //wait for 100 milliseconds
            try{

                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}

//            if(lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen())
//            {
//                System.out.print("All Horses have fallen!");
//                finished = true;
//            }
            boolean allFall= true;

//            for(int i = 0; i < horseArray.size(); i++)
//            {
//                if (!horseArray.get(i).hasFallen()){
//                    allFall = false;
//                }
//            }
            for (Horse H : horseMap.values()){
                if(!H.hasFallen()){
                    allFall = false;
                }
            }

            if (allFall) {
                System.out.print("All Horses have fallen!");
                allFallen = true;
                finished = true;
            }
        }
    }


    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen

        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            System.out.print("And the winner is " + theHorse.getName());
            theHorse.setConfidence(theHorse.getConfidence() + 0.1);
            wonBy = theHorse;
            return true;
        }
        else
        {
            return false;
        }
    }

    /***
     * Print the race on the terminal
     */
    private void printRace() throws UnsupportedEncodingException
    {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

//        System.out.print('\u000C');  //clear the terminal window


        System.out.print("\033[H\033[2J"); //clear the terminal windowj
        System.out.flush();

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

//        printLane(lane1Horse);
//        System.out.println();
//
//        printLane(lane2Horse);
//        System.out.println();
//
//        printLane(lane3Horse);
//        System.out.println();
//        for(int i = 0; i < horseArray.size();  i++){
//            printLane(horseArray.get(i));
//            System.out.println();
//        }
        for(int i = 1; i < lanes + 1; i++){
            if(horseMap.get(i) != null){
                printLane(horseMap.get(i));
                System.out.println();
            } else {
                printLane();
                System.out.println();
            }
        }


        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) throws UnsupportedEncodingException
    {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);

        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print("\u274C");
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }

        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);

        //print the | for the end of the track
        System.out.print('|');
        System.out.print(" " +  theHorse.getName().toUpperCase() + " (Current Confidence: " );
        System.out.printf("%.1f" , theHorse.getConfidence());
        System.out.print(")");

    }

    /**
        *prints an empty lane
        *
     */
    private void printLane() throws UnsupportedEncodingException
    {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ',raceLength + 1);


        //print the | for the end of the track
        System.out.print('|');
        System.out.print(" (EMPTY LANE)" );

    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) throws UnsupportedEncodingException
    {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public ArrayList<String> returnRace()
    {
        ArrayList<String> RaceSnapshot = new ArrayList<>();

        RaceSnapshot.addAll(returnMultiplePrint('=',raceLength+3));  //top edge of track

        for(int i = 1; i < lanes + 1; i++){
            if(horseMap.get(i) != null){
                RaceSnapshot.addAll(returnPrintLane(horseMap.get(i)));
            } else {
                RaceSnapshot.addAll(returnPrintLane());
            }
        }

        RaceSnapshot.addAll(returnMultiplePrint('=',raceLength+3)); //bottom edge of track


        return RaceSnapshot;
    }

    private ArrayList<String> returnMultiplePrint(char aChar, int times)
    {
        ArrayList<String> LineString = new ArrayList<>();
        int i = 0;
        while (i < times)
        {
            LineString.add(String.valueOf(aChar));
            i = i + 1;
        }
        StringBuffer stringBuffer = new StringBuffer();

        for (String S : LineString){
            stringBuffer.append(S);
        }

        String stringOutput = stringBuffer.toString();

        return LineString;
    }


    private ArrayList<String> returnPrintLane(Horse theHorse)
    {
        ArrayList<String> LineOutput = new ArrayList<>();
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        //| for the beginning of the lane
        LineOutput.add("|");


        LineOutput.addAll(returnMultiplePrint(' ',spacesBefore));

        if(theHorse.hasFallen())
        {
            LineOutput.add("\u274C");
        }
        else
        {
            LineOutput.add(String.valueOf(theHorse.getSymbol()));
        }


        LineOutput.addAll(returnMultiplePrint(' ',spacesAfter));


        LineOutput.add("|");
//        System.out.print(" " +  theHorse.getName().toUpperCase() + " (Current Confidence: " );
//        System.out.printf("%.1f" , theHorse.getConfidence());
//        System.out.print(")");

        StringBuffer stringBuffer = new StringBuffer();
        for (String S : LineOutput){
            stringBuffer.append(S);
        }

        String outputString = stringBuffer.toString();

        return LineOutput;
    }

    private ArrayList<String> returnPrintLane()
    {
        ArrayList<String> LineOutput = new ArrayList<>();

        //| for the beginning of the lane
        LineOutput.add("|");

        LineOutput.addAll(returnMultiplePrint(' ',raceLength));

        LineOutput.add("|");


        StringBuffer stringBuffer = new StringBuffer();
        for (String S : LineOutput){
            stringBuffer.append(S);
        }

        String outputString = stringBuffer.toString();

        return LineOutput;

    }


    public int getLanes(){
        return this.lanes;
    }

    public int getRaceLength(){
        return this.raceLength;
    }

    public HashMap<Integer, Horse> getHorseMap(){
        return this.horseMap;
    }

    public Boolean allFallen(){
        return allFallen;
    }

    public Horse getWonBy(){
        return wonBy;
    }

}
