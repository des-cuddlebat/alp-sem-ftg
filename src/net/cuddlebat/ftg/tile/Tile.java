package net.cuddlebat.ftg.tile;

import net.cuddlebat.ftg.game.Game;
import net.cuddlebat.ftg.util.Console;

public class Tile
{
    private char character;
    private String format;
    private OnUncover onUncoverFunc = (game, x, y) -> {};
    
    public Tile(char character, String format)
    {
        this.character = character;
        this.format = format;
    }
    
    public Tile withOnUncover(OnUncover onUncover)
    {
        onUncoverFunc = onUncover;
        return this;
    }
    
    public void onUncover(Game game, int x, int y)
    {
        onUncoverFunc.run(game, x, y);
    }

    public char getChar()
    {
        return character;
    }

    public String getCharString()
    {
        return String.valueOf(character);
    }
    
    public String getRenderString()
    {
        return format + character + Console.RESET;
    }
    
    @FunctionalInterface
    public static interface OnUncover
    {
        public void run(Game game, int x, int y);
    }
}
