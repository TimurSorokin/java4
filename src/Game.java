import javax.swing.*;
import java.io.*;

import java.util.Arrays;
import java.util.Scanner;


public class Game {
    int[][] table;
    boolean p1win=false;
    boolean p2win=false;

    public Game(String option) {

        if(option=="new")
        {
            table = new int[6][7]; //6 - filas | 7 - columnas

            for (int i = 0; i < table.length; i++) {
                for (int k = 0; k < table[i].length; k++) {
                    table[i][k] = 2;
                }
            }
        }
        else if(option=="load")
        {
            table = new int[6][7];
                readSave();
        }
    }

    public void addToTable(int columna, int value) {

        for (int fila = table.length - 1; fila >= 0; fila--) {

            if (table[fila][columna] == 2) {
                table[fila][columna] = value;
                break;
            } else if (table[0][columna] != 2) {
                JOptionPane.showMessageDialog(null, "No space available", "Info: " + "Attention", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }

    }
    public boolean checkWin()
    {
        getPlayersPosition();
        if(p1win)
        {
            JOptionPane.showMessageDialog(null, "Player 1 won the game", "Info: " + "Attention", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        else if(p2win)
        {
            JOptionPane.showMessageDialog(null, "Player 2 won the game", "Info: " + "Attention", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    public void getPlayersPosition() {
        for (int i = 0; i < table.length; i++) {
            for (int k = 0; k < table[i].length; k++) {
                if (table[i][k] == 1) {
                    //System.out.println("Player 1 at: " + i + k);
                    if(hWin(i,k,1))
                    {
                        System.out.println("hWin");
                        p1win=true;
                        break;
                    }
                    else if(vWin(i,k,1))
                    {
                        System.out.println("vWin");

                        p1win=true;
                        break;
                    }
                    else if(dWin_1(i,k,1))
                    {
                        System.out.println("dWin1");

                        p1win=true;
                    }
                    else if(dWin_2(i,k,1))
                    {
                        System.out.println("dWin2");

                        p1win=true;
                    }

                } else if (table[i][k] == 0) {
                    //System.out.println("Player 2 at: " + i + k);
                    if(hWin(i,k,0)) {
                        p2win = true;
                        break;
                    }
                    else if(vWin(i,k,0))
                    {
                        p2win=true;
                        break;
                    }
                    else if(dWin_1(i,k,0))
                    {
                        System.out.println("dWin1");

                        p2win=true;
                    }
                    else if(dWin_2(i,k,0))
                    {
                        System.out.println("dWin2");

                        p2win=true;
                    }

                }

            }
        }
    }
    public boolean hWin(int i, int k, int playerID)
    {
        int l=(table[i].length-1)-k;
        int count=0;
        boolean check=(l>=3);
        boolean status=(count>3);
        if(check) {
            // System.out.println("value of l= "+l);
            for (int z = k; z < table[i].length; z++) {
                if (table[i][z] == playerID) {
                    count++;
                    status=(count>3);
                } else {
                    break;
                }
            }
        }
        // System.out.println(status);
        return status;
    }
    public boolean vWin(int i, int k, int playerID)
    {
        int l=(table.length-1)-i;
        int count=0;
        boolean check=(l>=3);
        boolean status=(count>3);
        if(check) {
            // System.out.println("value of l= "+l);
            for (int z = i; z < table.length; z++) {
                if (table[z][k] == playerID) {
                    count++;
                    status=(count>3);
                } else {
                    break;
                }
            }
        }
        // System.out.println(status);
        return status;
    }
    public boolean dWin_1 (int i, int k, int playerID)
    {
        int count=0;
        boolean status=(count>3);

        for(int y=i;y>0;y--){
            for(int x=k;x<table[i].length;x++){
                if(table[y][x]==playerID)
                {
                    count++;
                    status=(count>3);
                    y--;
                }
                else{
                    count=0;
                    break;
                }
            }
        }

        return status;
    }
    public boolean dWin_2 (int i, int k, int playerID)
    {
        int count=0;
        boolean status=(count>3);

        for(int y=i;y>0;y--){
            for(int x=k;x>=0;x--){
                if(table[y][x]==playerID)
                {
                    count++;
                    status=(count>3);
                    y--;
                }
                else{
                    count=0;
                    break;
                }
            }
        }
        return status;
    }
    public void createSave() {
        deletePreviousSave();
        try {
            FileWriter writer = new FileWriter("src/save.txt", true);
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    writer.write(table[i][j]+" ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Game saved!");
    }

    public void deletePreviousSave(){
        File f = new File("src/save.txt");
        if(f.exists() && !f.isDirectory()){
            f.delete();
            System.out.println("Save Deleted");
        }
    }

public void readSave() {
        System.out.println("Reading file");
    Scanner sc = null;
    try {
        sc = new Scanner(new BufferedReader(new FileReader("src/save.txt")));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    while(sc.hasNextLine()) {
        for (int i=0; i<table.length; i++) {
            String[] line = sc.nextLine().trim().split(" ");
            for (int j=0; j<line.length; j++) {
               table[i][j] = Integer.parseInt(line[j]);
            }
        }
    }
    //System.out.println(Arrays.deepToString(table));
}


}
