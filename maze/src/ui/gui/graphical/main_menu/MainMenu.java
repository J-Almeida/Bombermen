package ui.gui.graphical.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu
{
    public static void main(String[] args) throws IOException
    {
        final JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setResizable(false);
        
        InputStream input = java.lang.ClassLoader.getSystemResourceAsStream("ui/gui/graphical/resources/background.png"); //$NON-NLS-1$
        Image bg = ImageIO.read(input);

        ImageBackground ibg = new ImageBackground(bg);
        
        frame.setContentPane(ibg);
        
        frame.getContentPane().setLayout(new BorderLayout());
        
        Font buttonsFont = new Font("arial", Font.BOLD, 30); //$NON-NLS-1$
        
        JButton playButton = new JButton(Messages.getString("MainMenu.PlayButton")); //$NON-NLS-1$
        JButton editButton = new JButton(Messages.getString("MainMenu.EditButton")); //$NON-NLS-1$
        JButton helpButton = new JButton(Messages.getString("MainMenu.HelpButton")); //$NON-NLS-1$

        playButton.setPreferredSize(new Dimension(200, 200));
        editButton.setPreferredSize(new Dimension(200, 200));
        helpButton.setPreferredSize(new Dimension(200, 200));

        playButton.setFont(buttonsFont);
        editButton.setFont(buttonsFont);
        helpButton.setFont(buttonsFont);
        
        playButton.setForeground(new Color(255, 255, 255, 255));
        editButton.setForeground(new Color(255, 255, 255, 255));
        helpButton.setForeground(new Color(255, 255, 255, 255));
        
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        editButton.setOpaque(false);
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        
        playButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false);
                ui.gui.graphical.game.Game.main(null);
            }
        });
        
        editButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false);
                ui.gui.graphical.editor.MazeEditor.main(null);
            }
        });
        
        helpButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JDialog dialog = new JDialog(frame, ModalityType.APPLICATION_MODAL);
                dialog.setTitle(Messages.getString("MainMenu.HelpTitle")); //$NON-NLS-1$
                
                dialog.getContentPane().setLayout(new BorderLayout());
                
                JLabel helpText = new JLabel(
                        Messages.getString("MainMenu.HelpText") //$NON-NLS-1$
                        );

                dialog.getContentPane().add(helpText, BorderLayout.CENTER);
                
                dialog.setResizable(false);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(600, 240));
        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;

        c.weighty = 3;
        c.weightx = 3;
        
        c.gridx = 0;
        //c.gridy = 4;
        buttonsPanel.add(editButton, c);
        
        c.gridx = 1;
        //c.gridy = 5;
        buttonsPanel.add(playButton, c);

        c.gridx = 2;
        //c.gridy = 6;
        buttonsPanel.add(helpButton, c);
        
        frame.setVisible(true);
    }

}
