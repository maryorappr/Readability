package readability;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ColemanLiauIndex
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/files/formattedReadabilityTESTONLY.txt");
        System.out.println(calculateColemanLiauIndex(file));
    }
    
    public static double calculateColemanLiauIndex(File book) throws IOException
    {
        double characters = 0;
        double words = 0;
        double sentences = 0;
        double index;
        
        //count sentences, words, and characters
        Scanner fileReader = new Scanner(book);
        while (fileReader.hasNext())
        {
            String sentence = fileReader.nextLine();
            sentences++;
            
            String[] wordsArray = sentence.split(" ");
            for (String word : wordsArray)
            {
                words++;
                String[] charArray = word.split("");
                for (String character : charArray)
                    if (character.matches("[\\p{L}]"))
                        characters++;
            }
        }
        fileReader.close();
        
        //calculate index
        if (words != 0)
        {
            final double L = 100 * (characters / words);
            final double S = 100 * (sentences / words);
            
            index = (0.0588 * L) - (0.296 * S) - 15.8;
            int temp = (int) Math.round(index * 100);
            index = temp / 100.0;
            return index;
        }
        else return 0;
    }
}
