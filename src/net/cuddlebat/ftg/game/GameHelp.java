package net.cuddlebat.ftg.game;

import java.io.IOException;
import java.util.function.Function;

import net.cuddlebat.ftg.opts.GameOptsParser;
import net.cuddlebat.ftg.tile.Tile;
import net.cuddlebat.ftg.tile.Tiles;

public final class GameHelp
{
    private GameHelp()
    {
    }
    
    public static void show(GameOptsParser parser, boolean legacy) throws IOException
    {
        Function<Tile, String> getter = legacy ? Tile::getCharString : Tile::getRenderString;
        System.out.println("Rules:");
        System.out.println("  The objective is to find the gift before you fall asleep.");
        System.out.println("  Input coordinates in letter number format: E7, a3.");
        System.out.println();
        System.out.println("Tiles");
        System.out.print("  " + getter.apply(Tiles.GIFT) + " - gift: ");
        System.out.println("uncovering it means victory! Look for clues to find it.");
        System.out.print("  " + getter.apply(Tiles.STAR) + " - star: ");
        System.out.println("four of these surround a moon; Another moon can replace.");
        System.out.print("  " + getter.apply(Tiles.MOON) + " - moon: ");
        System.out.println("uncovering a moon mean game over. You fell asleep.");
        System.out.println("      Two moons will never be diagonal neighbors.");
        System.out.print("  "
            + getter.apply(Tiles.DECOR) + " "
            + getter.apply(Tiles.SNOWMAN) + " "
            + getter.apply(Tiles.CANDLE) + " - ");
        System.out.println("decor, snowman, candle: if found, always in");
        System.out.println("          quadruplets forming a rectangle around the gift.");
        System.out.print("  "
            + getter.apply(Tiles.TREE_U) + " "
            + getter.apply(Tiles.TREE_D) + " "
            + getter.apply(Tiles.TREE_R) + " "
            + getter.apply(Tiles.TREE_L) + " - ");
        System.out.println("christmas tree: gifts are always below it!");
        System.out.println("            Whichever way it is facing.");
        System.out.println();
        parser.printHelpOn(System.out);
        return;
    }
}
