package net.cuddlebat.ftg.util;

public final class Console
{
    private Console()
    {
    }
    
    public static final String RESET = buildRenderMode(0);
    
    public static final String BLACK = buildRenderMode(30);
    public static final String RED = buildRenderMode(31);
    public static final String GREEN = buildRenderMode(32);
    public static final String YELLOW = buildRenderMode(33);
    public static final String BLUE = buildRenderMode(34);
    public static final String MAGENTA = buildRenderMode(35);
    public static final String CYAN = buildRenderMode(36);
    public static final String WHITE = buildRenderMode(37);
    public static final String LIGHT_BLACK = buildRenderMode(90);
    public static final String LIGHT_RED = buildRenderMode(91);
    public static final String LIGHT_GREEN = buildRenderMode(92);
    public static final String LIGHT_YELLOW = buildRenderMode(93);
    public static final String LIGHT_BLUE = buildRenderMode(94);
    public static final String LIGHT_MAGENTA = buildRenderMode(95);
    public static final String LIGHT_CYAN = buildRenderMode(96);
    public static final String LIGHT_WHITE = buildRenderMode(97);
    
    public static final String BG_BLACK = buildRenderMode(40);
    public static final String BG_RED = buildRenderMode(41);
    public static final String BG_GREEN = buildRenderMode(42);
    public static final String BG_YELLOW = buildRenderMode(43);
    public static final String BG_BLUE = buildRenderMode(44);
    public static final String BG_MAGENTA = buildRenderMode(45);
    public static final String BG_CYAN = buildRenderMode(46);
    public static final String BG_WHITE = buildRenderMode(47);
    public static final String BG_LIGHT_BLACK = buildRenderMode(100);
    public static final String BG_LIGHT_RED = buildRenderMode(101);
    public static final String BG_LIGHT_GREEN = buildRenderMode(102);
    public static final String BG_LIGHT_YELLOW = buildRenderMode(103);
    public static final String BG_LIGHT_BLUE = buildRenderMode(104);
    public static final String BG_LIGHT_MAGENTA = buildRenderMode(105);
    public static final String BG_LIGHT_CYAN = buildRenderMode(106);
    public static final String BG_LIGHT_WHITE = buildRenderMode(107);
    
    public static final void setCursorPos(int row, int col)
    {
        System.out.print("\u001B[" + row + ";" + col + "f");
    }
    
    public static final void prevLine()
    {
        System.out.print("\u001B[F");
    }
    
    public static final void clear()
    {
        System.out.print("\u001B[H\u001B[2J");
    }
    
    public static final void clearLine()
    {
        System.out.print("\u001B[2K");
    }
    
    public static final void storeCursorPos()
    {
        System.out.print("\u001B[s");
    }
    
    public static final void restoreCursorPos()
    {
        System.out.print("\u001B[u");
    }
    
    private static final String buildRenderMode(int n)
    {
        return "\u001B[" + n + "m";
    }
}
