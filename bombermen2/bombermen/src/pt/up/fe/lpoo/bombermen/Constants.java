package pt.up.fe.lpoo.bombermen;

/**
 * The Class Constants.
 */
public final class Constants
{
    // Window
    /** The Constant WINDOW_TITLE. */
    public static final String WINDOW_TITLE = "Bombermen";

    /** The Constant DEFAULT_WIDTH. */
    public static final int DEFAULT_WIDTH = 800;

    /** The Constant DEFAULT_HEIGHT. */
    public static final int DEFAULT_HEIGHT = 480;

    /** The Constant USE_GL20. */
    public static final boolean USE_GL20 = true;

    /** The Constant WINDOW_XMARGIN. */
    public static final int WINDOW_XMARGIN = 200;

    /** The Constant MAP_MAX_LATERAL_SIZE. */
    public static final int MAP_MAX_LATERAL_SIZE = 47;

    // Control Pad
    /** The Constant DEFAULT_PAD_WIDTH. */
    public static final int DEFAULT_PAD_WIDTH = 200;

    /** The Constant DEFAULT_PAD_HEIGHT. */
    public static final int DEFAULT_PAD_HEIGHT = 200;

    /** The Constant DEFAULT_PAD_BUTTON_SIZE_MULT. */
    public static final float DEFAULT_PAD_BUTTON_SIZE_MULT = 2.f / 3.f;

    /** The Constant SHOW_PAD. */
    public static final boolean SHOW_PAD = false;

    // Game
    /** The Constant INIT_NUM_MAX_BOMBS. */
    public static final int INIT_NUM_MAX_BOMBS = 1;

    /** The Constant INIT_BOMB_RADIUS. */
    public static final int INIT_BOMB_RADIUS = 2;

    /** The Constant BOMB_TIMER. */
    public static final int BOMB_TIMER = 3000; // 3 seconds

    /** The Constant EXPLOSION_TIMER. */
    public static final int EXPLOSION_TIMER = 600; // milliseconds

    /** The Constant DEFAULT_BOMB_STRENGTH. */
    public static final int DEFAULT_BOMB_STRENGTH = 1; // 1 hp damage

    /** The Constant CELL_SIZE. */
    public static final int CELL_SIZE = 36; // px

    /** The Constant POWER_UP_SPAWN_CHANCE. */
    public static final float POWER_UP_SPAWN_CHANCE = 0.8f;

    // Maps
    /** The Constant DEFAULT_MAP_FILE_CHARSET. */
    public static final String DEFAULT_MAP_FILE_CHARSET = "US-ASCII";

    /** The Constant WALL_CHANCE. */
    public static final float WALL_CHANCE = 0.5f;

    /** The Constant INIT_PLAYER_SPEED. */
    public static final float INIT_PLAYER_SPEED = 108f / 1000f;

    // Sizes
    /** The Constant PLAYER_WIDTH. */
    public static final float PLAYER_WIDTH = 18.f * CELL_SIZE * 0.75f / 26f;

    /** The Constant PLAYER_HEIGHT. */
    public static final float PLAYER_HEIGHT = CELL_SIZE * 0.75f;

    /** The Constant PLAYER_BOUNDING_WIDTH. */
    public static final float PLAYER_BOUNDING_WIDTH = PLAYER_WIDTH * 0.8f;

    /** The Constant PLAYER_BOUNDING_HEIGHT. */
    public static final float PLAYER_BOUNDING_HEIGHT = PLAYER_HEIGHT * 0.8f;

    /** The Constant BOMB_WIDTH. */
    public static final float BOMB_WIDTH = 0.9f * CELL_SIZE;

    /** The Constant BOMB_HEIGHT. */
    public static final float BOMB_HEIGHT = BOMB_WIDTH;

    /** The Constant EXPLOSION_WIDTH. */
    public static final float EXPLOSION_WIDTH = CELL_SIZE;

    /** The Constant EXPLOSION_HEIGHT. */
    public static final float EXPLOSION_HEIGHT = EXPLOSION_WIDTH;

    /** The Constant POWER_UP_WIDTH. */
    public static final float POWER_UP_WIDTH = 0.9f * CELL_SIZE;

    /** The Constant POWER_UP_HEIGHT. */
    public static final float POWER_UP_HEIGHT = POWER_UP_WIDTH;

    /** The Constant WALL_WIDTH. */
    public static final float WALL_WIDTH = CELL_SIZE;

    /** The Constant WALL_HEIGHT. */
    public static final float WALL_HEIGHT = WALL_WIDTH;

    /** The Constant WALL_BOUNDING_WIDTH. */
    public static final float WALL_BOUNDING_WIDTH = WALL_WIDTH;

    /** The Constant WALL_BOUNDING_HEIGHT. */
    public static final float WALL_BOUNDING_HEIGHT = WALL_HEIGHT;

    // PowerUp types
    /** The Constant POWER_UP_TYPE_BOMB_UP. */
    public static final int POWER_UP_TYPE_BOMB_UP = 0; // increase amount of bombs by 1

    /** The Constant POWER_UP_TYPE_SKATE. */
    public static final int POWER_UP_TYPE_SKATE = 1; // increase speed by 1

    /** The Constant POWER_UP_TYPE_KICK. */
    public static final int POWER_UP_TYPE_KICK = 2; // send bomb sliding across the stage until it collides with a wall/player/bomb

    /** The Constant POWER_UP_TYPE_PUNCH. */
    public static final int POWER_UP_TYPE_PUNCH = 3; // knock them away (out of screen or screen wrap to the other side)

    /** The Constant POWER_UP_TYPE_FIRE. */
    public static final int POWER_UP_TYPE_FIRE = 4; // increase bomb radius

    /** The Constant POWER_UP_TYPE_SKULL. */
    public static final int POWER_UP_TYPE_SKULL = 5; // debuffs (bomberman.wikia.com/wiki/Skull)

    /** The Constant POWER_UP_TYPE_FULL_FIRE. */
    public static final int POWER_UP_TYPE_FULL_FIRE = 6; // biggest possible explosion radius

    /** The Constant NUMBER_OF_POWER_UP_TYPES. */
    public static final int NUMBER_OF_POWER_UP_TYPES = 7;

    // Scores
    /** The Constant PLAYER_KILL_SCORE. */
    public static final int PLAYER_KILL_SCORE = 10;

    /** The Constant WALL_DESTROY_SCORE. */
    public static final int WALL_DESTROY_SCORE = 1;

    /** The Constant POWER_UP_SCORE. */
    public static final int POWER_UP_SCORE = Math.round((1 - POWER_UP_SPAWN_CHANCE) * 10);

    /** The Constant INIT_PLAYER_SCORE. */
    public static final int INIT_PLAYER_SCORE = 0;

    // Screens
    /** The Constant MAX_SERVERS_STORED. */
    public static final int MAX_SERVERS_STORED = 7;

    /**
     * Private constructor
     */
    private Constants()
    {
    }
}
