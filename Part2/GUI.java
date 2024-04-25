import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    public GUI() {
        JFrame mainFrame = new JFrame("Horse Race Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(1200, 600);

        JPanel TitleScreen = new JPanel(new GridBagLayout());
        TitleScreen.setBackground(Color.decode("#392F5A"));

        mainFrame.add(TitleScreen);

        JPanel TitlePanel = new JPanel(new BorderLayout());

        JLabel TitleLabel = new JLabel("Horse Race Simulator!", SwingConstants.CENTER);

        JButton startButton = new JButton("Start!");

//        startButton.addActionListener(e ->);
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.decode("#F4D06F"));
        startButton.setForeground(Color.decode("#23231A"));
//        startButton.setBorder(new LineBorder(Color.BLUE, 2));

        TitlePanel.add(TitleLabel);
//        TitlePanel.setBorder(new LineBorder(Color.BLUE, 2));
//        mainFrame.add(TitlePanel);



        TitlePanel.setBackground(new Color(0,0,0,0));
        TitleLabel.setForeground(Color.decode("#FFF8F0"));
        TitleLabel.setLocation(100,200);

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
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }


}
