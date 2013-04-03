package ui.gui.graphical;

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

import logic.Architect;
import logic.Direction;
import logic.InanimatedObject;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;
import logic.Unit;
import model.Position;
import utils.Key;

public class Main extends JPanel implements KeyListener
{
	private static Configuration CONFIG = new Configuration("maze.config");

    private static final long serialVersionUID = 1L;

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;

    private static int CELL_WIDTH = WINDOW_WIDTH / CONFIG.GetMazeSize();
    private static int CELL_HEIGHT = WINDOW_HEIGHT / CONFIG.GetMazeSize();

    private void UpdateMazeSizes()
    {
        int min = Math.min(getWidth(), getHeight());
        CELL_WIDTH = min / CONFIG.GetMazeSize();
        CELL_HEIGHT = min / CONFIG.GetMazeSize();
    }

    public static void main(String[] args)
    {
        final JFrame frame = new JFrame("The Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);

        frame.setLayout(new BorderLayout());

        final Main m = new Main();

        //m.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        final JButton startButton = new JButton("Start");
        final JButton exitButton = new JButton("Quit");
        final JButton settingsButton = new JButton("Settings");

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                CONFIG.TrySaveToFile();
                System.exit(0);
            }
        } );

        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startButton.setEnabled(false);
                m._gameStarted = true;
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
                SettingsDialog d = new SettingsDialog(frame, CONFIG);
                d.setVisible(true);
                CONFIG = d.GetNewConfiguration();
            }
        });

        startButton.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                m.requestFocusInWindow();
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                m.requestFocusInWindow();
            }
        });

        frame.getContentPane().add(m, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(1, 3));
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

        southPanel.add(startButton);
        southPanel.add(settingsButton);
        southPanel.add(exitButton);

        frame.setVisible(true);
    }

    private final Maze _maze;
    private boolean _gameStarted = false;
    private final Map<String, TiledImage> _sprites;

    public Main()
    {
        addKeyListener(this);
        setFocusable(true);

        Architect architect = new Architect();
        MazeGenerator mg = new RandomMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(CONFIG.GetMazeSize(), CONFIG.GetNumberOfDragons(), CONFIG.GetDragonMode());

        _maze = architect.GetMaze();

        // Load sprites
        _sprites = new HashMap<String, TiledImage>();
        _sprites.put("dragon",     new TiledImage("resources/dragon_trans.png", 128, 128));
        _sprites.put("eagle",      new TiledImage("resources/eagle_trans.png",  64,  64));
        _sprites.put("hero",       new TiledImage("resources/hero_trans.png",   96,  96));
        _sprites.put("sword",      new TiledImage("resources/sword_trans.png",  96,  96));
        _sprites.put("grass",      new TiledImage("resources/grass.png",        96,  96));
        _sprites.put("stone",      new TiledImage("resources/stone.png",        96,  96));
        _sprites.put("dark_stone", new TiledImage("resources/dark_stone.png",   96,  96));
    }

    static int iter = 0;

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        UpdateMazeSizes();

        g.setColor(new Color(0, 100, 0)); // dark green
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int x = 0; x < _maze.GetWidth(); ++x)
        {
            for (int y = 0; y < _maze.GetHeight(); ++y)
            {
                InanimatedObject c = _maze.GetGrid().GetCellT(x, y);
                if (c.IsPath())
                    DrawCellAt(g, _sprites.get("grass").GetTile(0, 0), x, y);
                else if (c.IsWall())
                    DrawCellAt(g, _sprites.get("stone").GetTile(0, 0), x, y);
                else if (c.IsExitPortal())
                    DrawCellAt(g, _sprites.get("dark_stone").GetTile(0, 0), x, y);
            }
        }

        for (Unit u : _maze.GetLivingObjects())
        {
            switch (u.Type)
            {
                case Dragon:
                    DrawCellAt(g, _sprites.get("dragon").GetTile(iter % 9, u.ToDragon().IsSleeping() ? 11 : 0), u.GetPosition());
                    break;
                case Eagle:
                    if (!u.ToEagle().IsFlying() && !u.ToEagle().IsFollowingHero())
                        DrawCellAt(g, _sprites.get("eagle").GetTile(16, 0), u.GetPosition());
                    else if (u.ToEagle().IsFollowingHero())
                        DrawHalfCellAt(g, _sprites.get("eagle").GetTile(iter % 16, 0), u.GetPosition(), false);
                    else
                        DrawCellAt(g, _sprites.get("eagle").GetTile(iter % 16, 0), u.GetPosition());
                    break;
                case Hero:
                    if (_maze.FindEagle() != null && _maze.FindEagle().ToEagle().IsFollowingHero())
                        DrawHalfCellAt(g, _sprites.get("hero").GetTile(iter % 7, u.ToHero().IsArmed() ? 6 : 8), u.GetPosition(), true);
                    else
                        DrawCellAt(g, _sprites.get("hero").GetTile(iter % 7, u.ToHero().IsArmed() ? 6 : 8), u.GetPosition());
                    break;
                case Sword:
                    DrawCellAt(g, _sprites.get("sword").GetTile(0, 0), u.GetPosition());
                    break;
                default:
                    break;
            }
        }

        iter++;
    }

    public void DrawCellAt(Graphics g, Image img, Position pos) { DrawCellAt(g, img, pos.X, pos.Y); }

    public void DrawCellAt(Graphics g, Image img, int x, int y)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * CONFIG.GetMazeSize()) / 2 + x * CELL_WIDTH, (getHeight() - CELL_HEIGHT * CONFIG.GetMazeSize()) / 2 + y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, null);
    }

    public void DrawHalfCellAt(Graphics g, Image img, Position pos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, left); }

    public void DrawHalfCellAt(Graphics g, Image img, int x, int y, boolean left)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * CONFIG.GetMazeSize()) / 2 + x * CELL_WIDTH + (left ? -(CELL_WIDTH / 4) : CELL_WIDTH / 4), (getHeight() - CELL_HEIGHT * CONFIG.GetMazeSize()) / 2 + y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, null);
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
        if (!_gameStarted)
            return;

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
				_maze.SendEagleToSword();
				break;
			default:
				return;
			}
		}

        if (k != null)
            _maze.MoveHero(Direction.FromKey(k));
        _maze.Update();

        if (_maze.IsFinished())
        {
            JOptionPane.showMessageDialog(this, "Game Over");
            _gameStarted = false;
        }

        repaint();
    }
}
