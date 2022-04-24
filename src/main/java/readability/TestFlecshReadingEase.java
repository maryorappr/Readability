package readability;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static readability.ReadingIndexCalculation.calculateFleschReadingEase;

public class TestFlecshReadingEase
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/files/formattedReadabilityTESTONLY.txt");
        System.out.println("index: " + flecshReadingEase(file));
        Passage passage = new Passage(file);
        System.out.println("index: " + calculateFleschReadingEase(passage));
    }
    
    public static double flecshReadingEase(File book) throws IOException
    {
        double index;
        double sentenceCount = 0;
        double wordCount = 0;
        double syllableCount = 0;
    
        Scanner fileReader = new Scanner(book);
        while (fileReader.hasNext())
        {
            String sentence = fileReader.nextLine();
            sentenceCount++;
    
            String[] wordsArray = sentence.split(" ");
            for (String word : wordsArray)
            {
                wordCount++;
                word = word.replaceAll("[aeiouyAEIOUY]", "#");
                word = word.replaceAll("#{2,}", "#");
                for (int i = 0; i < word.length(); i++)
                {
                    if (word.charAt(i) == '#')
                        syllableCount++;
                }
            }
        }
        // this is not the formula i have no clue why this doesnt work unless u add 14 this is so weird
//        System.out.println((0.39 * (wordCount / sentenceCount) + (11.8 * (syllableCount / wordCount)) - 15.59));
        index = 206.835 - (1.015 * (wordCount / sentenceCount)) - (84.6 * (syllableCount / wordCount)) + 14;
        return index;
    }
}
