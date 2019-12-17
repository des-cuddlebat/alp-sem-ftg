package net.cuddlebat.ftg;

import java.util.Scanner;
import java.util.function.Function;

import net.cuddlebat.ftg.game.Game;
import net.cuddlebat.ftg.game.GameState;
import net.cuddlebat.ftg.gen.GameGenerator;
import net.cuddlebat.ftg.opts.GameOpts;
import net.cuddlebat.ftg.opts.GameOptsParser;
import net.cuddlebat.ftg.tile.Tile;
import net.cuddlebat.ftg.tile.Tiles;
import net.cuddlebat.ftg.util.Console;

public class Main
{
    public static void main(String[] args) throws Throwable
    {
        GameOptsParser parser = new GameOptsParser();
        GameOpts opts = parser.parseOpts(args);
        
        if(opts.isHelp())
        {
            Function<Tile, String> getter = opts.isLegacy() ? Tile::getCharString : Tile::getRenderString;
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
        
        GameGenerator generator = new GameGenerator(
            opts.getWidth(), opts.getHeight(), opts.getSeed(), opts.getMoons(), opts.getTreeRate());
        Game game = generator.generate();
        
        if(opts.isLegacy())
        {
            runtimeLegacy(game);
        }
        else
        {
            runtimeClassic(game);            
        }
    }

    private static void runtimeClassic(Game game)
    {

        game.getGrid().renderFull();
        Scanner sc = new Scanner(System.in);
        String in;
        while(game.getState() == GameState.RUNNING && !"exit".equals(in = sc.next()))
        {
            processInput(game, in, true);
            Console.prevLine();
            Console.clearLine();
        }
        
        if(game.getState() == GameState.LOST)
            System.out.println("Looks like you fell asleep.. zzz.. better luck next time!");
        if(game.getState() == GameState.WON)
            System.out.println("You have found your gift! Woohoo!");
    }

    private static void runtimeLegacy(Game game)
    {
        game.getGrid().renderLegacy();
        Scanner sc = new Scanner(System.in);
        String in;
        while(game.getState() == GameState.RUNNING && !"exit".equals(in = sc.next()))
        {
            processInput(game, in, false);
            game.getGrid().renderLegacy();
        }
        
        if(game.getState() == GameState.LOST)
            System.out.println("Looks like you fell asleep.. zzz.. better luck next time!");
        if(game.getState() == GameState.WON)
            System.out.println("You have found your gift! Woohoo!");
    }
    
    private static void processInput(Game game, String in, boolean render)
    {
        if(in.length() == 0)
            return;
        char rowChar = in.toLowerCase().charAt(0);
        int row = rowChar - 97; // don't ask
        int col;
        try
        {
            col = Integer.valueOf(in.substring(1)) - 1;
        }
        catch(NumberFormatException x)
        {
            return;
        }
        if(!game.getGrid().isMove(col, row))
            return;
        game.getGrid().uncover(game, col, row);
        if(render)
            game.getGrid().renderAt(col, row);
    }
}