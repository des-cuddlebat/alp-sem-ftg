package net.cuddlebat.ftg.opts;

public class GameOpts
{
    private int width;
    private int height;
    private long seed;
    private int moons;
    
    private int treeRate;
    private boolean legacy;
    private boolean help;
    private boolean wasd;
    
    public GameOpts(int width, int height, long seed, int moons, int treeRate, boolean legacy, boolean help, boolean wasd)
    {
        super();
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.moons = moons;
        this.treeRate = treeRate;
        this.legacy = legacy;
        this.help = help;
        this.wasd = wasd;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public long getSeed()
    {
        return seed;
    }

    public int getMoons()
    {
        return moons;
    }

    public int getTreeRate()
    {
        return treeRate;
    }

    public boolean isLegacy()
    {
        return legacy;
    }

    public boolean isHelp()
    {
        return help;
    }

    public boolean isWasd()
    {
        return wasd;
    }
}
