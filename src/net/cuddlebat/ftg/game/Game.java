package net.cuddlebat.ftg.game;

public class Game
{
    private GameGrid grid;
    private GameState state;
    
    public Game(GameGrid grid)
    {
        this.grid = grid;
        state = GameState.RUNNING;
    }
    
    public GameState getState()
    {
        return state;
    }
    
    public void setState(GameState state)
    {
        this.state = state;
    }
    
    public GameGrid getGrid()
    {
        return grid;
    }
}
