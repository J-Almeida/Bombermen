package ui.gui.ascii;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import logic.Architect;
import logic.Direction;
import logic.Dragon;
import logic.Maze;
import logic.MazeGenerator;
import logic.RandomMazeGenerator;
import utils.Key;

/**
 * Graphical interface but still using ASCII representation of the maze
 */
public class SimpleGame extends JPanel implements KeyListener
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("The Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());

        //frame.pack();

        SimpleGame m = new SimpleGame();
        frame.getContentPane().add(m);

        frame.setVisible(true);
    }

    /** The text area. */
    private final JTextArea _textArea = new JTextArea(10, 10);

    /** The quit button. */
    private final JButton _quitButton = new JButton("Quit");

    /** The start button. */
    private final JButton _startButton = new JButton("Start");

    /** The maze. */
    private final Maze _maze;

    /** The game started. */
    private boolean _gameStarted = false;

    /**
     * Instantiates a new simple game.
     */
    public SimpleGame()
    {
        addKeyListener(this);
        setFocusable(true);

        Architect architect = new Architect();
        MazeGenerator mg = new RandomMazeGenerator();

        architect.SetMazeGenerator(mg);
        architect.ConstructMaze(10, 2, Dragon.Behaviour.Sleepy);

        _maze = architect.GetMaze();

        _textArea.setFont(new Font("Courier", _textArea.getFont().getStyle(), _textArea.getFont().getSize() + 1));
        _textArea.setText(_maze.toString());
        _textArea.setEditable(false);

        _startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _startButton.setEnabled(false);
                _gameStarted = true;
            }
        });

        _quitButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        _startButton.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                requestFocusInWindow();
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                requestFocusInWindow();
            }
        });

        add(_textArea);
        add(_startButton);
        add(_quitButton);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //_textArea.requestFocusInWindow();
        _textArea.setText(_maze.toString());
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e)
    {

        repaint();
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        repaint();
    }

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
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
        repaint();

        if (_maze.IsFinished())
        {
            JOptionPane.showMessageDialog(this, "Game Over");
            _gameStarted = false;
        }
    }
}
