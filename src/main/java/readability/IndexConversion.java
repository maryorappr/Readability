package readability;

public class IndexConversion
{
    public static int simple(double num)
    {
        if (num > 17)
            return 17;
        else if (num < 1)
            return 1;
        else return (int) num;
    }
    
    public static int colemanGrade(double num)
    {
        return simple(num);
    }
    
    public static int fleschEaseGrade(double num)
    {
        if (num >= 130)
            return 1;
        else if (num >= 120)
            return 2;
        else if (num >= 110)
            return 3;
        else if (num >= 100)
            return 4;
        else if (num >= 90)
            return 5;
        else if (num >= 80)
            return 6;
        else if (num >= 70)
            return 7;
        else if (num >= 65)
            return 8;
        else if (num >= 60)
            return 9;
        else if (num > 57)
            return 10;
        else if (num > 54)
            return 11;
        else if (num >= 50)
            return 12;
        else if (num >= 40)
            return 13;
        else if (num >= 30)
            return 14;
        else if (num >= 20)
            return 15;
        else if (num >= 10)
            return 16;
        else return 17;
    }
    
    public static int fleschKincaidGrade(double num)
    {
        return simple(num);
    }
    
    public static int automatedGrade(double num)
    {
        return simple(num);
    }
    
    public static int smogGrade(double num)
    {
        if (num <= 2)
            return 4;
        else if (num <= 6)
            return 5;
        else if (num <= 12)
            return 6;
        else if (num <= 20)
            return 7;
        else if (num <= 31)
            return 8;
        else if (num <= 42)
            return 9;
        else if (num <= 56)
            return 10;
        else if (num <= 72)
            return 1;
        else if (num <= 90)
            return 12;
        else if (num <= 110)
            return 13;
        else if (num <= 132)
            return 14;
        else if (num <= 156)
            return 15;
        else if (num <= 182)
            return 16;
        else return 17;
    }
    
    public static int daleChallGrade(double num)
    {
        return simple(num);
    }
    
    private IndexConversion()
    {
    
    }
}
