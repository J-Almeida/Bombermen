package ui.gui.graphical.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Architect;
import logic.Direction;
import logic.Dragon;
import logic.Eagle;
import logic.Eagle.EagleState;
import logic.Hero;
import logic.InanimatedObject;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;
import logic.Unit;
import model.Position;
import utils.Key;

/**
 * The main class.
 */
public class Game extends JPanel implements KeyListener, MazeGame
{
    /** The configuration. */
    private Configuration CONFIG = new Configuration(Messages.getString("Game.CONFIGURATION_FILE")); //$NON-NLS-1$

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The window width. */
    private final int WINDOW_WIDTH = 600;

    /** The window height. */
    private final int WINDOW_HEIGHT = 600;

    /** The maze size. */
    private int MAZE_SIZE = 10;

    /** The cell width. */
    private int CELL_WIDTH = WINDOW_WIDTH / MAZE_SIZE;

    /** The cell height. */
    private int CELL_HEIGHT = WINDOW_HEIGHT / MAZE_SIZE;

    /**
     * Update CELL_WIDTH and CELL_HEIGHT
     */
    private void UpdateMazeSizes()
    {
        int min = Math.min(getWidth(), getHeight());
        CELL_WIDTH = min / MAZE_SIZE;
        CELL_HEIGHT = min / MAZE_SIZE;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        final Game m = new Game();

        final JFrame frame = new JFrame(Messages.getString("Game.WINDOW_TITLE")); //$NON-NLS-1$
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(m.WINDOW_WIDTH, m.WINDOW_HEIGHT);
        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);

        frame.setLayout(new BorderLayout());

        final JButton newGameButton = new JButton(Messages.getString("Game.NEW_GAME_BUTTON")); //$NON-NLS-1$
        final JButton saveButton = new JButton(Messages.getString("Game.SAVE_LOAD_BUTTON")); //$NON-NLS-1$
        final JButton exitButton = new JButton(Messages.getString("Game.QUIT_BUTTON")); //$NON-NLS-1$
        final JButton settingsButton = new JButton(Messages.getString("Game.SETTINGS_BUTTON")); //$NON-NLS-1$

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                m.CONFIG.TrySaveToFile();
                System.exit(0);
            }
        });

        newGameButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int result = JOptionPane.showConfirmDialog(frame, Messages.getString("Game.NEW_GAME_CONFIRMATION")); //$NON-NLS-1$

                if (result == JOptionPane.YES_OPTION)
                    m.NewGame();
            }
        });

        exitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        settingsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SettingsDialog d = new SettingsDialog(frame, m.CONFIG);
                d.setVisible(true);
                m.CONFIG = d.GetNewConfiguration();
            }
        });

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SaveLoadDialog d = new SaveLoadDialog(frame, m, false);
                d.setVisible(true);
            }
        });

        newGameButton.addFocusListener(new FocusListener()
        {
            @Override public void focusLost(FocusEvent e) { m.requestFocusInWindow(); }
            @Override public void focusGained(FocusEvent e) { m.requestFocusInWindow(); }
        });

        saveButton.addFocusListener(new FocusListener()
        {
            @Override public void focusLost(FocusEvent e) { m.requestFocusInWindow(); }
            @Override public void focusGained(FocusEvent e) { m.requestFocusInWindow(); }
        });

        settingsButton.addFocusListener(new FocusListener()
        {
            @Override public void focusLost(FocusEvent e) { m.requestFocusInWindow(); }
            @Override public void focusGained(FocusEvent e) { m.requestFocusInWindow(); }
        });

        frame.getContentPane().add(m, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(1, 4));
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

        southPanel.add(newGameButton);
        southPanel.add(saveButton);
        southPanel.add(settingsButton);
        southPanel.add(exitButton);

        frame.setVisible(true);
    }

    /** The maze. */
    private Maze _maze;

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.MazeGame#GetMaze()
     */
    @Override
    public Maze GetMaze() { return _maze; }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.MazeGame#SetMaze(logic.Maze)
     */
    @Override
    public void SetMaze(Maze maze) { _maze = maze; }

    /** The sprites. */
    private final Map<String, TiledImage> _sprites;

    /** The unit sprites. */
    private final HashMap<Integer, AnimatedSprite> _unitSprites = new HashMap<Integer, AnimatedSprite>();

    /** Is game finished? */
    private boolean _gameFinished = false;

    /** Is hero moving? If so, block inputs. */
    private boolean _heroMoving = false;

    public void ResetGame()
    {
    	ArrayList<Dragon> drgs = _maze.FindDragons();
    	CONFIG.SetMazeSize(_maze.GetWidth());
    	CONFIG.SetNumberOfDragons(drgs.size());
    	if (drgs.size() > 0)
    	{
    		if (drgs.get(0).CanMove())
    			CONFIG.SetDragonMode(Dragon.Behaviour.RandomMovement);
    		else if (drgs.get(0).CanSleep())
    			CONFIG.SetDragonMode(Dragon.Behaviour.Sleepy);
    		else
    			CONFIG.SetDragonMode(Dragon.Behaviour.Idle);
    	}

        MAZE_SIZE = CONFIG.GetMazeSize();

    	// Initialize sprites
        _unitSprites.clear();
        for (Unit u : _maze.GetLivingObjects())
        {
            if (u.IsHero())
                _unitSprites.put(u.GetId(), new HeroSprite(u.ToHero(), _sprites.get(Messages.getString("Game.HERO_SPRITE_NAME")))); //$NON-NLS-1$
            else if (u.IsDragon())
                _unitSprites.put(u.GetId(), new DragonSprite(u.ToDragon(), _sprites.get(Messages.getString("Game.DRAGON_SPRITE_NAME")))); //$NON-NLS-1$
            else if (u.IsEagle())
                _unitSprites.put(u.GetId(), new EagleSprite(u.ToEagle(), _sprites.get(Messages.getString("Game.EAGLE_SPRITE_NAME")))); //$NON-NLS-1$
            else if (u.IsSword())
                _unitSprites.put(u.GetId(), new SwordSprite(u.ToSword(), _sprites.get(Messages.getString("Game.SWORD_SPRITE_NAME")))); //$NON-NLS-1$
        }

        repaint();
    }

    /**
     * Create a new game (calls repaint())
     */
    public void NewGame()
    {
        _gameFinished = false;
        _swordJustPicked = false;
        _eagleJustDied = false;
        _eagleJustArrived = false;
        _dragonsJustDied.clear();

        Architect architect = new Architect();
        MazeGenerator mg = new RandomMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(CONFIG.GetMazeSize(), CONFIG.GetNumberOfDragons(), CONFIG.GetDragonMode());
        MAZE_SIZE = CONFIG.GetMazeSize();

        SetMaze(architect.GetMaze());

        // Initialize sprites
        _unitSprites.clear();
        for (Unit u : _maze.GetLivingObjects())
        {
            if (u.IsHero())
                _unitSprites.put(u.GetId(), new HeroSprite(u.ToHero(), _sprites.get(Messages.getString("Game.HERO_SPRITE_NAME")))); //$NON-NLS-1$
            else if (u.IsDragon())
                _unitSprites.put(u.GetId(), new DragonSprite(u.ToDragon(), _sprites.get(Messages.getString("Game.DRAGON_SPRITE_NAME")))); //$NON-NLS-1$
            else if (u.IsEagle())
                _unitSprites.put(u.GetId(), new EagleSprite(u.ToEagle(), _sprites.get(Messages.getString("Game.EAGLE_SPRITE_NAME")))); //$NON-NLS-1$
            else if (u.IsSword())
                _unitSprites.put(u.GetId(), new SwordSprite(u.ToSword(), _sprites.get(Messages.getString("Game.SWORD_SPRITE_NAME")))); //$NON-NLS-1$
        }

        repaint();
    }

    /**
     * Instantiates a new game.
     */
    public Game()
    {
        addKeyListener(this);
        setFocusable(true);

        // Load sprites
        _sprites = new HashMap<String, TiledImage>();
        _sprites.put(Messages.getString("Game.DRAGON_SPRITE_NAME"),     new TiledImage(Messages.getString("Game.DRAGON_SPRITE_FILE_NAME"), 128, 128)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.DYING_DRAGON_SPRITE_NAME"), new TiledImage(Messages.getString("Game.DYING_DRAGON_SPRITE_FILE_NAME"), 128, 128)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.EAGLE_SPRITE_NAME"),      new TiledImage(Messages.getString("Game.EAGLE_SPRITE_FILE_NAME"),  64,  64)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.HERO_SPRITE_NAME"),       new TiledImage(Messages.getString("Game.HERO_SPRITE_FILE_NAME"),   96,  96)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.SWORD_SPRITE_NAME"),      new TiledImage(Messages.getString("Game.SWORD_SPRITE_FILE_NAME"),  96,  96)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.PATH_SPRITE_NAME"),      new TiledImage(Messages.getString("Game.PATH_SPRITE_FILE_NAME"),        96,  96)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.WALL_SPRITE_NAME"),      new TiledImage(Messages.getString("Game.WALL_SPRITE_FILE_NAME"),        96,  96)); //$NON-NLS-1$ //$NON-NLS-2$
        _sprites.put(Messages.getString("Game.EXIT_SPRITE_NAME"), new TiledImage(Messages.getString("Game.EXIT_SPRITE_FILE_NAME"),   96,  96)); //$NON-NLS-1$ //$NON-NLS-2$

        NewGame();

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Update(17);
                repaint();
            }
        };
        new Timer(17, taskPerformer).start();
    }

    private boolean _eagleJustDied = false;
    private boolean _swordJustPicked = false;
    private final Set<Integer> _dragonsJustDied = new HashSet<Integer>();
    private boolean _eagleJustArrived = false;

    /**
     * Update.
     *
     * @param diff the difference in milliseconds between this frame and previous
     */
    public void Update(int diff)
    {
        GetMaze().Update(diff);

        for (AnimatedSprite as : _unitSprites.values())
            as.Update(diff);

        Hero h = _maze.FindHero();
        if (h != null)
        {
            if (((HeroSprite)_unitSprites.get(h.GetId())).IsWalking())
                _heroMoving = true;
            else
                _heroMoving = false;
        }

        ArrayList<AnimatedSprite> toRemove = new ArrayList<AnimatedSprite>();

        for (AnimatedSprite as : _unitSprites.values())
            if (!as.IsAlive())
                toRemove.add(as);

        for (AnimatedSprite as : toRemove)
        {
            if (as instanceof DragonSprite)
            {
                Dragon d = ((DragonSprite)as).GetDragon();
                _unitSprites.remove(as.GetUnitId());
                _unitSprites.put(d.GetId(), new DyingDragonSprite(d,_sprites.get(Messages.getString("Game.DYING_DRAGON_SPRITE_NAME")))); //$NON-NLS-1$
            }
            else
            {
                _unitSprites.remove(as.GetUnitId());
            }
        }

        // eagle sounds
        Eagle eagle = _maze.FindEagle();
        if (eagle == null)
        {
            if (!_eagleJustDied)
            {
                PlaySound("eagle_scream.wav", 0);
                _eagleJustDied = true;
            }
        }
        else
        {
            if (eagle.GetState() == EagleState.FollowingHeroWithSword)
                if (!_eagleJustArrived)
                {
                    PlaySound("wing_flap.wav", 0);
                    _eagleJustArrived = true;
                }
        }

        // sword sounds
        if (_maze.FindSword() == null)
        {
            if (!_swordJustPicked)
            {
                PlaySound("pick_sword.wav", 0);
                _swordJustPicked = true;
            }
        }

        // dragon sounds
        ArrayList<Dragon> dragons = _maze.FindDragons();
        for (Dragon d : dragons)
            _dragonsJustDied.add(d.GetId());

        ArrayList<Integer> toRemoveDrags = new ArrayList<Integer>();
        for (Integer i : _dragonsJustDied)
        {
            boolean found = false;
            for (Dragon d2 : dragons)
            {
                if (i == d2.GetId() && d2.IsAlive())
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                PlaySound("dragon_roar.wav", 0);
                toRemoveDrags.add(i);
            }
        }

        for (Integer i : toRemoveDrags)
            _dragonsJustDied.remove(i);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        UpdateMazeSizes();
        requestFocusInWindow();

        g.setColor(new Color(0, 100, 0)); // dark green
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int x = 0; x < GetMaze().GetWidth(); ++x)
        {
            for (int y = 0; y < GetMaze().GetHeight(); ++y)
            {
                InanimatedObject c = GetMaze().GetGrid().GetCellT(x, y);
                if (c.IsPath())
                    DrawCellAt(g, _sprites.get(Messages.getString("Game.PATH_SPRITE_NAME")).GetTile(0, 0), x, y); //$NON-NLS-1$
                else if (c.IsWall())
                    DrawCellAt(g, _sprites.get(Messages.getString("Game.WALL_SPRITE_NAME")).GetTile(0, 0), x, y); //$NON-NLS-1$
                else if (c.IsExitPortal())
                    DrawCellAt(g, _sprites.get(Messages.getString("Game.EXIT_SPRITE_NAME")).GetTile(0, 0), x, y); //$NON-NLS-1$
            }
        }

        // draw dying dragons before everything else
        for (AnimatedSprite as : _unitSprites.values())
            if (as instanceof DyingDragonSprite)
                DrawCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));

        for (AnimatedSprite as : _unitSprites.values())
        {
            if (as instanceof EagleSprite)
            {
                Eagle e = _maze.FindEagle();
                if (!e.IsFlying() && !e.IsFollowingHero())
                    DrawCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
                else if (e.IsFollowingHero())
                    DrawHalfCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT), false);
                else
                    DrawCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
            }
            else if (as instanceof HeroSprite)
            {
                 if (GetMaze().FindEagle() != null && GetMaze().FindEagle().ToEagle().IsFollowingHero())
                     DrawHalfCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT), true);
                 else
                     DrawCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
            }
            else if (!(as instanceof DyingDragonSprite))
                DrawCellAt(g, as.GetCurrentImage(), as.GetPosition(), as.GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
        }

    }

    /**
     * Draw cell at position
     *
     * @param g the graphics
     * @param img the image to draw
     * @param pos the position
     * @param dPos the delta position
     */
    public void DrawCellAt(Graphics g, Image img, Position pos, Position dPos) { DrawCellAt(g, img, pos.X, pos.Y, dPos.X, dPos.Y); }

    /**
     * Draw cell at position
     *
     * @param g the graphics
     * @param img the image to draw
     * @param pos the position
     */
    public void DrawCellAt(Graphics g, Image img, Position pos) { DrawCellAt(g, img, pos.X, pos.Y, 0, 0); }

    /**
     * Draw cell at position
     *
     * @param g the graphics
     * @param img the image to draw
     * @param x the x position
     * @param y the y position
     */
    public void DrawCellAt(Graphics g, Image img, int x, int y) { DrawCellAt(g, img, x, y, 0, 0); }

    /**
     * Draw cell at position
     *
     * @param g the graphics
     * @param img the image to draw
     * @param pos the position
     * @param dX the x delta position
     * @param dY the y delta position
     */
    public void DrawCellAt(Graphics g, Image img, Position pos, int dX, int dY) { DrawCellAt(g, img, pos.X, pos.Y, dX, dY); }

    /**
     * Draw cell at position
     *
     * @param g the graphics
     * @param img the image to draw
     * @param x the x position
     * @param y the y position
     * @param dX the x delta position
     * @param dY the y delta position
     */
    public void DrawCellAt(Graphics g, Image img, int x, int y, int dX, int dY)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH + dX, (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT + dY, CELL_WIDTH, CELL_HEIGHT, null);
    }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param pos the position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, 0, 0, left); }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param pos the position
     * @param dPos the delta position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, Position dPos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, dPos.X, dPos.Y, left); }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param pos the position
     * @param dX the x delta position
     * @param dY the y delta position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, int dX, int dY, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, dX, dY, left); }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param x the x position
     * @param y the y position
     * @param dX the x delta position
     * @param dY the y delta position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, int x, int y, int dX, int dY, boolean left)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH + (left ? -(CELL_WIDTH / 4) : CELL_WIDTH / 4) + dX, (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT + dY, CELL_WIDTH, CELL_HEIGHT, null);
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) { }

    public static synchronized void PlaySound(final String fileName, final int timeOut)
    {
        new Thread(new Runnable()
        {
            @Override
			public void run()
            {
                try
                {
                    InputStream audioSrc = java.lang.ClassLoader.getSystemResourceAsStream("ui/gui/graphical/resources/" + fileName);
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);

                    Clip clip = AudioSystem.getClip();

                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                    clip.open(inputStream);
                    clip.start();
                    if (timeOut != 0)
                    {
                        Thread.sleep(timeOut);
                        clip.stop();
                    }
                }
                catch (Exception e)
                {
                    System.err.println(e);
                }
            }
        }).start();
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (_gameFinished)
            return;

        if (!_heroMoving)
        {
            Key k = null;
            Action a = CONFIG.GetAction(e.getKeyCode());
            if (a != null)
            {
                switch (a)
                {
                case HERO_UP:
                    k = Key.UP;
                    break;
                case HERO_DOWN:
                    k = Key.DOWN;
                    break;
                case HERO_RIGHT:
                    k = Key.RIGHT;
                    break;
                case HERO_LEFT:
                    k = Key.LEFT;
                    break;
                case SEND_EAGLE:
                    GetMaze().SendEagleToSword();
                    break;
                default:
                    return;
                }
            }

            if (k != null)
            {
                GetMaze().MoveHero(Direction.FromKey(k));
                PlaySound("footsteps.wav", 500);
            }
            Update(0);
        }

        if (GetMaze().IsFinished())
        {
            _gameFinished = true;

            if (GetMaze().FindHero() != null)
            {
                PlaySound("win.wav", 0);
                JOptionPane.showMessageDialog(this, Messages.getString("Game.WINNING_MESSAGE")); //$NON-NLS-1$
            }
            else
            {
                PlaySound("lose.wav", 0);
                JOptionPane.showMessageDialog(this, Messages.getString("Game.LOSE_MESSAGE")); //$NON-NLS-1$
            }

            int result = JOptionPane.showConfirmDialog(this, Messages.getString("Game.NEW_GAME_QUERY")); //$NON-NLS-1$
            if (result == JOptionPane.YES_OPTION)
                NewGame();
        }

        repaint();
    }
}
