package net.cuddlebat.ftg.gen;

import net.cuddlebat.ftg.game.GameGrid;
import net.cuddlebat.ftg.tile.Tile;
import net.cuddlebat.ftg.tile.Tiles;

public class GameBuilder
{
    private int giftX;
    private int giftY;
    private GameGrid grid;
    
    public GameBuilder(GameGrid grid)
    {
        super();
        this.grid = grid;
    }
    
    public void placeGift(int x, int y)
    {
        giftX = x;
        giftY = y;
        grid.setAt(x, y, Tiles.GIFT);
    }
    
    public void placeMoon(int x, int y)
    {
        final char NULL = '\u0000';
        
        grid.setAt(x, y, Tiles.MOON);
        if (isAndInside(x+1, y  , NULL))
            grid.setAt(x+1, y  , Tiles.STAR);
        if (isAndInside(x-1, y  , NULL))
            grid.setAt(x-1, y  , Tiles.STAR);
        if (isAndInside(x  , y+1, NULL))
            grid.setAt(x  , y+1, Tiles.STAR);
        if (isAndInside(x  , y-1, NULL))
            grid.setAt(x  , y-1, Tiles.STAR);
    }
    
    public void place(int x, int y, Tile tile)
    {
        grid.setAt(x, y, tile);
    }
    
    public boolean isNullArea(int x, int y)
    {
        final char NULL = '\u0000';
        return isOrOutside(x  , y  , NULL)
            && isOrOutside(x+1, y  , NULL)
            && isOrOutside(x-1, y  , NULL)
            && isOrOutside(x  , y+1, NULL)
            && isOrOutside(x  , y-1, NULL);
    }
    
    public boolean isOrOutside(int x, int y, char c)
    {
        return x < 0 || y < 0 || x >= grid.getWidth() || y >= grid.getHeight() || is(x, y, c);
    }
    
    public boolean isAndInside(int x, int y, char c)
    {
        return x >= 0 && y >= 0 && x < grid.getWidth() && y < grid.getHeight() && is(x, y, c);
    }
    
    public boolean is(int x, int y, char c)
    {
        return grid.getRawAt(x, y) == c;
    }
    
    public boolean isGiftLeft(int x, int y)
    {
        return giftX < x;
    }
    
    public boolean isGiftRight(int x, int y)
    {
        return giftX > x;
    }
    
    public boolean isGiftAbove(int x, int y)
    {
        return giftY < y;
    }
    
    public boolean isGiftBelow(int x, int y)
    {
        return giftY > y;
    }

    public int getGiftX()
    {
        return giftX;
    }

    public int getGiftY()
    {
        return giftY;
    }

    public void setAt(int x, int y, Tile tile)
    {
        grid.setAt(x, y, tile);
    }
    
    public GameGrid build()
    {
        return grid;
    }
}
