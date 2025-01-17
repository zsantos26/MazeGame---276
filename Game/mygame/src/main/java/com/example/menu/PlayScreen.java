package com.example.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import com.example.abstractfactory.AbstractFactory;
import com.example.abstractfactory.GameObjectFactory;
import com.example.game.GameEngine;
/**
 * The PlayScreen represents the screen where the user can start the game.
 * 
 * This class extends the JFrame class and implements the ActionListener interface to respond to intersactions.
 * with the buttons. The user can also go back to the main menu.
 * 
 * The buttons are styled using ButtonStyler class to represent SFU colours
 */
public class PlayScreen extends JFrame implements ActionListener {

    // Constant to represent SFU primary colours
    private static final Color PRIMARY_COLOR = new Color(204, 6, 51);

    // buttons
    JButton playButton, backButton;

    /**
     * Constructor for the PlayScreen class.
     * 
     * This constructor establishes the background color of the JFrame to match SFU colours.
     * It also makes and establishes the buttons, and adds them to the JFrame.
     */
    public PlayScreen() {
        
        // Set the title to "Play"
        setTitle("Play");

        // set the background color of the JFrame
        getContentPane().setBackground(PRIMARY_COLOR);

        // Declare 2 JButton instances to represent interactable buttons on the screen.
        playButton = new JButton("Start Game");
        backButton = new JButton("Back");

        // Use ButtonStyler to style buttons
        ButtonStyler.styleButton(playButton, 200, 75, 24);
        ButtonStyler.styleButton(backButton, 100, 50, 16);

        playButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        // add action listeners to the buttons
        playButton.addActionListener(this);
        backButton.addActionListener(this);

        // create the panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 65, 10)); // set the layout manager to FlowLayout and center the buttons
        buttonPanel.setBackground(PRIMARY_COLOR);
        buttonPanel.add(playButton);
        buttonPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 100));  // Prevent UI structure from breaking down
        buttonPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE,100));
        
        // create the panel for the back button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        backPanel.setBackground(PRIMARY_COLOR);
        backPanel.add(backButton);
        backPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 500));

        // create the panel for the header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(PRIMARY_COLOR);
        JLabel headerLabel = new JLabel("Play");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        headerPanel.setMinimumSize(new Dimension(200, 70));  // Prevent UI structure from breaking down
        headerPanel.setPreferredSize(new Dimension(200, 300));
        
        // add the panels to the frame
        Box container = Box.createVerticalBox(); // create a vertical box container
        container.setBorder(new EmptyBorder(30, 0, 5, 0)); // add a top margin to the container
        container.setBackground(PRIMARY_COLOR);
        container.add(headerPanel);
        container.add(Box.createVerticalStrut(30)); // add a vertical gap between the header and the buttons
        container.add(buttonPanel);
        container.add(backPanel);
        container.setPreferredSize(new Dimension(Integer.MAX_VALUE, 260));
        add(container);

        // set the size and center the frame
        setSize(1280, 720);
        setMinimumSize(new Dimension(640, 360)); // Prevent UI structure from breaking down
        setLocationRelativeTo(null);

    }
    /**
     * ActionPerformed method. This responds to the buttons interacted with by the user.
     * 
     * This method gets called when the user clicks one of the buttons, either 'Start Game', or 'Back'.
     * If the button clicked is 'Start Game', A new game starts via the GameEngine class.
     * If the button clicked is the back button, the user goes back to the main menu screen.
     * 
     * @param e The ActionEvent object that carries information regarding how the button has been interacted with
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            
            // Initialize gamefactory using AbstractFactory
            GameObjectFactory gameObjectFactory = new AbstractFactory();

            // Setup window for game
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("SFU Escape");
            GameEngine gameEngine = new GameEngine(gameObjectFactory);
            window.add(gameEngine);
        
            window.pack();
        
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        
            gameEngine.startGameThread();

            dispose();
        } else if (e.getSource() == backButton) {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            dispose();
        }
    }
}
