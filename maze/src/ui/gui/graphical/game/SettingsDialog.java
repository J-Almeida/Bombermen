package ui.gui.graphical.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

import logic.Dragon;

/**
 * The Class SettingsDialog.
 */
public class SettingsDialog extends JDialog
{
    /**
     * The state of SettingsDialog, editing table or not.
     */
    private enum State
    {
        /** The normal. */
        NORMAL,

        /** The editing. */
        EDITING
    }

    /**
     * Instantiates a new settings dialog.
     *
     * @param frame the parent frame
     * @param prevConfig the previous config
     */
    SettingsDialog(JFrame frame, Configuration prevConfig)
    {
        super(frame, ModalityType.APPLICATION_MODAL);
        _prevConfig = prevConfig;
        _newConfig = _prevConfig;

        initUI();

        setSize(getSize().width + 50, getSize().height + 100);
        setLocation(frame.getLocation().x + frame.getSize().width / 2 - getSize().width / 2, frame.getLocation().y + frame.getSize().height / 2 - getSize().height / 2);
        setTitle("Settings");
    }

    /**
     * Initializes the interface
     */
    @SuppressWarnings("serial")
    private void initUI()
    {
        String[] columnNames = { "Action", "Key" };

        Map<Integer, Action> tempKeys = _prevConfig.GetKeys();

        Object[][] columnData = new Object[tempKeys.size()][2];

        int i = 0;
        for (Map.Entry<Integer, Action> elem : tempKeys.entrySet())
        {
            keys.put(elem.getValue(),elem.getKey());
            columnData[i][0] = elem.getValue();
            columnData[i][1] = KeyEvent.getKeyText(elem.getKey());
            i++;
        }

        tblKeys = new JTable(columnData, columnNames)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        tblKeys.enableInputMethods(false);
        tblKeys.setCellSelectionEnabled(false);
        tblKeys.setRowSelectionAllowed(true);

        tblKeys.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        tblKeys.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
        tblKeys.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");

        AbstractAction nothing = new AbstractAction() { @Override public void actionPerformed(ActionEvent ae) {} };

        tblKeys.getActionMap().put("Enter", nothing);
        tblKeys.getActionMap().put("Left", nothing);
        tblKeys.getActionMap().put("Right", nothing);

        tblKeys.addKeyListener(new KeyListener()
        {
            private State _state = State.NORMAL;
            private int _rowNumber = -1;
            ActionMap _prevActionMap = null;
            @Override
            public void keyTyped(KeyEvent arg0) { }

            @Override
            public void keyReleased(KeyEvent arg0) { }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (_state == State.NORMAL)
                {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        _state = State.EDITING;
                        _rowNumber = tblKeys.getSelectedRow();
                        _prevActionMap = tblKeys.getActionMap();
                        tblKeys.setActionMap(new ActionMap());
                        lblMessage.setText("<HTML>Press a key to change the selected<BR>action os ESC to cancel.</HTML>");
                    }
                }
                else if (_state == State.EDITING)
                {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    {
                        _state = State.NORMAL;
                        lblMessage.setText("<HTML>Action Cacelled.<BR>Select an action and press enter to edit.</HTML>");
                    }
                    else if (tblKeys.getSelectedRow() != _rowNumber)
                    {
                        _state = State.NORMAL;
                        lblMessage.setText("<HTML>Select an action and press enter to edit.</HTML>");
                    }
                    else if (!keys.containsValue(e.getKeyCode()))
                    {
                        keys.remove(tblKeys.getValueAt(tblKeys.getSelectedRow(), 0));
                        keys.put((Action)tblKeys.getValueAt(tblKeys.getSelectedRow(), 0), e.getKeyCode());

                        tblKeys.setValueAt(KeyEvent.getKeyText(e.getKeyCode()), tblKeys.getSelectedRow(), 1);

                        _state = State.NORMAL;

                        lblMessage.setText("<HTML>Attribution done.<BR>Select an action and press enter to edit.</HTML>");
                    }
                    else
                        lblMessage.setText("<HTML>The key " + KeyEvent.getKeyText(e.getKeyCode()) + " is already in use.</HTML>");

                    if (_state == State.NORMAL)
                        tblKeys.setActionMap(_prevActionMap);
                }
            }
        });

        btnOK.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                _newConfig = (Configuration) _prevConfig.clone();
                _newConfig.SetNumberOfDragons((Integer)spnNumberOfDragons.getValue());
                _newConfig.SetMazeSize((Integer)spnMazeSize.getValue());
                _newConfig.SetDragonMode((Dragon.Behaviour)cmbDragonMode.getSelectedItem());
                LinkedHashMap<Integer, Action> newKeys = new LinkedHashMap<Integer, Action>();

                for (Map.Entry<Action, Integer> elem : keys.entrySet())
                    newKeys.put(elem.getValue(), elem.getKey());

                _newConfig.SetKeys(newKeys);

                setVisible(false);
            }
        });

        btnCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                _newConfig = _prevConfig;
                setVisible(false);
            }
        });

        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout gbl = new GridBagLayout();

        c.weighty = 4;

        setLayout(gbl);

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        getContentPane().add(new JLabel("Number of Dragons: "), c);

        c.gridx = 1;
        getContentPane().add(spnNumberOfDragons, c);

        c.gridx = 0;
        c.gridy = 1;
        getContentPane().add(new JLabel("Maze Size: "), c);

        c.gridx = 1;
        getContentPane().add(spnMazeSize, c);

        c.gridx = 0;
        c.gridy = 2;
        getContentPane().add(new JLabel("Dragon Mode: "), c);

        c.gridx = 1;
        getContentPane().add(cmbDragonMode, c);

        c.gridx = 0;
        c.gridy = 3;
        getContentPane().add(new JLabel("Keys:"), c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        getContentPane().add(tblKeys, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        getContentPane().add(btnOK, c);

        c.gridx = 1;
        getContentPane().add(btnCancel, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        getContentPane().add(new JSeparator(), c);

        c.gridy = 7;
        lblMessage.setPreferredSize(new Dimension(getWidth(), 25));
        getContentPane().add(lblMessage, c);

        spnNumberOfDragons.setValue(_prevConfig.GetNumberOfDragons());
        spnMazeSize.setValue(_prevConfig.GetMazeSize());

        for (Dragon.Behaviour db : Dragon.Behaviour.values())
            cmbDragonMode.addItem(db);

        cmbDragonMode.setSelectedItem(_prevConfig.GetDragonMode());

        pack();
    }

    /**
     * Gets the new configuration.
     *
     * @return the configuration
     */
    public Configuration GetNewConfiguration() { return _newConfig; }

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The previous configuration. */
    private final Configuration _prevConfig;

    /** The new configuration. */
    private Configuration _newConfig = null;

    /** The OK button. */
    private final JButton  btnOK                             = new JButton("OK");

    /** The Cancel button. */
    private final JButton  btnCancel                         = new JButton("Cancel");

    /** The number of dragons spinner. */
    private final JSpinner spnNumberOfDragons                = new JSpinner(new SpinnerNumberModel(2, 0, 1000, 1));

    /** The maze size spinner. */
    private final JSpinner spnMazeSize                       = new JSpinner(new SpinnerNumberModel(10, 6, 1000, 1));

    /** The combo box with dragon modes. */
    private final JComboBox<Dragon.Behaviour> cmbDragonMode  = new JComboBox<Dragon.Behaviour>();

    /** The table with keys. */
    private JTable tblKeys;

    /** The keys map. */
    private final Map<Action, Integer> keys                  = new LinkedHashMap<Action, Integer>();

    /** The helper label message. */
    private final JLabel lblMessage                          = new JLabel("<HTML>Select an action and press enter to edit.<BR></HTML>");
}
