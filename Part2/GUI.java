import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.geom.CubicCurve2D;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
        mainFrame.setLocationRelativeTo(null);

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

        JPanel ChooseHorsesScreen = new JPanel(new GridBagLayout());
        ChooseHorsesScreen.setBackground(Color.decode("#392F5A"));



        //Title Screen////////////////////////////////////////////////////////////////////

            JPanel TitlePanel = new JPanel(new BorderLayout());

            JLabel TitleLabel = new JLabel("Horse Race Simulator!", SwingConstants.CENTER);

            JButton startButton = createButton("Start");
            startButton.addActionListener(e -> switchScreens(TitleScreen, HomeScreen));
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

            JButton RaceButton = createButton("Create RaceTrack!");
            RaceButton.addActionListener(e -> switchScreens(HomeScreen, NewRaceScreen));


            JButton StartButton = createButton("Start Race!");
            StartButton.addActionListener(e -> switchScreens(HomeScreen, ChooseHorsesScreen));
            StartButton.addActionListener(e-> {

                JPanel CHMainPanel = new JPanel(new FlowLayout());
                CHMainPanel.setBackground(new Color(0,0,0,0));
                CHMainPanel.setName("Delete");

                JButton StartRaceButton = new JButton("Start Race!");
                StartRaceButton.addActionListener(E -> StartRaceGUI());
                StartRaceButton.setBackground(Color.decode("#261D03"));
                StartRaceButton.setForeground(Color.decode("#FFC685"));
                StartRaceButton.setFocusPainted(false);
                StartRaceButton.setBorderPainted(false);
                StartRaceButton.setEnabled(false);
                StartRaceButton.setName("Delete");



                ArrayList<JButton> LockedInHorses = new ArrayList<>();

                if (mainRace != null){
                    JLabel RaceInfoLabel = new JLabel(mainRace.getLanes() + " Lane Track, " + mainRace.getRaceLength() + " Metres.");
                    RaceInfoLabel.setForeground(Color.decode("#FFF8F0"));
                    RaceInfoLabel.setFont(new Font("Dialog", Font.BOLD, 18));
                    RaceInfoLabel.setName("Delete");

                    HashMap<Horse, JLabel> HorseLabelMap = new HashMap<>();

                    for (Horse H : AllHorses){
                        JLabel HName = new JLabel(H.getName(), SwingConstants.CENTER);
                        HName.setForeground(Color.decode("#FFF8F0"));
                        HName.setFont(new Font("Dialog", Font.BOLD, 16));

                        HorseLabelMap.put(H, HName);
                    }

                    gridConstraints.insets = new Insets(2,2,20,2);
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 1;
                    gridConstraints.ipadx = 15;
                    gridConstraints.ipady = 10;

                    ChooseHorsesScreen.add(RaceInfoLabel, gridConstraints);

                    gridConstraints.insets = new Insets(2,2,20,2);
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 3;
                    gridConstraints.ipadx = 15;
                    gridConstraints.ipady = 10;

                    ChooseHorsesScreen.add(CHMainPanel, gridConstraints);

                    if(HorseLabelMap.isEmpty()){
                        JLabel noHorsesLabel = new JLabel("No Horses to Race :(");
                        noHorsesLabel.setForeground(Color.decode("#FFF8F0"));
                        CHMainPanel.add(noHorsesLabel);
                    } else {
                        for (JLabel J : HorseLabelMap.values()) {

                            JPanel CHBlockPanel = new JPanel(new GridBagLayout());
                            CHBlockPanel.setBackground(new Color(0,0,0,0));
    //                        CHBlockPanel.setBorder(new LineBorder(Color.decode("#23231A"), 2));

                            JButton chooseHorseButton = createButton("Select!");
                            chooseHorseButton.addActionListener(E -> {
                                Horse thisHorse = null;
                                for(Horse H : HorseLabelMap.keySet()){
                                    if(HorseLabelMap.get(H).equals(J)){
                                        thisHorse = H;
                                    }
                                }
                                int laneValue = addHorseScreen(thisHorse);
                                ChooseHorsesScreen.repaint();
                                ChooseHorsesScreen.revalidate();
                                if (laneValue != -1){
                                    chooseHorseButton.setText("Lane: " + laneValue);
                                    chooseHorseButton.setBackground(Color.decode("#261D03"));
                                    chooseHorseButton.setForeground(Color.decode("#FFC685"));
                                    chooseHorseButton.setBorderPainted(false);
                                    chooseHorseButton.setEnabled(false);
                                    LockedInHorses.add(chooseHorseButton);
                                    for ( ActionListener AL : chooseHorseButton.getActionListeners()){
                                        chooseHorseButton.removeActionListener(AL);
                                    }
                                }

                                if(LockedInHorses.size() >= 2){
                                    StartRaceButton.setEnabled(true);
                                    StartRaceButton.setBackground(Color.decode("#F4D06F"));
                                    StartRaceButton.setForeground(Color.decode("#23231A"));
                                    StartRaceButton.setBorderPainted(true);
                                }

                            });

                            gridConstraints.insets = new Insets(2,2,20,2);
                            gridConstraints.gridx = 0;
                            gridConstraints.gridy = 0;
                            gridConstraints.ipadx = 15;
                            gridConstraints.ipady = 10;

                            CHBlockPanel.add(J, gridConstraints);

                            gridConstraints.gridy = 1;

                            CHBlockPanel.add(chooseHorseButton, gridConstraints);

                            gridConstraints.insets = new Insets(2,2,2,2);
                            gridConstraints.gridx = 0;
                            gridConstraints.gridy = 1;
                            gridConstraints.ipadx = 15;
                            gridConstraints.ipady = 10;

                            CHMainPanel.add(CHBlockPanel);

                            gridConstraints.insets = new Insets(2,2,70,2);
                            gridConstraints.gridx = 0;
                            gridConstraints.gridy =5 ;
                            gridConstraints.ipadx = 15;
                            gridConstraints.ipady = 10;

                            ChooseHorsesScreen.add(StartRaceButton, gridConstraints);

                            }
                    }


                }  else {
                    JLabel NoRaceLabel = new JLabel("There is no race set!");
                    NoRaceLabel.setForeground(Color.decode("#FFF8F0"));
                    NoRaceLabel.setFont(new Font("Dialog", Font.BOLD, 18));
                    NoRaceLabel.setName("Delete");

                    gridConstraints.insets = new Insets(2,2,50,2);
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 2;
                    gridConstraints.ipadx = 15;
                    gridConstraints.ipady = 10;

                    ChooseHorsesScreen.add(NoRaceLabel, gridConstraints);


                }

            });

            JButton HorsesButton = createButton("Customize Horses!");

            JButton AddHorseButton =createButton("New Horse!");
            AddHorseButton.addActionListener(e -> switchScreens(HomeScreen, AddHorseScreen));

            JButton StatisticsButton = createButton("Statistics and Analytics");

            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = 0;
            gridConstraints.ipadx = 15;
            gridConstraints.ipady = 10;

            HomeScreen.add(RaceButton, gridConstraints);

            gridConstraints.gridx = 1;

            HomeScreen.add(StartButton, gridConstraints);

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

            JButton RaceSubmitButton = createButton("Submit!");
            RaceSubmitButton.addActionListener(e -> intCheckTextFields(NewRaceFields));

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

        JButton newHorseSubmitButton =createButton("Submit!");
        newHorseSubmitButton.addActionListener(e -> newHorseCheck(newHorseName, newHorseConfidence));

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

    //Choose Horses Screen/////////////////////////////////////////////////////////////////////

        JLabel CHTitle = new JLabel("Which Horses are Racing?");
        CHTitle.setForeground(Color.decode("#FFF8F0"));
        CHTitle.setFont(new Font("Dialog", Font.BOLD, 20));

        JLabel HorseName = new JLabel("Horse Name");
        HorseName.setForeground(Color.decode("#FFF8F0"));

        HashMap<Horse, JLabel> HorseLabelMap = new HashMap<>();

        for (Horse H : AllHorses){
            JLabel HName = new JLabel(H.getName());
            HName.setForeground(Color.decode("#FFF8F0"));

            HorseLabelMap.put(H, HName);
        }

        JButton chooseHorsesBackButton = createButton("Back");
        chooseHorsesBackButton.addActionListener(e -> HomeScreen());
        chooseHorsesBackButton.addActionListener(e ->{
            try{
                resetHorses();
            } catch (Exception E){}
            for( Component C : ChooseHorsesScreen.getComponents()){
                try {
                    if (C.getName().equals("Delete")) {
                        ChooseHorsesScreen.remove(C);
                    }
                }catch (Exception E){}
            }
        });


        gridConstraints.insets = new Insets(2,2,10,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 15;
        gridConstraints.ipady = 10;

        ChooseHorsesScreen.add(CHTitle, gridConstraints);

        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(2,2,2,2);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 6;

        ChooseHorsesScreen.add(chooseHorsesBackButton, gridConstraints);

    //Race Screen////////////////////////////////////////////////////////////////////////////////////////////


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
        CurrentScreen = HomeScreen;

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
            for(JTextField J : TextFields){
                J.setText("");
            }
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
            HorseName.setText("");
            HorseConfidence.setText("");
            HomeScreen();
        }
    }


    public void NewRace(String lanes, String distance){
        mainRace = new Race(Integer.valueOf(lanes), Integer.valueOf(distance));

        return;
    }

    public JButton createButton(String Text){
        JButton newButton = new JButton(Text);
        newButton.setFocusPainted(false);
        newButton.setBackground(Color.decode("#F4D06F"));
        newButton.setForeground(Color.decode("#23231A"));

        return newButton;
    }


    public int addHorseScreen(Horse Horse){
        final int[] ChosenPosition = {-1};

        JDialog ChooseLaneFrame = new JDialog(mainFrame, "Choose Lane", Dialog.ModalityType.DOCUMENT_MODAL);
        ChooseLaneFrame.setLayout(new CardLayout());


//        JFrame ChooseLaneFrame = new JFrame("Choose Lane");


        ChooseLaneFrame.setLayout(new CardLayout());
        ChooseLaneFrame.setSize(500,250);
        ChooseLaneFrame.setMaximumSize( new Dimension(800, 900));


        JPanel MainPanel = new JPanel(new GridBagLayout());
        MainPanel.setBackground(Color.decode("#23231A"));

        ChooseLaneFrame.add(MainPanel);

        JLabel LaneTitle = new JLabel("Which lane will you choose?", SwingConstants.CENTER);
        LaneTitle.setForeground(Color.decode("#F4D06F"));
        LaneTitle.setFont(new Font("Dialog", Font.BOLD, 20));

        JPanel ButtonPanel = new JPanel(new GridBagLayout());
        ButtonPanel.setBackground(new Color(0,0,0,0));

        JButton[] LaneButtons = new JButton[mainRace.getLanes()];
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.insets = new Insets(2,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 15;
        gridConstraints.ipady = 10;
        gridConstraints.fill = GridBagConstraints.BOTH;

        for(int i = 0; i < mainRace.getLanes(); i++){

            LaneButtons[i] = createButton("Lane " +( i + 1));

            int LanePosition = i + 1;
            LaneButtons[i].addActionListener(e -> addHorse(Horse, LanePosition));
            LaneButtons[i].addActionListener(e-> {
                ChosenPosition[0] = LanePosition;
                ChooseLaneFrame.dispatchEvent(new WindowEvent(ChooseLaneFrame, WindowEvent.WINDOW_CLOSING));
            });

            gridConstraints.gridx = gridConstraints.gridx + 1;

            if( i % 7 == 0){
                gridConstraints.gridy = gridConstraints.gridy + 1;
                gridConstraints.gridx = 0;
            }

            ButtonPanel.add(LaneButtons[i], gridConstraints);

        }

        for(Integer I : mainRace.getHorseMap().keySet()){
            LaneButtons[I - 1].setText(mainRace.getHorseMap().get(I).getName());
            LaneButtons[I - 1].setBackground(Color.decode("#261D03"));
            LaneButtons[I - 1].setForeground(Color.decode("#FFC685"));
            LaneButtons[I - 1].setBorderPainted(false);
            for(ActionListener AL : LaneButtons[I - 1].getActionListeners()){
                LaneButtons[I - 1].removeActionListener(AL);
            }
        }


        gridConstraints.insets = new Insets(2,2,20,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 15;
        gridConstraints.ipady = 10;
        gridConstraints.fill = GridBagConstraints.NONE;

        MainPanel.add(LaneTitle, gridConstraints);


        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(2,2,20,2);

        MainPanel.add(ButtonPanel, gridConstraints);

//        MainPanel.setSize(  MainPanel.getWidth() , MainPanel.getHeight() + (30 *(mainRace.getLanes())));
        MainPanel.setSize(MainPanel.getWidth(), 500);

        ChooseLaneFrame.pack();
        ChooseLaneFrame.setLocationRelativeTo(mainFrame);

        ChooseLaneFrame.setVisible(true);
//        DialogFrame.setVisible(true);

        return ChosenPosition[0];
    }

    public void addHorse(Horse Horse, int Lane){
        mainRace.addHorse(Horse, Lane);

        return;
    }

    public void resetHorses(){
        mainRace = new Race(mainRace.getLanes(), mainRace.getRaceLength());

        return;
    }

    public void StartRaceGUI(){
        JDialog RaceDialog = new JDialog(mainFrame, "Race!", Dialog.ModalityType.DOCUMENT_MODAL);
        RaceDialog.setLayout(new CardLayout());
        RaceDialog.setSize(mainFrame.getSize());
        RaceDialog.setLocationRelativeTo(mainFrame);

        JPanel RaceScreen = new JPanel(new GridBagLayout());
        RaceScreen.setBackground(Color.decode("#392F5A"));

        RaceDialog.add(RaceScreen);

        GridBagConstraints gridConstraints = new GridBagConstraints();

        ArrayList<JPanel> RaceBorder = new ArrayList<>();

//        for(int i = 0; i < mainRace.getRaceLength(); i++){
//
//        }


        RaceDialog.setVisible(true);

        return;
    }

    public void StartRace(){
        try {
            mainRace.startRace();
        } catch (UnsupportedEncodingException e) {

        }
    }

}
