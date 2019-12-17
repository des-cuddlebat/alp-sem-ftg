package net.cuddlebat.ftg.util;

public class ConsoleGameUtils
{
    public static void drawHorbar(int cols)
    {
        System.out.print("  ");
        for(int x = 0; x < cols; x++)
            System.out.print("+---");
        System.out.println("+");
    }
    
    public static void drawColIndices(int cols)
    {
        System.out.print("  ");
        for(int i = 0; i < cols; i++)
            System.out.printf(" %2d ", i+1);
        System.out.println();
    }
    
    public static int consoleXFor(int col)
    {
        return 4 * col + 5;
    }
    
    public static int consoleYFor(int row)
    {
        return 2 * row + 3;
    }
}
