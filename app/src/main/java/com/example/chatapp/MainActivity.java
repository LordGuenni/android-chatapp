package com.example.chatapp;



import android.health.connect.datatypes.ExerciseSegment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.fhb.fbi.msr.maus.uebung1.chatapp.AbstractMessageSender;
import de.fhb.fbi.msr.maus.uebung1.chatapp.PushClient;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AbstractMessageSender {

    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private PushClient.IOHandler mIOHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIOHandler = new PushClient.IOHandler(){
            private PrintWriter mWriter;
            @Override
            public void displayInput(String input) {
                updateConversation(input);
            }

            @Override
            public void sendOutput(String output) {
                mExecutor.execute(() -> mWriter.println(output));
            }

            @Override
            public void setOutputWriter(PrintWriter writer) {
                mWriter = writer;
                }
            };
            mExecutor.execute(new PushClient(mIOHandler)::connect);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.conversation);
    }

    @Override
    protected void processMessage(String message) {
        super.updateConversation(message);
        mIOHandler.sendOutput(message);
    }
}