package gui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.IState;
import logic.StateManager;

@SuppressWarnings("serial")
public class Client extends JPanel implements Runnable, KeyListener
{   
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 800;

    Map<String, IDraw> _states;
    StateManager _stateManager;
    
    private void AddState(String name, IDraw state)
    {
        if (!(state instanceof IState))
        {
            System.err.println("Client::AddState: received wrong state object.");
            return;
        }
        
        _states.put(name, state);
        _stateManager.AddState(name, (IState) state);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Bombermen 0.1");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Client c = new Client();
        frame.add(c);
        
        //c._stateManager.
        frame.addKeyListener(c);

        //frame.pack();
        frame.setVisible(true);
        
        new Thread(c).start();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        _states.get(_stateManager.GetCurrentState()).Draw((Graphics2D)g);
    }
    
    public Client()
    {
        _stateManager = new StateManager();
        _states = new HashMap<String, IDraw>();

        AddState("game", new SwingGameState());

        _stateManager.ChangeState("game");
    }

    @Override
    public void run()
    {
        boolean done = false;
        while (!done)
        {
            _stateManager.Update(10);
            repaint();
            try
            {
                Thread.sleep(20);
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        _stateManager.GetState("game").keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        _stateManager.GetState("game").keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        _stateManager.GetState("game").keyReleased(e);
    }
}
