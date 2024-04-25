import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class GUI extends JFrame{

    private static Race mainRace;
    private static JFrame mainFrame;

    public GUI() {

        //Screen Initialisation////////////////////////////////////////////////////////////////

        mainFrame = new JFrame("Horse Race Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new CardLayout());
        mainFrame.setSize(1200, 600);

        JPanel TitleScreen = new JPanel(new GridBagLayout());
        TitleScreen.setBackground(Color.decode("#392F5A"));

        mainFrame.add(TitleScreen);

        JPanel HomeScreen = new JPanel(new GridBagLayout());
//        HomeScreen.setVisible(false);
        HomeScreen.setBackground(Color.decode("#392F5A"));

        JPanel NewRaceScreen = new JPanel(new GridBagLayout());
//        NewRaceScreen.setVisible(false);
        NewRaceScreen.setBackground(Color.decode("#392F5A"));

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

            GridBagConstraints titleConstraints = new GridBagConstraints();

            titleConstraints.insets = new Insets(2,2,2,2);
            titleConstraints.gridx = 0;
            titleConstraints.gridy = 1;
            titleConstraints.ipadx = 15;
            titleConstraints.ipady = 10;
            titleConstraints.fill = GridBagConstraints.BOTH;

            TitleScreen.add(startButton, titleConstraints);

            titleConstraints.gridy = 0;
            titleConstraints.insets = new Insets(2, 2, 50, 2);

            TitleScreen.add(TitlePanel, titleConstraints);


        mainFrame.setVisible(true);


//        mainFrame.add(NewRaceScreen);

        //Home Screen/////////////////////////////////////////////////////////////////////////////////////////

            JButton RaceButton = new JButton("Create Race!");
            RaceButton.addActionListener(e -> switchScreens(HomeScreen, NewRaceScreen));
//            RaceButton.addActionListener(e -> NewRace());
            RaceButton.setFocusPainted(false);
            RaceButton.setBackground(Color.decode("#F4D06F"));
            RaceButton.setForeground(Color.decode("#23231A"));

            JButton TrackButton = new JButton("Customize Track!");
            TrackButton.setFocusPainted(false);
            TrackButton.setBackground(Color.decode("#F4D06F"));
            TrackButton.setForeground(Color.decode("#23231A"));

            JButton HorsesButton = new JButton("Customize Horses!");
            HorsesButton.setFocusPainted(false);
            HorsesButton.setBackground(Color.decode("#F4D06F"));
            HorsesButton.setForeground(Color.decode("#23231A"));

            JButton StatisticsButton = new JButton("Statistics and Analytics");
            StatisticsButton.setFocusPainted(false);
            StatisticsButton.setBackground(Color.decode("#F4D06F"));
            StatisticsButton.setForeground(Color.decode("#23231A"));

            titleConstraints.insets = new Insets(2,2,2,2);
            titleConstraints.gridx = 0;
            titleConstraints.gridy = 0;
            titleConstraints.ipadx = 15;
            titleConstraints.ipady = 10;

            HomeScreen.add(RaceButton, titleConstraints);

            titleConstraints.gridx = 1;

            HomeScreen.add(TrackButton, titleConstraints);

            titleConstraints.gridx = 2;

            HomeScreen.add(HorsesButton, titleConstraints);

            titleConstraints.gridx = 3;

            HomeScreen.add(StatisticsButton, titleConstraints);


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
            RaceSubmitButton.addActionListener(e -> checkTextFields(NewRaceFields));
//            RaceSubmitButton.addActionListener(e -> NewRace(LaneNumber.getText(), RaceDistance.getText()));
//            RaceSubmitButton.addActionListener(e -> switchScreens(NewRaceScreen));
            RaceSubmitButton.setFocusPainted(false);
            RaceSubmitButton.setBackground(Color.decode("#F4D06F"));
            RaceSubmitButton.setForeground(Color.decode("#23231A"));

            titleConstraints.insets = new Insets(2,2,2,2);
            titleConstraints.gridx = 0;
            titleConstraints.gridy = 0;
            titleConstraints.ipadx = 15;
            titleConstraints.ipady = 10;
            titleConstraints.fill = GridBagConstraints.NONE;

            NewRaceScreen.add(LaneLabel, titleConstraints);

            titleConstraints.gridy = 1;
            titleConstraints.insets = new Insets(2,2,50,2);

            NewRaceScreen.add(LaneNumber, titleConstraints);

            titleConstraints.gridy = 2;
            titleConstraints.insets = new Insets(2,2,2,2);

            NewRaceScreen.add(DistanceLabel, titleConstraints);

            titleConstraints.gridy = 3;

            NewRaceScreen.add(RaceDistance, titleConstraints);

            titleConstraints.gridy = 4;
            titleConstraints.insets = new Insets(20,2,2,2);

            NewRaceScreen.add(RaceSubmitButton, titleConstraints);


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
//        titleConstraints.insets = new Insets(2,2,2,2);
//        titleConstraints.gridx = 0;
//        titleConstraints.gridy = 0;
//        titleConstraints.ipadx = 15;
//        titleConstraints.ipady = 10;
//
//        NewRaceScreen.add(HorseButton, titleConstraints);
//
//        titleConstraints.gridy = 1;
//
//        NewRaceScreen.add(StartRaceButton, titleConstraints);


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

    public void checkTextFields(JTextField[] TextFields){
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
