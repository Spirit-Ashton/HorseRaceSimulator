
/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    String name;
    char symbol;
    int distance;
    boolean fallen;
    double confidence;
    
      
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
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {

    }
    
    public double getConfidence()
    {
        
    }
    
    public int getDistanceTravelled()
    {
        
    }
    
    public String getName()
    {
        
    }
    
    public char getSymbol()
    {
        
    }
    
    public void goBackToStart()
    {
        
    }
    
    public boolean hasFallen()
    {
        
    }

    public void moveForward()
    {
        
    }

    public void setConfidence(double newConfidence)
    {
        
    }
    
    public void setSymbol(char newSymbol)
    {
        
    }
    
}
