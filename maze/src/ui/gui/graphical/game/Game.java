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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Architect;
import logic.Direction;
import logic.Hero;
import logic.InanimatedObject;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;
import logic.Unit;
import model.Position;
import utils.Key;

public class Game extends JPanel implements KeyListener, MazeGame
{
    private Configuration CONFIG = new Configuration("maze.config");

    private static final long serialVersionUID = 1L;

    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 600;

    private int MAZE_SIZE = 10;

    private int CELL_WIDTH = WINDOW_WIDTH / MAZE_SIZE;
    private int CELL_HEIGHT = WINDOW_HEIGHT / MAZE_SIZE;

    private void UpdateMazeSizes()
    {
        int min = Math.min(getWidth(), getHeight());
        CELL_WIDTH = min / MAZE_SIZE;
        CELL_HEIGHT = min / MAZE_SIZE;
    }

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

    private Maze _maze;
    @Override
    public Maze GetMaze() { return _maze; }
    @Override
    public void SetMaze(Maze maze) { _maze = maze; }
    private final Map<String, TiledImage> _sprites;
    private final HashMap<Integer, AnimatedSprite> _unitSprites = new HashMap<Integer, AnimatedSprite>();
    private boolean _gameFinished = false;

	private boolean _heroMoving = false;

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

    public Game()
    {
        addKeyListener(this);
        setFocusable(true);

        // Load sprites
        _sprites = new HashMap<String, TiledImage>();
        _sprites.put("dragon",     new TiledImage("ui/gui/graphical/resources/dragon_trans.png", 128, 128));
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

    static int iter = 0;

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
    }

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

        for (Unit u : GetMaze().GetLivingObjects())
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
        }

        iter++;
    }

    public void DrawCellAt(Graphics g, Image img, Position pos, Position dPos) { DrawCellAt(g, img, pos.X, pos.Y, dPos.X, dPos.Y); }
    public void DrawCellAt(Graphics g, Image img, Position pos) { DrawCellAt(g, img, pos.X, pos.Y, 0, 0); }
    public void DrawCellAt(Graphics g, Image img, int x, int y) { DrawCellAt(g, img, x, y, 0, 0); }

    public void DrawCellAt(Graphics g, Image img, Position pos, int dX, int dY) { DrawCellAt(g, img, pos.X, pos.Y, dX, dY); }

    public void DrawCellAt(Graphics g, Image img, int x, int y, int dX, int dY)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH + dX, (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT + dY, CELL_WIDTH, CELL_HEIGHT, null);
    }

    public void DrawHalfCellAt(Graphics g, Image img, Position pos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, 0, 0, left); }
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, Position dPos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, dPos.X, dPos.Y, left); }
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, int dX, int dY, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, dX, dY, left); }

    public void DrawHalfCellAt(Graphics g, Image img, int x, int y, int dX, int dY, boolean left)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH + (left ? -(CELL_WIDTH / 4) : CELL_WIDTH / 4) + dX, (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT + dY, CELL_WIDTH, CELL_HEIGHT, null);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        //repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        //repaint();
    }

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
