package ui.gui.graphical;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;

import logic.Dragon;

public class SettingsDialog extends JDialog {

	SettingsDialog(JFrame frame, Configuration prevConfig)
	{
		super(frame, ModalityType.APPLICATION_MODAL);
		_prevConfig = prevConfig;
		_newConfig = _prevConfig;

		initUI();

		this.setSize(this.getSize().width + 50, this.getSize().height + 100);

		this.setLocation(frame.getLocation().x + frame.getSize().width / 2 - this.getSize().width / 2, frame.getLocation().y + frame.getSize().height / 2 - this.getSize().height / 2);
	}

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

		tblKeys = new JTable(columnData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblKeys.enableInputMethods(false);
		tblKeys.setCellSelectionEnabled(false);
		tblKeys.setRowSelectionAllowed(true);

		tblKeys.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) { }

			@Override
			public void keyReleased(KeyEvent arg0) { }

			@Override
			public void keyPressed(KeyEvent e) {
				if (!keys.containsValue(e.getKeyCode())) {
					keys.remove(tblKeys.getValueAt(tblKeys.getSelectedRow(), 0));
					keys.put((Action)tblKeys.getValueAt(tblKeys.getSelectedRow(), 0), e.getKeyCode());

					tblKeys.setValueAt(KeyEvent.getKeyText(e.getKeyCode()), tblKeys.getSelectedRow(), 1);
				}

			}
		});

		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_newConfig = _prevConfig;
				setVisible(false);
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();

		c.weighty = 4;

		this.setLayout(gbl);

		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		this.getContentPane().add(new JLabel("Number of Dragons: "), c);

		c.gridx = 1;
		this.getContentPane().add(spnNumberOfDragons, c);

		c.gridx = 0;
		c.gridy = 1;
		this.getContentPane().add(new JLabel("Maze Size: "), c);

		c.gridx = 1;
        this.getContentPane().add(spnMazeSize, c);

        c.gridx = 0;
		c.gridy = 2;
        this.getContentPane().add(new JLabel("Dragon Mode: "), c);

        c.gridx = 1;
		this.getContentPane().add(cmbDragonMode, c);


		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		this.getContentPane().add(new JLabel("Keys:"), c);

		c.gridy = 4;
		this.getContentPane().add(tblKeys, c);

		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 5;
		this.getContentPane().add(btnOK, c);

		c.gridx = 1;
		this.getContentPane().add(btnCancel, c);

		spnNumberOfDragons.setValue(_prevConfig.GetNumberOfDragons());
		spnMazeSize.setValue(_prevConfig.GetMazeSize());

		for (Dragon.Behaviour db : Dragon.Behaviour.values())
			cmbDragonMode.addItem(db);

		cmbDragonMode.setSelectedItem(_prevConfig.GetDragonMode());

		this.pack();
	}

	public Configuration GetNewConfiguration() { return _newConfig; }

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Configuration _prevConfig;
	private Configuration _newConfig = null;

	private final JButton   btnOK 				            = new JButton("OK");
	private final JButton   btnCancel 			            = new JButton("Cancel");
	private final JSpinner  spnNumberOfDragons 	            = new JSpinner();
	private final JSpinner  spnMazeSize 		            = new JSpinner();
	private final JComboBox<Dragon.Behaviour> cmbDragonMode = new JComboBox<Dragon.Behaviour>();
	private JTable	tblKeys									; // new JTable();
	private final Map<Action, Integer> keys						= new LinkedHashMap<Action, Integer>();


}
