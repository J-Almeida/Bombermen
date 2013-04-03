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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Architect;
import logic.Direction;
import logic.Dragon;
import logic.InanimatedObject;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;
import logic.Unit;
import model.Position;
import utils.Key;

public class Main extends JPanel implements KeyListener
{
    private static final long serialVersionUID = 1L;

    private static final int MAZE_SIZE = 20;
    private static final int DRAGON_COUNT = 2000;

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;

    private static int CELL_WIDTH = WINDOW_WIDTH / MAZE_SIZE;
    private static int CELL_HEIGHT = WINDOW_HEIGHT / MAZE_SIZE;

    private void UpdateMazeSizes()
    {
        int min = Math.min(getWidth(), getHeight());
        CELL_WIDTH = min / MAZE_SIZE;
        CELL_HEIGHT = min / MAZE_SIZE;
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
                System.exit(0);
            }
        });

        settingsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JDialog d = new JDialog(frame);
                d.add(new JButton("Test"));
                d.pack();
                d.setVisible(true);
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

    private Maze _maze;
    private boolean _gameStarted = false;
    private Map<String, TiledImage> _sprites;

    public Main()
    {
        addKeyListener(this);
        setFocusable(true);

        Architect architect = new Architect();
        MazeGenerator mg = new RandomMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(MAZE_SIZE, DRAGON_COUNT, Dragon.Behaviour.Sleepy);

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
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH, (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, null);
    }

    public void DrawHalfCellAt(Graphics g, Image img, Position pos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, left); }

    public void DrawHalfCellAt(Graphics g, Image img, int x, int y, boolean left)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH + (left ? -(CELL_WIDTH / 4) : CELL_WIDTH / 4), (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, null);
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

        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W:
                k = Key.UP;
                break;
            case KeyEvent.VK_S:
                k = Key.DOWN;
                break;
            case KeyEvent.VK_D:
                k = Key.RIGHT;
                break;
            case KeyEvent.VK_A:
                k = Key.LEFT;
                break;
            case KeyEvent.VK_F1:
                _maze.SendEagleToSword();
                break;
            default:
                return;
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
