import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.CubicCurve2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class GUI extends JFrame implements Runnable{


    private static Race mainRace;
    private static JFrame mainFrame;
    private static JPanel HomeScreen;
    private static JPanel CurrentScreen;
    private static ArrayList<Horse> AllHorses = new ArrayList<>();
    private static ArrayList<Race> AllRaces = new ArrayList<>();
    private static GridBagConstraints gridConstraints;
    private static ArrayList<JPanel> RaceSnapshot;
    private static JPanel RaceScreen;
    private static ArrayList<JPanel> InfoPanes;
    private static final GUI Instance;

    static {
        try {
            Instance = new GUI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GUI getInstance(){
        return Instance;
    }
    private GUI() throws IOException {

        LoadHorses();;

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

        JPanel StatsScreen = new JPanel(new GridBagLayout());
        StatsScreen.setBackground(Color.decode("#392F5A"));

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

            gridConstraints = new GridBagConstraints();

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
            StartButton.addActionListener(e-> {

                JPanel CHMainPanel = new JPanel(new FlowLayout());
                CHMainPanel.setBackground(new Color(0,0,0,0));
                CHMainPanel.setName("Delete");

                JButton StartRaceButton = new JButton("Start Race!");
                StartRaceButton.setBackground(Color.decode("#261D03"));
                StartRaceButton.setForeground(Color.decode("#FFC685"));
                StartRaceButton.setFocusPainted(false);
                StartRaceButton.setBorderPainted(false);
                StartRaceButton.setEnabled(false);
                StartRaceButton.setName("Delete");
                StartRaceButton.addActionListener(E -> {
                    for( Component C : ChooseHorsesScreen.getComponents()){
                        try {
                            if (C.getName().equals("Delete")) {
                                ChooseHorsesScreen.remove(C);
                            }
                        }catch (Exception é){}
                    }
                    try {
                        HomeScreen();
                        StartRaceGUI();
                        AllRaces.add(mainRace);
                        Thread raceThread = new Thread(mainRace);
                        raceThread.start();
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                });


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

            switchScreens(HomeScreen, ChooseHorsesScreen);

            });

            JButton HorsesButton = createButton("Customize Horses!");

            JButton AddHorseButton =createButton("New Horse!");
            AddHorseButton.addActionListener(e -> switchScreens(HomeScreen, AddHorseScreen));

            JButton StatisticsButton = createButton("Statistics and Analytics");
            StatisticsButton.addActionListener(e -> {

                if(!AllHorses.isEmpty()) {

                    Horse[] HorseArray = new Horse[AllHorses.size()];

                    HorseArray = AllHorses.toArray(HorseArray);

                    JList<Horse> HorseList = new JList<>(HorseArray);

                    String[] items = {"Apple", "Banana", "Orange", "Grapes", "Pineapple"};

                    String[] HorseNameArray = new String[AllHorses.size()];

                    int Index = 0;

                    for (Horse H : AllHorses) {
                        HorseNameArray[Index] = H.getName();
                        Index++;
                    }

                    JList<Horse> StringList = new JList<>(HorseArray);
                    StringList.setPreferredSize(new Dimension(800, 200));
                    StringList.setBackground(Color.decode("#FFF8F0"));
                    StringList.setForeground(Color.decode("#23231A"));
                    StringList.setBorder(new LineBorder(Color.BLACK, 0));
                    StringList.setFont(new Font("Dialog", Font.BOLD, 13));
                    StringList.setVisibleRowCount(5);
                    StringList.setFixedCellHeight(25);

                    JScrollPane ScrollPane = new JScrollPane(StringList);
                    ScrollPane.setName("Delete");
                    ScrollPane.setBackground(Color.decode("#23231A"));
                    ScrollPane.setForeground(Color.decode("#F4D67B"));
                    ScrollPane.setBorder(new LineBorder(Color.BLACK, 0));


                    gridConstraints.insets = new Insets(50, 2, 40, 2);
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 1;
                    gridConstraints.ipadx = 0;
                    gridConstraints.ipady = 0;

                    StatsScreen.add(ScrollPane, gridConstraints);

                    JButton saveDataButton = createButton("Save Data");
                    saveDataButton.setName("Delete");
                    saveDataButton.addActionListener(E -> {
                        try {
                            SaveHorses();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        JOptionPane.showMessageDialog(null,
                                "Data has been Saved");
                        HomeScreen();
                    });

                    gridConstraints.insets = new Insets(50, 2, 2, 2);
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 3;
                    gridConstraints.ipadx = 0;
                    gridConstraints.ipady = 0;

                    StatsScreen.add(saveDataButton,gridConstraints);

                } else{
                    JLabel NoDataLabel = new JLabel("There is No Horse Data to be shown!");
                    NoDataLabel.setForeground(Color.decode("#FFF8F0"));
                    NoDataLabel.setFont(new Font("Dialog", Font.BOLD, 16));
                    NoDataLabel.setName("Delete");

                    gridConstraints.insets = new Insets(50, 2, 40, 2);
                    gridConstraints.gridx = 0;
                    gridConstraints.gridy = 1;
                    gridConstraints.ipadx = 0;
                    gridConstraints.ipady = 0;

                    StatsScreen.add(NoDataLabel,gridConstraints);
                }

                switchScreens(HomeScreen, StatsScreen);
            });

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

    //Stats Screen////////////////////////////////////////////////////////////////////////////////////////////

        JLabel StatsTitle = new JLabel("Statistics and Analytics");
        StatsTitle.setForeground(Color.decode("#FFF8F0"));
        StatsTitle.setFont(new Font("Dialog" , Font.BOLD, 20));

        gridConstraints.insets = new Insets(2,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 0;
        gridConstraints.ipady = 0;

        StatsScreen.add(StatsTitle,gridConstraints);


        JButton StatsBackButton = createButton("Back");
        StatsBackButton.addActionListener(e -> {

            for( Component C : StatsScreen.getComponents()){
                try {
                    if (C.getName().equals("Delete")) {
                        StatsScreen.remove(C);
                    }
                }catch (Exception E){}
            }

            HomeScreen();
        });

        gridConstraints.gridy = 2;

        StatsScreen.add(StatsBackButton,gridConstraints);

    }


    public static void main(String[] args) {
//        GUI mainGUI = new GUI();
//        SwingUtilities.invokeLater(mainGUI);
        SwingUtilities.invokeLater(Instance);
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
        try {
            if (Integer.valueOf(TextFields[0].getText()) > 12) {
                Accepted = false;
                TextFields[0].setText("12 or Under");
            }
        } catch (NumberFormatException e){}
        try {
            if (Integer.valueOf(TextFields[1].getText()) > 50) {
                Accepted = false;
                TextFields[1].setText("50 or Under");
            }
        }catch (NumberFormatException e){}

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
        for(Horse H : AllHorses){
            if(H.getName().equals(HorseName.getText())){
                Accepted = false;
                HorseName.setText("Name Taken");
            }
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
        gridConstraints = new GridBagConstraints();
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
        for(Horse H : AllHorses){
            H.resetFall();

        }
        return;
    }



    public void StartRaceGUI() throws UnsupportedEncodingException {
        JDialog RaceDialog = new JDialog(mainFrame, "Race!");
//        RaceDialog.setLayout(new CardLayout());
//        RaceDialog.setSize(mainFrame.getSize());
//        RaceDialog.setLocationRelativeTo(mainFrame);

//        JFrame RaceDialog = new JFrame( "Race!");
        RaceDialog.setLayout(new CardLayout());
        RaceDialog.setSize(mainFrame.getSize());
        RaceDialog.setLocationRelativeTo(mainFrame);

        JPanel MainHolder = new JPanel(new GridBagLayout());
        MainHolder.setBackground(Color.decode("#3A2F5A"));

        InfoPanes = new ArrayList<>();


        InfoPanes.add(0, new JPanel(new GridBagLayout()));
        InfoPanes.get(0).setBackground(Color.decode("#23231A"));
        InfoPanes.get(0).setBorder(new LineBorder(Color.red, 2));

        gridConstraints.insets = new Insets(2,2,300,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 200;
        gridConstraints.ipady = 30;

        MainHolder.add(InfoPanes.get(0), gridConstraints);

        InfoPanes.add(1, new JPanel(new GridBagLayout()));
        InfoPanes.get(1).setBackground(Color.decode("#23231A"));
        InfoPanes.get(1).setBorder(new LineBorder(Color.red, 2));

        gridConstraints.insets = new Insets(2,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 4;
        gridConstraints.ipadx = 200;
        gridConstraints.ipady = 30;

        MainHolder.add(InfoPanes.get(1), gridConstraints);

        InfoPanes.add(2, new JPanel(new GridBagLayout()));
        InfoPanes.get(2).setBackground(Color.decode("#23231A"));

        gridConstraints.insets = new Insets(2,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 0;
        gridConstraints.ipady = 0;


        MainHolder.add(InfoPanes.get(2),gridConstraints);
        InfoPanes.get(2).setVisible(false);

        JButton FinishedButton = createButton("Back Home");
        FinishedButton.addActionListener( e -> {
            RaceDialog.dispatchEvent(new WindowEvent(RaceDialog, WindowEvent.WINDOW_CLOSING));
            try{
                HomeScreen();
                TimeUnit.MILLISECONDS.sleep(100);
                resetHorses();

            }catch(Exception E){}

        });


        InfoPanes.get(2).add(FinishedButton, gridConstraints);

        InfoPanes.get(0).removeAll();
        InfoPanes.get(1).removeAll();


        RaceScreen = new JPanel(new GridBagLayout());
        RaceScreen.setBackground(Color.decode("#392F5A"));
        RaceScreen.setBorder(new LineBorder(Color.BLACK, 2));

        gridConstraints.insets = new Insets(100,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 0;
        gridConstraints.ipady = 0;


        MainHolder.add(RaceScreen,gridConstraints);

        gridConstraints.weightx = 0;
        gridConstraints.weighty = 0;

        RaceDialog.add(MainHolder);

        gridConstraints = new GridBagConstraints();

        RaceSnapshot= new ArrayList<>();

        trackSnapshot(gridConstraints, RaceSnapshot, RaceScreen, InfoPanes);

        RaceDialog.setVisible(true);

//        mainRace.startRace();
        return;
    }


    private static void trackSnapshot(GridBagConstraints gridConstraints, ArrayList<JPanel> RaceSnapshot, JPanel RaceScreen, ArrayList<JPanel> InfoPanes) {
        RaceSnapshot = new ArrayList<>();
        RaceScreen.removeAll();

        InfoPanes.get(0).removeAll();
        InfoPanes.get(1).removeAll();
        int Interval = 0;

        Boolean allFallen = true;

        for (Horse H : mainRace.getHorseMap().values()){
            if(!H.hasFallen()){
                allFallen = false;
            }
        }

        for(Horse H : mainRace.getHorseMap().values()){
            String ConfidenceText = H.getName() + " Confidence Level: " + String.format("%.1f", H.getConfidence());
            JLabel InfoLabel = new JLabel(ConfidenceText);
            InfoLabel.setForeground(Color.decode("#FFF8F0"));


            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = Interval;
            gridConstraints.ipadx = 0;
            gridConstraints.ipady = 0;


            InfoPanes.get(0).add(InfoLabel,gridConstraints);
            String DistanceText  = null;

            if(!allFallen && mainRace.getWonBy() == null) {
                if (H.hasFallen()) {
                    DistanceText = H.getName() + " has fallen at: " + H.getDistanceTravelled() + " Meters!";
                } else {
                    DistanceText = H.getName() + " has travelled: " + H.getDistanceTravelled() + " Meters!";

                }
                InfoLabel = new JLabel(DistanceText);
                InfoLabel.setForeground(Color.decode("#FFF8F0"));


                InfoPanes.get(1).add(InfoLabel, gridConstraints);

            }

            Interval ++;
        }

        if(allFallen && mainRace.getWonBy() == null){
            JLabel InfoLabel = new JLabel("All the Horses have fallen! Nobody won!");
            InfoLabel.setFont(new Font("Dialog", Font.BOLD, 20));
            InfoLabel.setForeground(Color.decode("#FFF8F0"));

            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = 0;
            gridConstraints.ipadx = 0;
            gridConstraints.ipady = 0;

            InfoPanes.get(1).add(InfoLabel,gridConstraints);
            InfoPanes.get(2).setVisible(true);
        }

        if(mainRace.getWonBy() != null){
            JLabel InfoLabel = new JLabel(mainRace.getWonBy().getName() + " Has won the Race! Congratulations!");
            InfoLabel.setFont(new Font("Dialog", Font.BOLD, 20));
            InfoLabel.setForeground(Color.decode("#FFF8F0"));

            gridConstraints.insets = new Insets(2,2,2,2);
            gridConstraints.gridx = 0;
            gridConstraints.gridy = 0;
            gridConstraints.ipadx = 0;
            gridConstraints.ipady = 0;

            InfoPanes.get(1).add(InfoLabel,gridConstraints);
            InfoPanes.get(2).setVisible(true);
        }


        JPanel RaceBlock = new JPanel();

        gridConstraints.insets = new Insets(2,2,2,2);
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.ipadx = 5;
        gridConstraints.ipady = 5;


        for(int k = 0; k < mainRace.getLanes(); k++) {
            BarrierLane(gridConstraints, RaceSnapshot, RaceScreen);
            gridConstraints.gridy = gridConstraints.gridy - 1;
            if(mainRace.getHorseMap().get(k + 1) != null){
                occupiedLane(gridConstraints, RaceSnapshot, RaceScreen, mainRace.getHorseMap().get(k + 1));
            } else {
                emptyLane(gridConstraints, RaceSnapshot, RaceScreen);
            }
            gridConstraints.gridx = 0;
            gridConstraints.gridy = gridConstraints.gridy - 1;
        }
        gridConstraints.gridy = gridConstraints.gridy - 1;
        BarrierLane(gridConstraints, RaceSnapshot, RaceScreen);


        RaceScreen.repaint();
        RaceScreen.revalidate();
    }

    private static void BarrierLane(GridBagConstraints gridConstraints, ArrayList<JPanel> RaceSnapshot, JPanel RaceScreen) {
        JPanel RaceBlock;
        for (int i = 0; i < mainRace.getRaceLength() + 2; i++) {
            gridConstraints.gridx = i;
            RaceBlock = new JPanel();
            RaceBlock.setBackground(Color.decode("#23231a"));
            RaceSnapshot.add(RaceBlock);
            RaceScreen.add(RaceBlock, gridConstraints);
        }
    }

    private static void emptyLane(GridBagConstraints gridConstraints, ArrayList<JPanel> RaceSnapshot, JPanel RaceScreen) {
        JPanel RaceBlock;
        for (int i = 0; i < mainRace.getRaceLength() + 2 ; i++) {
            gridConstraints.gridx = i;
            RaceBlock = new JPanel();
            RaceBlock.setBackground(Color.decode("#392F5A"));
            if(i == 0 || i == mainRace.getRaceLength() + 1){
                RaceBlock.setBackground(Color.decode("#23231a"));
            }
            RaceSnapshot.add(RaceBlock);
            RaceScreen.add(RaceBlock, gridConstraints);
        }
    }

    private static void occupiedLane(GridBagConstraints gridConstraints, ArrayList<JPanel> RaceSnapshot, JPanel RaceScreen, Horse thisHorse) {
        JPanel RaceBlock;
        for (int i = 0; i < mainRace.getRaceLength() + 2 ; i++) {
            gridConstraints.gridx = i;
            RaceBlock = new JPanel();
            RaceBlock.setBackground(Color.decode("#392F5A"));
            if(i == 0 || i == mainRace.getRaceLength() + 1){
                RaceBlock.setBackground(Color.decode("#23231a"));
            }
            if(i == thisHorse.getDistanceTravelled() + 1){
                if(thisHorse.hasFallen()){
                    RaceBlock.setBackground(Color.decode("#140A00"));
                } else {
                    RaceBlock.setBackground(Color.decode("#FF8811"));
                    }
            }
            RaceSnapshot.add(RaceBlock);
            RaceScreen.add(RaceBlock, gridConstraints);
        }
    }

    @Override
    public void run() {
        try {
            UpdateRace();
        } catch(Exception e){

        }
    }

    public static void UpdateRace() throws UnsupportedEncodingException {
//        Instance.StartRaceGUI();
            trackSnapshot(gridConstraints, RaceSnapshot, RaceScreen, InfoPanes);
    }



    public void StartRace(){
        try {
            mainRace.startRace();
        } catch (UnsupportedEncodingException e) {

        }
    }


    private static void SaveHorses() throws IOException {
        File HorseFile  = new File("Horses.txt");
        HorseFile.createNewFile();
        FileWriter FileWriter = new FileWriter("Horses.txt", false);
        BufferedWriter BufferedWriter = new BufferedWriter(FileWriter);

        for (Horse H: AllHorses){
            BufferedWriter.write(H.storeString());
            BufferedWriter.newLine();
        }

        BufferedWriter.close();
        FileWriter.close();

    }

    private static void LoadHorses() throws IOException {
        File HorseFile = new File("Horses.txt");
        Scanner SCANNER = new Scanner(HorseFile);

        ArrayList<String> HorseLines = new ArrayList<>();
        while(SCANNER.hasNextLine()){
            HorseLines.add(SCANNER.nextLine());
        }

        for(String S : HorseLines){
            ArrayList<String> SplitData = new ArrayList<>();
            for(String s : S.split(" ")){
                SplitData.add(s);
            }
            AllHorses.add(new Horse(  SplitData.get(0)  ,  SplitData.get(1).charAt(0)  ,  Integer.valueOf(SplitData.get(2))  ,  Boolean.valueOf(SplitData.get(3)) , Double.valueOf(SplitData.get(4)) , Integer.valueOf(SplitData.get(5))  , Integer.valueOf(SplitData.get(6))  , Integer.valueOf(SplitData.get(7))  , Integer.valueOf(SplitData.get(8))  ));
        }
    }

}
