package net.cuddlebat.ftg.game;

import net.cuddlebat.ftg.tile.Tile;
import net.cuddlebat.ftg.tile.Tiles;
import net.cuddlebat.ftg.util.Console;
import net.cuddlebat.ftg.util.ConsoleGameUtils;

public class GameGrid
{
    private char[][] board;
    private boolean[][] uncovered;
    
    public GameGrid(int width, int height)
    {
        board = new char[height][width];
        uncovered = new boolean[height][width];
        board[height/2][width/2] = ' ';
        uncovered[height/2][width/2] = true;
    }
    
    public GameGrid(char[][] board)
    {
        this.board = board;
        uncovered = new boolean[getHeight()][getWidth()];
        uncovered[getHeight()/2][getWidth()/2] = true;
    }
    
    public void renderFull()
    {
        Console.clear();
        
        char[] rowInd = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        System.out.print("  ");
        for(int i = 0; i < getWidth(); i++)
            System.out.printf(" %2d ", i+1);
        System.out.println();
        
        for(int y = 0; y < getHeight(); y++)
        {
            System.out.print("  ");
            for(int x = 0; x < getWidth(); x++)
                System.out.print("+---");
            System.out.println("+");
            System.out.print(rowInd[y] + " ");
            for(int x = 0; x < getWidth(); x++)
                System.out.print("| " + getRenderStringAt(x, y) + " ");
            System.out.print("|");
            System.out.println(" " + rowInd[y]);
        }
        System.out.print("  ");
        for(int x = 0; x < getWidth(); x++)
            System.out.print("+---");
        System.out.println("+");

        System.out.print("  ");
        for(int i = 0; i < getWidth(); i++)
            System.out.printf(" %2d ", i+1);
        System.out.println();
    }
    
    public void renderLegacy()
    {
        System.out.println();
        
        char[] rowInd = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        ConsoleGameUtils.drawColIndices(getWidth());
        
        for(int y = 0; y < getHeight(); y++)
        {
            ConsoleGameUtils.drawHorbar(getWidth());
            System.out.print(rowInd[y] + " ");
            for(int x = 0; x < getWidth(); x++)
                System.out.print("| " + (uncovered[y][x] ? board[y][x] : '?') + " ");
            System.out.print("|");
            System.out.println(" " + rowInd[y]);
        }
        ConsoleGameUtils.drawHorbar(getWidth());
        ConsoleGameUtils.drawColIndices(getWidth());
    }
    
    public void renderAt(int x, int y)
    {
        Console.storeCursorPos();
        final int xx = ConsoleGameUtils.consoleXFor(x);
        final int yy = ConsoleGameUtils.consoleYFor(y);
        Console.setCursorPos(yy, xx);
        System.out.println(getRenderStringAt(x, y));
        Console.restoreCursorPos();
    }
    
    private String getRenderStringAt(int x, int y)
    {
        if(uncovered[y][x])
            return Tiles.get(board[y][x]).getRenderString();
        else
            return Console.LIGHT_CYAN + "?" + Console.RESET;
    }
    
    public void setAt(int x, int y, Tile tile)
    {
        board[y][x] = tile.getChar();
    }
    
    public Tile getAt(int x, int y)
    {
        return Tiles.get(board[y][x]);
    }
    
    public char getRawAt(int x, int y)
    {
        return board[y][x];
    }
    
    public int getWidth()
    {
        return board[0].length;
    }
    
    public int getHeight()
    {
        return board.length;
    }
    
    public boolean isMove(int x, int y)
    {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight() && !uncovered[y][x];
    }

    public void uncover(Game game, int x, int y)
    {
        uncovered[y][x] = true;
        Tiles.get(board[y][x]).onUncover(game, x, y);
    }
}
