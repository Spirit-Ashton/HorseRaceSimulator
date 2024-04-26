
/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distance;
    private boolean fallen;
    private double confidence;
    private int wins;
    private int losses;
    private int winstreak;
    private int topWinstreak;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.name =  horseName;
       this.symbol = horseSymbol;
       this.distance = 0;
       this.fallen = false;
       this.confidence = horseConfidence;
       this.wins = 0;
       this.losses = 0;
       this.winstreak = 0;
       this.topWinstreak = 0;
    }

    //Other methods of class Horse
    public void fall()
    {
        this.fallen  = true;
        if( this.confidence >= 0.1){
            this.confidence = this.confidence - 0.1;
        }

        return;
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distance;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void goBackToStart()
    {
        this.distance = 0;

        return;
    }
    
    public boolean hasFallen()
    {
        return this.fallen;
    }

    public void moveForward()
    {this.distance = this.distance + 1;
    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence <= 1  && newConfidence >= 0) {
            this.confidence = newConfidence;
        }

        return;
    }

    public void resetFall(){
        this.fallen = false;
    }

    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;

        return;
    }

    public int getLosses() {
        return this.losses;
    }
    public int getWins(){
        return this.wins;
    }
    public int getWinstreak(){
        return this.winstreak;
    }

    public int getTopWinstreak(){
        return this.topWinstreak;
    }

    public void win(){
        this.wins  ++;
        this.winstreak ++;
        if(this.winstreak > this.topWinstreak){
            this.topWinstreak = this.winstreak;
        }
    }

    public void lose(){
        this.losses ++;
        this.winstreak = 0;
    }

    @Override
    public String toString() {
        String returnString = this.name + ":  Wins-" + this.wins + ", Losses-" + this.losses + ", current Winstreak-" + this.winstreak + ", highest Winstreak-" + this.topWinstreak + ", current Confidence Level-" + String.format("%.3f",this.confidence);

        return returnString;
    }

    public String storeString() {
        String returnString = this.name + " : "+ this.symbol + " " + this.distance + " " + this.fallen + " " + this.confidence + " " + this.wins + " " + this.losses + " " + this.winstreak + " " + this.topWinstreak;

        return returnString;
    }
}
