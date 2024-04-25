import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


public class GUI extends JFrame{

    private static Race mainRace;
    private static JFrame mainFrame;
    private static JPanel HomeScreen;
    private static JPanel CurrentScreen;
    private static ArrayList<Horse> AllHorses = new ArrayList<>();

    public GUI() {

        //Screen Initialisation////////////////////////////////////////////////////////////////

        mainFrame = new JFrame("Horse Race Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new CardLayout());
        mainFrame.setSize(1200, 600);

        JPanel TitleScreen = new JPanel(new GridBagLayout());
        TitleScreen.setBackground(Color.decode("#392F5A"));

        mainFrame.add(TitleScreen);
        CurrentScreen = TitleScreen;

        HomeScreen = new JPanel(new GridBagLayout());
        HomeScreen.setBackground(Color.decode("#392F5A"));

        JPanel NewRaceScreen = new JPanel(new GridBagLayout());
        NewRaceScreen.setBackground(Color.decode("#392F5A"));

        JPanel AddHorseScreen = new JPanel(new GridBagLayout());
        AddHorseScreen.setBackground(Color.decode("#392F5A"));

        //Title Screen////////////////////////////////////////////////////////////////////

            JPanel TitlePanel = new JPanel(new BorderLayout());

            JLabel TitleLabel = new JLabel("Horse Race Simulator!", SwingConstants.CENTER);

            JButton startButton = new JButton("Start!");

            startButton.addActionListener(e -> switchScreens(TitleScreen, HomeScreen));
            startButton.setFocusPainted(false);
            startButton.setBackground(Color.decode("#F4D06F"));
            startButton.setForeground(Color.decode("#23231A"));
    //        startButton.setBorder(new LineBorder(Color.BLUE, 2));

            TitlePanel.add(TitleLabel);
    //        TitlePanel.setBorder(new LineBorder(Color.BLUE, 2));
    //        mainFrame.add(TitlePanel);



            TitlePanel.setBackground(new Color(0,0,0,0));
            TitleLabel.setForeground(Color.decode("#FFF8F0"));

            GridBagConstraints gridConstraints = new GridBagConstraints();

            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = 1;
            gridConstraints.ipadx = 15;
            gridConstraints.ipady = 10;
            gridConstraints.fill = GridBagConstraints.BOTH;

            TitleScreen.add(startButton, gridConstraints);

            gridConstraints.gridy = 0;
            gridConstraints.insets = new Insets(2, 2, 50, 2);

            TitleScreen.add(TitlePanel, gridConstraints);


        mainFrame.setVisible(true);


//        mainFrame.add(NewRaceScreen);

        //Home Screen/////////////////////////////////////////////////////////////////////////////////////////

            JButton RaceButton = new JButton("Create RaceTrack!");
            RaceButton.addActionListener(e -> switchScreens(HomeScreen, NewRaceScreen));
            RaceButton.setFocusPainted(false);
            RaceButton.setBackground(Color.decode("#F4D06F"));
            RaceButton.setForeground(Color.decode("#23231A"));

            JButton TrackButton = new JButton("Start Race!");
            TrackButton.setFocusPainted(false);
            TrackButton.setBackground(Color.decode("#F4D06F"));
            TrackButton.setForeground(Color.decode("#23231A"));

            JButton HorsesButton = new JButton("Customize Horses!");
            HorsesButton.setFocusPainted(false);
            HorsesButton.setBackground(Color.decode("#F4D06F"));
            HorsesButton.setForeground(Color.decode("#23231A"));

            JButton AddHorseButton = new JButton("New Horse!");
            AddHorseButton.addActionListener(e -> switchScreens(HomeScreen, AddHorseScreen));
            AddHorseButton.setFocusPainted(false);
            AddHorseButton.setBackground(Color.decode("#F4D06F"));
            AddHorseButton.setForeground(Color.decode("#23231A"));

            JButton StatisticsButton = new JButton("Statistics and Analytics");
            StatisticsButton.setFocusPainted(false);
            StatisticsButton.setBackground(Color.decode("#F4D06F"));
            StatisticsButton.setForeground(Color.decode("#23231A"));

            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = 0;
            gridConstraints.ipadx = 15;
            gridConstraints.ipady = 10;

            HomeScreen.add(RaceButton, gridConstraints);

            gridConstraints.gridx = 1;

            HomeScreen.add(TrackButton, gridConstraints);

            gridConstraints.gridx = 2;

            HomeScreen.add(HorsesButton, gridConstraints);

            gridConstraints.gridx = 3;

            HomeScreen.add(AddHorseButton, gridConstraints);

            gridConstraints.gridx = 4;

            HomeScreen.add(StatisticsButton, gridConstraints);


        //New Race Screen//////////////////////////////////////////////////////////////////////

            JLabel LaneLabel = new JLabel("How Many Lanes are in your track?");
            LaneLabel.setForeground(Color.decode("#FFF8F0"));

            JTextField LaneNumber = new JTextField(10);
            LaneNumber.setForeground(Color.GRAY);
            LaneNumber.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!LaneNumber.getText().equals("")){
                        LaneNumber.setText("");
                        LaneNumber.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    LaneNumber.setForeground(Color.GRAY);
                }
            });

            JLabel DistanceLabel = new JLabel("How long is your track in metres?");
            DistanceLabel.setForeground(Color.decode("#FFF8F0"));

            JTextField RaceDistance = new JTextField(10);
            RaceDistance.setForeground(Color.GRAY);
            RaceDistance.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!RaceDistance.getText().equals("")){
                        RaceDistance.setText("");
                        RaceDistance.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    RaceDistance.setForeground(Color.GRAY);
                }
            });

            JTextField[] NewRaceFields = new JTextField[2];
            NewRaceFields[0] = LaneNumber;
            NewRaceFields[1] = RaceDistance;

            JButton RaceSubmitButton = new JButton("Submit!");
            RaceSubmitButton.addActionListener(e -> intCheckTextFields(NewRaceFields));
            RaceSubmitButton.setFocusPainted(false);
            RaceSubmitButton.setBackground(Color.decode("#F4D06F"));
            RaceSubmitButton.setForeground(Color.decode("#23231A"));

            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = 0;
            gridConstraints.ipadx = 15;
            gridConstraints.ipady = 10;
            gridConstraints.fill = GridBagConstraints.NONE;

            NewRaceScreen.add(LaneLabel, gridConstraints);

            gridConstraints.gridy = 1;
            gridConstraints.insets = new Insets(2,2,50,2);

            NewRaceScreen.add(LaneNumber, gridConstraints);

            gridConstraints.gridy = 2;
            gridConstraints.insets = new Insets(2,2,2,2);

            NewRaceScreen.add(DistanceLabel, gridConstraints);

            gridConstraints.gridy = 3;

            NewRaceScreen.add(RaceDistance, gridConstraints);

            gridConstraints.gridy = 4;
            gridConstraints.insets = new Insets(20,2,2,2);

            NewRaceScreen.add(RaceSubmitButton, gridConstraints);

        //Add Horse Screen////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel newHorseNameLabel = new JLabel("What would you like to name your new horse?");
        newHorseNameLabel.setForeground(Color.decode("#FFF8F0"));

        JTextField newHorseName = new JTextField(10);

        JLabel newHorseConfidenceLabel = new JLabel("How Confident would you like your horse to be?");
        newHorseConfidenceLabel.setForeground(Color.decode("#FFF8F0"));

        JTextField newHorseConfidence = new JTextField(10);
        newHorseConfidence.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!newHorseConfidence.getText().equals("")){
                    newHorseConfidence.setText("");
                    newHorseConfidence.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                newHorseConfidence.setForeground(Color.GRAY);
            }
        });

        JButton newHorseSubmitButton = new JButton("Submit!");
        newHorseSubmitButton.addActionListener(e -> newHorseCheck(newHorseName, newHorseConfidence));
        newHorseSubmitButton.setFocusPainted(false);
        newHorseSubmitButton.setBackground(Color.decode("#F4D06F"));
        newHorseSubmitButton.setForeground(Color.decode("#23231A"));

        gridConstraints.insets = new Insets(2,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 15;
        gridConstraints.ipady = 10;

        AddHorseScreen.add(newHorseNameLabel, gridConstraints);

        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(2, 2 ,50,2);

        AddHorseScreen.add(newHorseName, gridConstraints);

        gridConstraints.gridy = 2;
        gridConstraints.insets = new Insets(2,2,2,2);

        AddHorseScreen.add(newHorseConfidenceLabel, gridConstraints);

        gridConstraints.gridy = 3;

        AddHorseScreen.add(newHorseConfidence, gridConstraints);

        gridConstraints.gridy = 4;
        gridConstraints.insets = new Insets(20, 2 ,2,2);

        AddHorseScreen.add(newHorseSubmitButton, gridConstraints);




//        JButton HorseButton = new JButton("Add Horse!");
//        HorseButton.addActionListener(e -> addHorse());
//        HorseButton.setFocusPainted(false);
//        HorseButton.setBackground(Color.decode("#F4D06F"));
//        HorseButton.setForeground(Color.decode("#23231A"));
//
//        JButton StartRaceButton = new JButton("Start Race!");
//        StartRaceButton.addActionListener(e -> StartRace());
//        StartRaceButton.setFocusPainted(false);
//        StartRaceButton.setBackground(Color.decode("#F4D06F"));
//        StartRaceButton.setForeground(Color.decode("#23231A"));
//
//        gridConstraints.insets = new Insets(2,2,2,2);
//        gridConstraints.gridx = 0;
//        gridConstraints.gridy = 0;
//        gridConstraints.ipadx = 15;
//        gridConstraints.ipady = 10;
//
//        NewRaceScreen.add(HorseButton, gridConstraints);
//
//        gridConstraints.gridy = 1;
//
//        NewRaceScreen.add(StartRaceButton, gridConstraints);


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    public void switchScreens(JPanel ScreenOff,  JPanel ScreenOn){

        mainFrame.remove(ScreenOff);
        mainFrame.repaint();
        mainFrame.revalidate();

        mainFrame.add(ScreenOn);
        mainFrame.repaint();
        mainFrame.revalidate();

        CurrentScreen = ScreenOn;
        return;
    }

    public void HomeScreen(){
        mainFrame.remove(CurrentScreen);
        mainFrame.repaint();
        mainFrame.revalidate();

        mainFrame.add(HomeScreen);
        mainFrame.repaint();
        mainFrame.revalidate();

        return;
    }

    public  String  checkInt( String checkValue){
        if (checkValue.isEmpty()){
            return "There is no value";
        }
        try{
            if (Integer.valueOf(checkValue) <= 0) {
                return "Invalid number";
            }
        } catch(Exception e) {
            try{
                Double.valueOf(checkValue);
                return "No Decimals";
            } catch (Exception E) {
                return "Not a number";
            }
        }

        return "Applicable";
    }

    public  String  checkDouble( String checkValue){
        if (checkValue.isEmpty()){
            return "There is no value";
        }
        try{
            if (Double.valueOf(checkValue) < 0 || Double.valueOf(checkValue) > 1) {
                return "In-between 0 and 1";
            }
        } catch(Exception e) {
            return "Not a number";
        }

        return "Applicable";
    }

    public void intCheckTextFields(JTextField[] TextFields){
        boolean Accepted = true;
        String checkReturn;

        for( JTextField J : TextFields){
            checkReturn = checkInt(J.getText());
            if(!checkReturn.equals("Applicable")){
                Accepted = false;
                J.setText(checkReturn);
            }
        }

        if(Accepted == true){
            NewRace(TextFields[0].getText(), TextFields[1].getText());
            HomeScreen();
        }
    }

    public void newHorseCheck(JTextField HorseName, JTextField HorseConfidence){
        boolean Accepted = true;
        String checkReturn;

        checkReturn = checkDouble(HorseConfidence.getText());

        if(!checkReturn.equals("Applicable")){
            Accepted = false;
            HorseConfidence.setText(checkReturn);
        }

        if(Accepted == true){
            AllHorses.add( new Horse('*', HorseName.getText(), Double.valueOf(HorseConfidence.getText())));
            HomeScreen();
        }
    }


    public void NewRace(String lanes, String distance){
        mainRace = new Race(Integer.valueOf(lanes), Integer.valueOf(distance));

        return;
    }

    public void addHorse(){
        Scanner SCANNER = new Scanner(System.in);
        char newSymbol;
        String newName;
        double newConfidence;
        System.out.println("What Symbol do you want to represent your horse?(Single Character): ");
        newSymbol = SCANNER.nextLine().charAt(0);
        System.out.println("What Name do you want to give your horse?: ");
        newName = SCANNER.nextLine();
        System.out.println("How confident is your horse?(between 0-1): ");
        newConfidence = SCANNER.nextDouble();
        Horse newHorse = new Horse(newSymbol,newName, newConfidence);
        int laneNumber;
        System.out.println("What lane number do you want to put your horse in?: ");
        laneNumber = SCANNER.nextInt();
        mainRace.addHorse(newHorse,laneNumber);
    }

    public void StartRace(){
        try {
            mainRace.startRace();
        } catch (UnsupportedEncodingException e) {

        }
    }

}
