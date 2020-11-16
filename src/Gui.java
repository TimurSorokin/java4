import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui {
    int x=800;
    int y=600;
    String option;
Game game;
    public Gui() {
welcomeFrame();
    }
    public void welcomeFrame()
    {
        JFrame frame = new JFrame("Practica 5 | Timur Sorokin");
        JPanel menu = new JPanel();
        JPanel game = new JPanel();
        JButton newGame = new JButton("New Game");
        JButton loadGame = new JButton("Load Game");
        JButton exitGame = new JButton("Exit Game");
        JLabel img = new JLabel(getIcon());
        frame.setDefaultCloseOperation(3);
        menu.add(newGame);
        menu.add(loadGame);
        menu.add(exitGame);
        menu.add(img);
        frame.add(game);
        frame.add(menu);
        frame.setSize(x, y);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                img.setVisible(false);
               frame.setVisible(false);
                option="new";
               newGameFrame();
            }
        });

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                img.setVisible(false);
                frame.setVisible(false);
                option="load";
                newGameFrame();
            }
        });

        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    int count;


    public void newGameFrame()
    {
        //System.out.println("option: " + option);
        count=0;
       game = new Game(option);
        JFrame frame = new JFrame("Practica 5 | Timur Sorokin");
        frame.setDefaultCloseOperation(3);
       JPanel buttonsPanel = new JPanel();//Contains the buttons
        JPanel visualArray;
        visualArray = new getVisualArray();
        JLabel turn = new JLabel("Turno: ");
        buttonsPanel.add(turn);
        //buttons array and loop
        JButton buttonList[] = new JButton[7];
        for(int i = 0; i < 7; i++) {
            int columna = i;
            JButton button = new JButton(String.valueOf(new StringBuilder("col: "+ (columna+1))));
            buttonList[i] = button;
            buttonList[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    count++;
                    int valor = count%2;
                    game.addToTable(columna,valor);
                    frame.repaint();
                    turn.setText("Turno: " + count);
                    if(game.checkWin())
                    {
                        JOptionPane.showMessageDialog(null, "WIN CONDITION REACHED! \n Resetting the table", "Info: " + "Attention", JOptionPane.INFORMATION_MESSAGE);
                        option="new";
                        game = new Game(option);
                        count=0;
                        turn.setText("Turno: " + count);
                        frame.repaint();

                    }
                }
            });
            buttonsPanel.add(button);
        }
        JButton saveGame = new JButton("Save Game");
        JButton backMenu = new JButton("Back to Menu");
        backMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                welcomeFrame();
            }
        });
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.createSave();
            }
        });
        buttonsPanel.add(saveGame);
        buttonsPanel.add(backMenu);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
       frame.getContentPane().add(visualArray,BorderLayout.CENTER);
        frame.setSize(x,y);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
public class getVisualArray extends JPanel{
        public void paint(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            int xg=0;
            int yg=0;
            int w=45;
            int h=45;
            int[][] table = game.table;
            for(int fila=0;fila<table.length;fila++)
            {
                for(int columna=0;columna<table[fila].length;columna++)
                {
                    if(table[fila][columna] == 0)
                    {
                        g2d.setColor(Color.red);
                    }
                    else if(table[fila][columna]==1)
                    {
                        g2d.setColor(Color.blue);
                    }
                   else
                    {
                        g2d.setColor(Color.black);
                    }
                    g2d.fillOval(columna*50,fila*50,w,h);
                }
            }

        }
    }
    //Imagen para welcomeFrame(Enunciado)
    public ImageIcon getIcon()
    {
        ImageIcon icon= null;
        try {
            BufferedImage img = ImageIO.read(new File("src/picture.jpg"));
           icon = new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }
}
