package readability;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadingIndexCalculation
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/files/formattedReadabilityTESTONLY.txt");
    
        Passage passage = new Passage(file);
        System.out.println(calculateColemanLiau(file));
        System.out.println(calculateColemanLiau(passage));
    }
    
    public static double calculateColemanLiau(File book) throws IOException
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
                    if (character.matches("\\p{L}"))
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
    
    public static double calculateColemanLiau(Passage book)
    {
        if (book.getWordCount() != 0)
        {
            final double L = 100 * (book.getCharacterCount() / book.getWordCount());
            final double S = 100 * (book.getSentenceCount() / book.getWordCount());
    
            double index = (0.0588 * L) - (0.296 * S) - 15.8;
            int temp = (int) Math.round(index * 100);
            index = temp / 100.0;
            return index;
        } else return 0;
    }
    
    public static double calculateFleschReadingEase(Passage book)
    {
        if (book.getWordCount() != 0)
        {
            final double ASL = book.getWordCount() / book.getSentenceCount();
            final double ASW = book.getSyllableCount() / book.getWordCount();
            
            
            double index = 206.835 - (1.015 * ASL) - (84.6 * ASW) + 14;
            int temp = (int) Math.round(index * 100);
            index = temp / 100.0;
            return index;
        } else return 0;
    }
    
    public static double calculateFleschGradeLevel(Passage book)
    {
        if (book.getWordCount() != 0)
        {
            final double ASL = book.getWordCount() / book.getSentenceCount();
            final double ASW = book.getSyllableCount() / book.getWordCount();
            
            
            double index = (0.39 * ASL) + (11.8 * ASW) - 15.59;
            int temp = (int) Math.round(index * 100);
            index = temp / 100.0;
            return index;
        } else return 0;
    }
    
    public static double calculateAutomated(Passage book)
    {
        double letters = book.getCharacterCount();
        double words = book.getWordCount();
        double sentences = book.getSentenceCount();
        
        double index = (4.71 *(letters/words)) + (0.5 * (words / sentences)) - 21.43;
        int temp = (int) Math.round(index * 100);
        index = temp / 100.0;
        return index;
    }
    
}
