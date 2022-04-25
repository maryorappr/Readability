package driver;

import readability.Passage;

import java.io.File;
import java.io.IOException;

import static readability.ReadingIndexCalculation.calculateColemanLiau;
import static readability.ReadingIndexCalculation.calculateFleschGradeLevel;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        //TODO fix the main method
        File file = new File("src/files/formattedReadabilityTESTONLY.txt");
        
        Passage passage = new Passage(file);
        System.out.println(calculateFleschGradeLevel(passage));
        
        System.out.println(calculateColemanLiau(file));
        System.out.println(calculateColemanLiau(passage));
    }
}
