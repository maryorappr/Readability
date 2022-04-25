package driver;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static readability.FileReformatting.formatFile;

public class FormatFile
{
    public static void main(String[] args) throws IOException
    {
        //TODO redo this area and move files around
        File file = new File("src/files/readabilityTextTESTONLY.txt");
        File formattedFile = new File("src/files/formattedReadabilityTESTONLY.txt");
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number: ");
        int num = input.nextInt();
        
        formatFile(file, formattedFile, num, true, false);
        System.out.println("Formatted");
    }
}
