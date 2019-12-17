package net.cuddlebat.ftg.opts;

import java.util.Arrays;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

public class GameOptsParser extends OptionParser
{
    private static final String[] WIDTH = { "width", "w" };
    private static final String[] HEIGHT = { "height", "h" };
    private static final String[] SEED = { "seed", "s" };
    private static final String[] MOON_COUNT = { "moon_count", "m" };
    private static final String TREE_RATE = "tree_rate";
    private static final String LEGACY = "legacy";
    private static final String HELP = "help";
    
    private OptionSpec<Integer> width;
    private OptionSpec<Integer> height;
    private OptionSpec<Long> seed;
    private OptionSpec<Integer> moons;
    
    private OptionSpec<Integer> treeRate;
    private OptionSpec<Void> legacy;
    private OptionSpec<Void> help;
    
    public GameOptsParser()
    {
        width = acceptsAll(Arrays.asList(WIDTH), "Width of the play area in tiles.")
        .withRequiredArg().ofType(Integer.class);
        height = acceptsAll(Arrays.asList(HEIGHT), "Height of the play area in tiles.")
            .withRequiredArg().ofType(Integer.class);
        seed = acceptsAll(Arrays.asList(SEED), "Seed to use for the random generator.")
            .withRequiredArg().ofType(Long.class);
        moons = acceptsAll(Arrays.asList(MOON_COUNT), "The number of moon tiles to attempt to place.")
            .withRequiredArg().ofType(Integer.class);

        treeRate = accepts(TREE_RATE, "Empty tiles have 1 in tree_rate chance to become christmas trees.")
            .withRequiredArg().ofType(Integer.class);
        legacy = accepts(LEGACY, "Play in highly compatible mode without escape sequences.");
        help = accepts(HELP, "Display help information.").forHelp();
    }
    
    public GameOpts parseOpts(String... args)
    {
        OptionSet opts = this.parse(args);
        
        int widthVal = opts.valueOfOptional(width).orElse(12);
        int heightVal = opts.valueOfOptional(height).orElse(8);
        long seedVal = opts.valueOfOptional(seed).orElse(System.currentTimeMillis());
        int moonsVal = opts.valueOfOptional(moons).orElse(widthVal * heightVal / 12);
        
        int treeRateVal = opts.valueOfOptional(treeRate).orElse(8);
        boolean legacyVal = opts.has(legacy);
        boolean helpVal = opts.has(help);
        
        return new GameOpts(widthVal, heightVal, seedVal, moonsVal, treeRateVal, legacyVal, helpVal);
    }
}
