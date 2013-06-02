package pt.up.fe.pt.lpoo.bombermen;

public final class Constants
{
    // Window
    public static final String WINDOW_TITLE = "Bombermen";
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 480;
    public static final boolean USE_GL20 = true;

    // Control Pad
    public static final int DEFAULT_PAD_WIDTH = 200;
    public static final int DEFAULT_PAD_HEIGHT = 200;
    public static final float DEFAULT_PAD_BUTTON_SIZE_MULT = 2.f / 3.f;

    // Game
    public static final int INIT_NUM_MAX_BOMBS = 1;
    public static final int INIT_BOMB_RADIUS = 2;
    public static final int DEFAULT_BOMB_TIMER = 3; // 3 seconds
    public static final int DEFAULT_BOMB_STRENGTH = 1; // 1 hp damage
    public static final int CELL_SIZE = 36; // px

    // Maps
    public static final String DEFAULT_MAP_FILE_CHARSET = "US-ASCII";
    public static final float WALL_CHANCE = 0.6f;
    public static final float INIT_PLAYER_SPEED = 10;

    private Constants()
    {
    }
}
