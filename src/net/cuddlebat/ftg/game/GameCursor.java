package net.cuddlebat.ftg.game;

import net.cuddlebat.ftg.util.Console;
import net.cuddlebat.ftg.util.ConsoleGameUtils;

public class GameCursor
{
    private int x;
    private int y;
    private int width;
    private int height;
    
    public GameCursor(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveUp()
    {
        y = Math.max(0, y - 1);
    }
    
    public void moveLeft()
    {
        x = Math.max(0, x - 1);
    }

    public void moveDown()
    {
        y = Math.min(height - 1, y + 1);
    }
    
    public void moveRight()
    {
        x = Math.min(width - 1, x + 1);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    
    public void render()
    {
        Console.storeCursorPos();
        final int xx = ConsoleGameUtils.consoleXFor(x);
        final int yy = ConsoleGameUtils.consoleYFor(y);
        Console.setCursorPos(yy, xx - 1);
        System.out.print("[");
        Console.setCursorPos(yy, xx + 1);
        System.out.print("]");
        Console.restoreCursorPos();
    }
    
    public void unrender()
    {
        Console.storeCursorPos();
        final int xx = ConsoleGameUtils.consoleXFor(x);
        final int yy = ConsoleGameUtils.consoleYFor(y);
        Console.setCursorPos(yy, xx - 1);
        System.out.print(" ");
        Console.setCursorPos(yy, xx + 1);
        System.out.print(" ");
        Console.restoreCursorPos();
    }
}
