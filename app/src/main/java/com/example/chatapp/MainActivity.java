package com.example.chatapp;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.fhb.fbi.msr.maus.uebung1.chatapp.AbstractMessageSender;
import de.fhb.fbi.msr.maus.uebung1.chatapp.PushClient;

import java.io.PrintWriter;

public class MainActivity extends AbstractMessageSender {

    private class IOHandler implements PushClient.IOHandler {
        private PrintWriter writer;
        @Override
        public void displayInput(String input) {
            updateConversation(input);
        }

        @Override
        public void sendOutput(String output) {
            writer.write(output);
        }

        @Override
        public void setOutputWriter(PrintWriter writer) {
            this.writer = writer;
        }
    }
    private IOHandler ioHandler = new IOHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushClient client = new PushClient(ioHandler);
        client.connect();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.conversation);
    }

    @Override
    protected void processMessage(String message) {
        super.updateConversation(message);
        ioHandler.sendOutput(message);
    }
}