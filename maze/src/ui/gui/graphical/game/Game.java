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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Architect;
import logic.Direction;
import logic.Dragon;
import logic.Eagle;
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
    private Configuration CONFIG = new Configuration("maze.config");

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

        final JFrame frame = new JFrame("The Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(m.WINDOW_WIDTH, m.WINDOW_HEIGHT);
        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);

        frame.setLayout(new BorderLayout());

        final JButton newGameButton = new JButton("New Game");
        final JButton saveButton = new JButton("Save/Load");
        final JButton exitButton = new JButton("Quit");
        final JButton settingsButton = new JButton("Settings");

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
                int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to start a new game?");

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

    /**
     * Create a new game (calls repaint())
     */
    public void NewGame()
    {
        _gameFinished = false;

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
                _unitSprites.put(u.GetId(), new HeroSprite(u.ToHero(), _sprites.get("hero")));
            else if (u.IsDragon())
                _unitSprites.put(u.GetId(), new DragonSprite(u.ToDragon(), _sprites.get("dragon")));
            else if (u.IsEagle())
                _unitSprites.put(u.GetId(), new EagleSprite(u.ToEagle(), _sprites.get("eagle")));
            else if (u.IsSword())
                _unitSprites.put(u.GetId(), new SwordSprite(u.ToSword(), _sprites.get("sword")));
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
        _sprites.put("dragon",     new TiledImage("ui/gui/graphical/resources/dragon_trans.png", 128, 128));
        _sprites.put("dying_dragon", new TiledImage("ui/gui/graphical/resources/dying_dragon_trans.png", 128, 128));
        _sprites.put("eagle",      new TiledImage("ui/gui/graphical/resources/eagle_trans.png",  64,  64));
        _sprites.put("hero",       new TiledImage("ui/gui/graphical/resources/hero_trans.png",   96,  96));
        _sprites.put("sword",      new TiledImage("ui/gui/graphical/resources/sword_trans.png",  96,  96));
        _sprites.put("grass",      new TiledImage("ui/gui/graphical/resources/grass.png",        96,  96));
        _sprites.put("stone",      new TiledImage("ui/gui/graphical/resources/stone.png",        96,  96));
        _sprites.put("dark_stone", new TiledImage("ui/gui/graphical/resources/dark_stone.png",   96,  96));

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
            if (((HeroSprite)(_unitSprites.get(h.GetId()))).IsWalking())
                _heroMoving = true;
            else
                _heroMoving = false;
        }

        /*for (Unit u : _maze.GetLivingObjects())
        {
            if (!u.IsAlive())
            {
                _unitSprites.remove(u.GetId());
                if (u.IsDragon())
                {
                    _unitSprites.put(u.GetId(), new DyingDragonSprite(u.ToDragon(),_sprites.get("dying_dragon")));
                }
            }
        }*/

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
                _unitSprites.put(d.GetId(), new DyingDragonSprite(d,_sprites.get("dying_dragon")));
            }
            else
            {
                _unitSprites.remove(as.GetUnitId());
            }
        }
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
                    DrawCellAt(g, _sprites.get("grass").GetTile(0, 0), x, y);
                else if (c.IsWall())
                    DrawCellAt(g, _sprites.get("stone").GetTile(0, 0), x, y);
                else if (c.IsExitPortal())
                    DrawCellAt(g, _sprites.get("dark_stone").GetTile(0, 0), x, y);
            }
        }

        /*for (Unit u : GetMaze().GetLivingObjects())
        {
            switch (u.Type)
            {
            case Dragon:
                DrawCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), _unitSprites.get(u.GetId()).GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
                break;
            case Eagle:
                if (!u.ToEagle().IsFlying() && !u.ToEagle().IsFollowingHero())
                    DrawCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), _unitSprites.get(u.GetId()).GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
                else if (u.ToEagle().IsFollowingHero())
                    DrawHalfCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), _unitSprites.get(u.GetId()).GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT), false);
                else
                    DrawCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), _unitSprites.get(u.GetId()).GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
                break;
            case Hero:
                if (GetMaze().FindEagle() != null && GetMaze().FindEagle().ToEagle().IsFollowingHero())
                    DrawHalfCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), _unitSprites.get(u.GetId()).GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT), true);
                else
                    DrawCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), _unitSprites.get(u.GetId()).GetDeltaPosition(CELL_WIDTH, CELL_HEIGHT));
                break;
            case Sword:
                DrawCellAt(g, _unitSprites.get(u.GetId()).GetCurrentImage(), _unitSprites.get(u.GetId()).GetPosition(), 0, 0);
                break;
            default:
                break;
            }
        }*/

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
            else
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
     * @param dx the x delta position
     * @param dy the y delta position
     */
    public void DrawCellAt(Graphics g, Image img, Position pos, int dX, int dY) { DrawCellAt(g, img, pos.X, pos.Y, dX, dY); }

    /**
     * Draw cell at position
     *
     * @param g the graphics
     * @param img the image to draw
     * @param x the x position
     * @param y the y position
     * @param dx the x delta position
     * @param dy the y delta position
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
     * @param dpos the delta position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, Position dPos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, dPos.X, dPos.Y, left); }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param pos the position
     * @param dx the x delta position
     * @param dy the y delta position
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
     * @param dx the x delta position
     * @param dy the y delta position
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
                GetMaze().MoveHero(Direction.FromKey(k));
            Update(0);
        }

        if (GetMaze().IsFinished())
        {
            _gameFinished = true;

            if (GetMaze().FindHero() != null)
                JOptionPane.showMessageDialog(this, "You won!");
            else
                JOptionPane.showMessageDialog(this, "You lost, game over.");

            int result = JOptionPane.showConfirmDialog(this, "Do you wish to start a new game?");
            if (result == JOptionPane.YES_OPTION)
                NewGame();
        }

        repaint();
    }
}
