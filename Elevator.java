import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**Elevator Project
 *@author Ronard Nyongkah
 *@version Spring 2025
 *CSci1130
*/

public class Elevator extends JFrame implements ActionListener, ItemListener{
    private final int DISPLAY_WIDTH = 800;
    private final int DISPLAY_HEIGHT = 900;

    private JPanel choicePanel;
    JButton submitButton;
    JComboBox choiceBox;
    
    int num, choice = 1, currentFloor = 1, nextFloor = 0, floorDifference;
    int locX, yChange = 0, elevatorY;

    final int LIMIT = 10;
    final int NUM_WINDOWS = 10;
    final int WINDOW_HEIGHT = 65;
    final int WINDOW_WIDTH = 40;
    final int WINDOW_GAP = 15;
    final int X_OFFSET = 0;
    final int COUNT_START = 1;

    int floorHeight = WINDOW_HEIGHT + WINDOW_GAP;

    private DisplayPanel display;
    public static void main(String[] args){
        Elevator frame = new Elevator();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.initializeVariables();
        frame.createGUI();
        frame.pack();
        frame.setVisible(true);
    }

    public void createGUI(){
        setUpCombo();
        setUpGUI();
        
    }

    public void setUpGUI(){
        Container window = getContentPane();
        display = new DisplayPanel();

        window.add(display, BorderLayout.CENTER);
    }

// creating my elevator change panel at the bottom of the window
    public void setUpCombo(){
        choicePanel = new JPanel(new FlowLayout());
        choiceBox = new JComboBox();

        for (int i = 0; i < LIMIT; i++){
            choiceBox.addItem(String.valueOf(i+1));
        }
        choiceBox.addItemListener(this);
        choicePanel.add(choiceBox);

        submitButton = new JButton("Change Level");
        submitButton.addActionListener(this);
        choicePanel.add(submitButton);

        getContentPane().add(choicePanel, BorderLayout.SOUTH);
        
    }

    // Variable creation
    public void initializeVariables(){
        num = COUNT_START;
        locX = X_OFFSET;
    }
    // changes to the next floor
    public void UpdateElevatorY() {
        floorDifference = nextFloor - currentFloor; 
        elevatorY = (LIMIT - nextFloor) * floorHeight; 
        currentFloor = nextFloor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextFloor = choice;
        UpdateElevatorY();
        
        display.repaint();
    }
    // creating elevator animation 
    public void inMotion(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(locX,elevatorY,WINDOW_WIDTH,WINDOW_HEIGHT);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            choice = Integer.parseInt((String) choiceBox.getSelectedItem());
        }
    }
    
    class DisplayPanel extends JPanel {
        DisplayPanel() {
            setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            this.setBackground(Color.gray);
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d=(Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            drawFloor(g2d);
            inMotion(g2d);

        }

        public void drawFloor(Graphics2D g2d) {
        // creating building 
        int buildingWidth = NUM_WINDOWS * WINDOW_WIDTH + (NUM_WINDOWS - 1) * WINDOW_GAP;
        int buildingHeight = NUM_WINDOWS * WINDOW_HEIGHT + (NUM_WINDOWS - 1) * WINDOW_GAP;

        // Draw background (the building)
        g2d.setColor(new Color(150, 50, 50)); 
        g2d.fillRect(locX - 5, 5, buildingWidth + 10, buildingHeight + 10);

            // creating my floors
            for (int j = 0; j < NUM_WINDOWS; j++) {
                //creating my windows
                for (int i = 0; i < NUM_WINDOWS; i++) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(locX + (i * (WINDOW_WIDTH + WINDOW_GAP)), (j * (WINDOW_HEIGHT + WINDOW_GAP)), WINDOW_WIDTH, WINDOW_HEIGHT);
                }
                
            }

        }
    }
    

} 