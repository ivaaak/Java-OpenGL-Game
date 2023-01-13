package Infrastructure;

import Startup.JumpAndRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Calculations
{
// Saves the high score in a text file and compares it to the current score
    public static void highScoreCalculate()
    {
        // get the file, which saves the high score and read it
        String dir = System.getProperty("user.dir");
        String paths = dir + "\\high_score.txt";

        try {
            File high_score = new File(paths);
            Scanner sc = new Scanner(high_score);
            while(sc.hasNextLine())
            {
                String data= sc.nextLine();
                JumpAndRun.HighScore = Integer.parseInt(data);
                // save the new high score
                if(JumpAndRun.Score > JumpAndRun.HighScore)
                {
                    JumpAndRun.HighScore = JumpAndRun.Score;
                }
            }
            sc.close();
        } catch(FileNotFoundException e) {
            System.out.println("An error occurred. Can't read from file");
            e.printStackTrace();
        }
        //write the new value for the high score
        try {
            FileWriter myWriter = new FileWriter(paths);
            myWriter.write(String.valueOf(JumpAndRun.HighScore));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred. Can't write to file");
            e.printStackTrace();
        }
    }
}
