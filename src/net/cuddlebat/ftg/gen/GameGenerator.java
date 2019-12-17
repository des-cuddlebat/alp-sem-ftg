package net.cuddlebat.ftg.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.cuddlebat.ftg.game.Game;
import net.cuddlebat.ftg.game.GameGrid;
import net.cuddlebat.ftg.tile.Tile;
import net.cuddlebat.ftg.tile.Tiles;

public class GameGenerator
{
    int width;
    int height;
    long seed;
    int moons;
    int treeRate;
    
    public GameGenerator(int width, int height, long seed, int moons, int treeRate)
    {
        super();
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.moons = moons;
        this.treeRate = treeRate;
    }
    
    public Game generate()
    {
        Random rand = new Random(seed);
        GameBuilder builder = new GameBuilder(new GameGrid(width, height));
        
        generateGift(builder, rand);
        generateRectangleClues(builder, rand, Tiles.DECOR  );
        generateRectangleClues(builder, rand, Tiles.SNOWMAN);
        generateRectangleClues(builder, rand, Tiles.CANDLE );
        generateMoons(builder, rand);
        generateTrees(builder, rand);
        generateEmpty(builder);
        
        return new Game(builder.build());
    }
    
    private void generateGift(GameBuilder builder, Random rand)
    {
        int low = height / 4;
        int high = 3 * height / 4;
        int left = width / 4;
        int right = 3 * width / 4;
        
        int x = width / 2;
        int y = height / 2;
        
        while(x > left && x <= right && y > low && y <= high)
        {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        }
        
        builder.placeGift(x, y);
    }
    
    private void generateRectangleClues(GameBuilder builder, Random rand, Tile tile)
    {
        for (int attempt = 0; attempt < 10; attempt++)
        {
            int left = rand.nextInt(builder.getGiftX() + 1);
            int right = builder.getGiftX() + rand.nextInt(width - builder.getGiftX());
            int top = rand.nextInt(builder.getGiftY() + 1);
            int bot = builder.getGiftY() + rand.nextInt(height - builder.getGiftY());
            
            if(left == right || top == bot)
                continue;
            
            boolean canPlace = true;
            canPlace &= builder.is(left , top, '\u0000');
            canPlace &= builder.is(right, top, '\u0000');
            canPlace &= builder.is(left , bot, '\u0000');
            canPlace &= builder.is(right, bot, '\u0000');
            
            if(canPlace)
            {
                builder.place(left , top, tile);
                builder.place(right, top, tile);
                builder.place(left , bot, tile);
                builder.place(right, bot, tile);
                return;
            }
        }
    }
    
    private void generateMoons(GameBuilder builder, Random rand)
    {
        List<Long> potential = new ArrayList<>();
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                if(builder.isNullArea(x, y))
                    potential.add(pack(x, y));
            }            
        }
        
        for(int i = 0; i < moons; i++)
        {
            if(potential.size() == 0)
                break;
            int index = rand.nextInt(potential.size());
            long pos = potential.get(index);
            potential.remove(index);
            int x = unpackX(pos);
            int y = unpackY(pos);
            builder.placeMoon(x, y);
            if(potential.contains(pack(x+1, y+1)))
                potential.remove(pack(x+1, y+1));
            if(potential.contains(pack(x+1, y-1)))
                potential.remove(pack(x+1, y-1));
            if(potential.contains(pack(x-1, y+1)))
                potential.remove(pack(x-1, y+1));
            if(potential.contains(pack(x-1, y-1)))
                potential.remove(pack(x-1, y-1));
        }
    }
    
    private void generateTrees(GameBuilder builder, Random rand)
    {
        List<Tile> potential = new ArrayList<Tile>();
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                if(builder.is(x, y, '\u0000') && rand.nextInt(treeRate) == 0)
                {
                    if (builder.isGiftBelow(x, y))
                        potential.add(Tiles.TREE_U);
                    if (builder.isGiftAbove(x, y))
                        potential.add(Tiles.TREE_D);
                    if (builder.isGiftLeft(x, y))
                        potential.add(Tiles.TREE_R);
                    if (builder.isGiftRight(x, y))
                        potential.add(Tiles.TREE_L);
                    builder.place(x, y, potential.get(rand.nextInt(potential.size())));
                    potential.clear();
                }
            }            
        }
    }
    
    private void generateEmpty(GameBuilder builder)
    {
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                if(builder.is(x, y, '\u0000'))
                    builder.place(x, y, Tiles.EMPTY);
            }            
        }
    }
    
    private long pack(int x, int y)
    {
        return ((long)x << 32) + y;
    }
    
    private int unpackX(long packed)
    {
        return (int)(packed >> 32);
    }
    
    private int unpackY(long packed)
    {
        return (int)packed;
    }
}
