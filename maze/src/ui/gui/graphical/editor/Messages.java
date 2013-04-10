package ui.gui.graphical.editor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** Maze Editor external strings class. */
public class Messages {
	private static final String BUNDLE_NAME = "ui.gui.graphical.editor.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
