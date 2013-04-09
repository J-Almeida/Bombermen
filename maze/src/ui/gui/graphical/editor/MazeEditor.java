package ui.gui.graphical.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Architect;
import logic.Dragon;
import logic.Dragon.Behaviour;
import logic.Eagle;
import logic.EmptyMazeGenerator;
import logic.ExitPortal;
import logic.Hero;
import logic.InanimatedObject;
import logic.Maze;
import logic.MazeGenerator;
import logic.Path;
import logic.Sword;
import logic.Unit;
import logic.Wall;
import logic.WorldObject;
import model.Position;
import ui.gui.graphical.game.MazeGame;
import ui.gui.graphical.game.SaveLoadDialog;
import ui.gui.graphical.game.TiledImage;

/**
 * The Class MazeEditor.
 */
public class MazeEditor extends JPanel implements MouseListener, MazeGame
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant WINDOW_WIDTH. */
    private static final int WINDOW_WIDTH = 600;

    /** The Constant WINDOW_HEIGHT. */
    private static final int WINDOW_HEIGHT = 600;

    /** The maze size. */
    private static int MAZE_SIZE = 20;

    /** The cell width. */
    private static int CELL_WIDTH = WINDOW_WIDTH / 10;

    /** The cell height. */
    private static int CELL_HEIGHT = WINDOW_HEIGHT / 10;

    /**
     * Update maze sizes.
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
        final JFrame frame = new JFrame("The Maze Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        frame.setLayout(new BorderLayout());

        final MazeEditor m = new MazeEditor();

        final JButton saveButton = new JButton("Save/Load");
        final JButton resetButton = new JButton("Reset");
        final JButton exitButton = new JButton("Quit");

        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SaveLoadDialog d = new SaveLoadDialog(frame, m, true);
                d.setVisible(true);
            }
        });

        resetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                m.ResetMaze();
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

        frame.getContentPane().add(m, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(1, 3));
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

        southPanel.add(saveButton);
        southPanel.add(resetButton);
        southPanel.add(exitButton);

        frame.setVisible(true);
    }

    /** The maze. */
    private Maze _maze;

    /** The map of sprites. */
    private Map<String, TiledImage> _sprites;

    /**
     * Instantiates a new maze editor.
     */
    public MazeEditor()
    {
        addMouseListener(this);
        setFocusable(true);

        boolean success = false;
        int mazeSize = 0;

        do
        {
            String mazeSizeStr = JOptionPane.showInputDialog("Maze size", 10);
            try
            {
                mazeSize = Integer.parseInt(mazeSizeStr);
                if (mazeSize > 5)
                    success = true;
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Invalid maze size");
                success = false;
            }
        }
        while (!success);

        MAZE_SIZE = mazeSize;

        Architect architect = new Architect();
        MazeGenerator mg = new EmptyMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(MAZE_SIZE, 0, Dragon.Behaviour.Sleepy);

        _maze = architect.GetMaze();

        // Load sprites
        _sprites = new HashMap<String, TiledImage>();
        _sprites.put("dragon",     new TiledImage("../resources/dragon_trans.png", 128, 128));
        _sprites.put("eagle",      new TiledImage("../resources/eagle_trans.png",  64,  64));
        _sprites.put("hero",       new TiledImage("../resources/hero_trans.png",   96,  96));
        _sprites.put("sword",      new TiledImage("../resources/sword_trans.png",  96,  96));
        _sprites.put("grass",      new TiledImage("../resources/grass.png",        96,  96));
        _sprites.put("stone",      new TiledImage("../resources/stone.png",        96,  96));
        _sprites.put("dark_stone", new TiledImage("../resources/dark_stone.png",   96,  96));
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
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
                    DrawCellAt(g, _sprites.get("dragon").GetTile(0, 0), u.GetPosition());
                    break;
                case Eagle:
                    DrawHalfCellAt(g, _sprites.get("eagle").GetTile(0, 0), u.GetPosition(), false);
                    break;
                case Hero:
                    DrawHalfCellAt(g, _sprites.get("hero").GetTile(0, 8), u.GetPosition(), true);
                    break;
                case Sword:
                    DrawCellAt(g, _sprites.get("sword").GetTile(0, 0), u.GetPosition());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Draw cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param pos the position
     */
    public void DrawCellAt(Graphics g, Image img, Position pos) { DrawCellAt(g, img, pos.X, pos.Y); }

    /**
     * Draw cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param x the x position
     * @param y the y position
     */
    public void DrawCellAt(Graphics g, Image img, int x, int y)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH, (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, null);
    }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param pos the position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, Position pos, boolean left) { DrawHalfCellAt(g, img, pos.X, pos.Y, left); }

    /**
     * Draw half cell at position.
     *
     * @param g the graphics used to draw
     * @param img the image to draw
     * @param x the x position
     * @param y the y position
     * @param left true if draw at left, false if draw at right
     */
    public void DrawHalfCellAt(Graphics g, Image img, int x, int y, boolean left)
    {
        g.drawImage(img, (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2 + x * CELL_WIDTH + (left ? -(CELL_WIDTH / 4) : CELL_WIDTH / 4), (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2 + y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT, null);
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int offsetx = (getWidth() - CELL_WIDTH * MAZE_SIZE) / 2;
        int offsety = (getHeight() - CELL_HEIGHT * MAZE_SIZE) / 2;
        int x = (e.getX() - offsetx) / CELL_WIDTH;
        int y = (e.getY() - offsety) / CELL_HEIGHT;
        Position pos = new Position(x, y);

        WorldObject obj = _maze.GetPosition(pos);
        if (obj == null)
            return;

        WorldObject newObj = GetNextWorldObject(obj);
        if (newObj == null)
            return;

        if (obj.IsUnit())
        {
            obj.ToUnit().Kill();
            if (obj.ToUnit().IsHero())
                if (_maze.FindEagle() != null)
                    _maze.FindEagle().Kill();
        }
        else if (obj.IsInanimatedObject() && newObj.IsUnit())
        {
            InanimatedObject path = new Path();
            path.SetPosition(obj.GetPosition().clone());
            _maze.AddWorldObject(path);
        }

        _maze.Update(); // update to remove killed units

        _maze.AddWorldObject(newObj);
        if (newObj.IsUnit() && newObj.ToUnit().IsHero())
        {
            Eagle eagle = new Eagle();
            eagle.SetPosition(newObj.GetPosition().clone());
            _maze.AddWorldObject(eagle);
        }

        repaint();
    }

    /**
     * Gets the next world object when iterating over the possible objects for a cell.
     *
     * @param oldObj the previous object
     * @return the world object (null if nothing is available)
     */
    private WorldObject GetNextWorldObject(WorldObject oldObj)
    {
        WorldObject newObj = null;

        // List to follow:
        // 1. path
        // 2. wall
        // 3. exitportal
        // 4. hero
        // 5. dragon
        // 6. sword
        // 1. ... (back to beginning)

        if (oldObj.IsInanimatedObject())
        {
            InanimatedObject io = oldObj.ToInanimatedObject();
            if (io.IsPath())
                newObj = new Wall();
            else if (io.IsWall())
                newObj = new ExitPortal();
            else if (io.IsExitPortal())
                newObj = new Hero();
        }
        else if (oldObj.IsUnit())
        {
            Unit u = oldObj.ToUnit();
            if (u.IsHero())
                newObj = new Dragon(Behaviour.Idle);
            else if (u.IsDragon())
                newObj = new Sword();
            else if (u.IsSword())
                newObj = new Path();
        }

        if (newObj != null)
        {
            newObj.SetPosition(oldObj.GetPosition().clone());

            // constraints

            // exit portal, can't be duplicated and has to be in border
            if (newObj.IsInanimatedObject() && newObj.ToInanimatedObject().IsExitPortal())
            {
                if (!IsBorder(newObj.GetPosition()))
                    return GetNextWorldObject(newObj);
                else if (_maze.FindExitPortal() != null)
                    return GetNextWorldObject(newObj);
            }

            // path, can't be in border
            if (newObj.IsInanimatedObject() && newObj.ToInanimatedObject().IsPath())
            {
                if (IsBorder(newObj.GetPosition()))
                    return GetNextWorldObject(newObj);
            }

            // hero, can't be duplicated nor in border
            if (newObj.IsUnit() && newObj.ToUnit().IsHero())
            {
                if (IsBorder(newObj.GetPosition()))
                    return GetNextWorldObject(newObj);
                else if (_maze.FindHero() != null)
                    return GetNextWorldObject(newObj);
            }

            // dragon, can't be in border
            if (newObj.IsUnit() && newObj.ToUnit().IsDragon())
                if (IsBorder(newObj.GetPosition()))
                    return GetNextWorldObject(newObj);

            // sword, can't be duplicated nor in border
            if (newObj.IsUnit() && newObj.ToUnit().IsSword())
            {
                if (IsBorder(newObj.GetPosition()))
                    return GetNextWorldObject(newObj);
                if (_maze.FindSword() != null)
                    return GetNextWorldObject(newObj);
            }
        }

        return newObj;
    }

    /**
     * Checks if is border.
     *
     * @param position the position
     * @return true, if successful
     */
    private boolean IsBorder(Position position)
    {
        int x = position.X;
        int y = position.Y;
        return x == 0 || x == MAZE_SIZE - 1 || y == 0 || y == MAZE_SIZE - 1;
    }

    /**
     * Reset maze.
     */
    public void ResetMaze()
    {
        Architect architect = new Architect();
        MazeGenerator mg = new EmptyMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(MAZE_SIZE, 0, Dragon.Behaviour.Idle);

        _maze = architect.GetMaze();
        repaint();
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) { }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) { }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) { }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.MazeGame#GetMaze()
     */
    @Override
    public Maze GetMaze()
    {
        return _maze;
    }

    /* (non-Javadoc)
     * @see ui.gui.graphical.game.MazeGame#SetMaze(logic.Maze)
     */
    @Override
    public void SetMaze(Maze m)
    {
        _maze = m;
    }
}
