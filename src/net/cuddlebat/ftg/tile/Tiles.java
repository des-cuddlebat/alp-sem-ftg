package net.cuddlebat.ftg.tile;

import java.util.HashMap;
import java.util.Map;

import net.cuddlebat.ftg.game.GameState;
import net.cuddlebat.ftg.util.Console;

public final class Tiles
{
    private static Map<Character, Tile> ALL = new HashMap<>();
    
    // core gameplay
    public static final Tile EMPTY   = register(new Tile(' ', ""));
    public static final Tile GIFT    = register(new Tile('X', Console.YELLOW + Console.BG_RED))
        .withOnUncover((game, x, y) -> game.setState(GameState.WON));
    public static final Tile STAR    = register(new Tile('*', Console.LIGHT_YELLOW));
    public static final Tile MOON    = register(new Tile('C', Console.LIGHT_WHITE))
        .withOnUncover((game, x, y) -> game.setState(GameState.LOST));
    // rectangle hints
    public static final Tile DECOR   = register(new Tile('o', Console.RED));
    public static final Tile SNOWMAN = register(new Tile('8', Console.LIGHT_WHITE));
    public static final Tile CANDLE  = register(new Tile('\'', Console.WHITE + Console.BG_MAGENTA));
    // tree hints
    public static final Tile TREE_L  = register(new Tile('<', Console.GREEN));
    public static final Tile TREE_R  = register(new Tile('>', Console.GREEN));
    public static final Tile TREE_U  = register(new Tile('A', Console.GREEN));
    public static final Tile TREE_D  = register(new Tile('V', Console.GREEN));
    
    private Tiles()
    {
    }
    
    public static Tile register(Tile tile)
    {
        ALL.put(tile.getChar(), tile);
        return tile;
    }
    
    public static Tile get(char character)
    {
        return ALL.get(character);
    }
}
