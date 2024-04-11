package de.fhb.fbi.msr.maus.uebung1.chatapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.example.chatapp.R;

public abstract class AbstractMessageSender extends Activity {

	/**
	 * logging
	 */
	protected static final String logger = "AbstractMessageSender";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView();

		// the entry field for the message
		EditText newMessage = (EditText) findViewById(R.id.new_message);
		Log.i(logger,"Eingabefeld: " + newMessage);

		newMessage.setImeOptions(EditorInfo.IME_ACTION_DONE);

		// Falls eine neue Nachricht eingegeben wird und mittels "done" beendet
		// wird, wird die Activity Callee aufgerufen
		
		newMessage.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView view, int arg1, KeyEvent arg2) {
				Log.d(logger, "got editor action " + arg1 + " on textview "
						+ view + ". KeyEvent is: " + arg2);
				if (arg1 == EditorInfo.IME_ACTION_DONE) {
					Log.d(logger, "done was pressed!");

					// access the typed text
					String message = view.getText().toString();

					// do something with the typed text
					processMessage(message);

					view.setText("");

					return false;
				}
				return false;
			}

		});

	}

	/**
	 * subclasses can choose the view/layout. The view needs to provide at least
	 * the two sub-element accessed here via findViewById()
	 */
	protected abstract void setContentView();

	/**
	 * this method updates the content of the message exchange, i.e. new messages
	 * are appended as a new line
	 */
	protected void updateConversation(String message) {
		// repeated access to the message exchange via layout
		TextView conversation = (TextView) findViewById(R.id.conversation_transcript);
		conversation.append("\n" + message);
	}

	/**
	 * example of pattern "Abstract Method"
	 * 
	 * responsible for processing the message text which was typed into the input field of the app
	 * 
	 * @param message
	 */
	protected abstract void processMessage(String message);

}
