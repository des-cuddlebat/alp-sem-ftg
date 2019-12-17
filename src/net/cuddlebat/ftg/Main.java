package net.cuddlebat.ftg;

import java.util.Scanner;

import net.cuddlebat.ftg.game.Game;
import net.cuddlebat.ftg.game.GameCursor;
import net.cuddlebat.ftg.game.GameHelp;
import net.cuddlebat.ftg.game.GameState;
import net.cuddlebat.ftg.gen.GameGenerator;
import net.cuddlebat.ftg.opts.GameOpts;
import net.cuddlebat.ftg.opts.GameOptsParser;
import net.cuddlebat.ftg.util.Console;

public class Main
{
    public static void main(String[] args) throws Throwable
    {
        GameOptsParser parser = new GameOptsParser();
        GameOpts opts = parser.parseOpts(args);
        
        if(opts.isHelp())
        {
            GameHelp.show(parser, opts.isLegacy());
            return;
        }
        
        GameGenerator generator = new GameGenerator(
            opts.getWidth(), opts.getHeight(), opts.getSeed(), opts.getMoons(), opts.getTreeRate());
        Game game = generator.generate();
        
        if(opts.isLegacy())
        {
            runtimeLegacy(game);
        }
        else if(opts.isWasd()) // WASD mode is intentionally incompatible with legacy.
        {
            runtimeWasd(game);
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
        while(game.getState() == GameState.RUNNING && !"exit".equals(in = sc.nextLine()))
        {
            processCoordInput(game, in, true);
            Console.prevLine();
            Console.clearLine();
        }
        
        if(game.getState() == GameState.LOST)
            System.out.println("Looks like you fell asleep.. zzz.. better luck next time!");
        if(game.getState() == GameState.WON)
            System.out.println("You have found your gift! Woohoo!");
    }

    private static void runtimeWasd(Game game)
    {

        game.getGrid().renderFull();
        Scanner sc = new Scanner(System.in);
        GameCursor cursor = new GameCursor(
            game.getGrid().getWidth() / 2,
            game.getGrid().getHeight() / 2,
            game.getGrid().getWidth(),
            game.getGrid().getHeight());
        String in;
        cursor.render();
        while(game.getState() == GameState.RUNNING && !"exit".equals(in = sc.nextLine()))
        {
            processWasdInput(cursor, game, in);
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
        while(game.getState() == GameState.RUNNING && !"exit".equals(in = sc.nextLine()))
        {
            processCoordInput(game, in, false);
            game.getGrid().renderLegacy();
        }
        
        if(game.getState() == GameState.LOST)
            System.out.println("Looks like you fell asleep.. zzz.. better luck next time!");
        if(game.getState() == GameState.WON)
            System.out.println("You have found your gift! Woohoo!");
    }
    
    private static void processCoordInput(Game game, String in, boolean render)
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
    
    private static void processWasdInput(GameCursor cursor, Game game, String in)
    {
        if(in.length() == 0)
        {
            if(game.getGrid().isMove(cursor.getX(), cursor.getY()))
            {
                game.getGrid().uncover(game, cursor.getX(), cursor.getY());
                game.getGrid().renderAt(cursor.getX(), cursor.getY());
            }
        }
        else
        {
            cursor.unrender();
            switch(in.toUpperCase().charAt(0))
            {
            case 'W':
                cursor.moveUp();
                break;
            case 'A':
                cursor.moveLeft();
                break;
            case 'S':
                cursor.moveDown();
                break;
            case 'D':
                cursor.moveRight();
                break;
            }
            cursor.render();
        }
    }
}