package gui.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.StateManager;

@SuppressWarnings("serial")
public class Client extends JPanel implements Runnable, KeyListener
{
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    StateManager _stateManager;

    public static void main(String[] args) throws InterruptedException
    {
        JFrame frame = new JFrame("Bombermen 0.1");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Client c = new Client();
        frame.add(c);

        frame.addKeyListener(c);

        frame.setVisible(true);

        //Thread.sleep(200);
        new Thread(c).start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        ((IDraw)_stateManager.GetCurrentState()).Draw((Graphics2D)g);
    }

    public Client()
    {
        _stateManager = new StateManager();

        //_stateManager.AddState("mainmenu", new SwingMainMenuState());
        //_stateManager.AddState("settings", new SwingSettings());
        //_stateManager.AddState("selectserver", new SwingSelectServer());
        //_stateManager.AddState("scoreboard", new SwingScoreBoard());
        _stateManager.AddState("game", new SwingGameState());

        _stateManager.ChangeState("game");
    }

    @Override
    public void run()
    {
        boolean done = false;
        long millis = System.currentTimeMillis();
        while (!done)
        {
            int dt = (int) (System.currentTimeMillis() - millis);
            millis = System.currentTimeMillis();

            _stateManager.Update(dt);

            repaint(); // draw

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        _stateManager.GetCurrentState().keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        _stateManager.GetCurrentState().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        _stateManager.GetCurrentState().keyReleased(e);
    }
}
