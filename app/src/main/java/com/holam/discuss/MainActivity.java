package com.holam.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final int SIGN_IN = 1;
    private ConstraintLayout activity_main;
    private FirebaseListAdapter <Message> adapter;
    private ImageButton sendBtn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN)
        {
            if (resultCode == RESULT_OK)
            {
                Snackbar.make(activity_main, "You are already authed", Snackbar.LENGTH_SHORT).show();
                displayAllMessages();
            }
            else {
                Snackbar.make(activity_main, "You are not already authed", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = findViewById(R.id.activity_main);
        sendBtn = findViewById(R.id.sendButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                EditText textField = findViewById(R.id.InputLayout);
                if (textField.getText().toString() == "")
                    return;
                FirebaseDatabase.getInstance().getReference().push().setValue(
                        new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                textField.getText().toString())
                );
                textField.setText("");
            }

        });
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN);
        else
            Snackbar.make(activity_main, "You are already authed", Snackbar.LENGTH_SHORT).show();

        displayAllMessages();

    }

private void displayAllMessages()
{
    ListView ListOfMessages = findViewById(R.id.MessageList);
    adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()){
        @Override
        protected void populateView(View v, Message model, int position) {
            TextView message_user, message_time, message_text;
            message_user = v.findViewById(R.id.username);
            message_time = v.findViewById(R.id.message_time);
            message_text = v.findViewById(R.id.message_text);

            message_user.setText(model.getUserName());
            message_text.setText(model.getUserMessage());
            message_time.setText(DateFormat.format("dd-mm-yy HH:mm:ss", model.getMessageTime()));
        }
    };

    ListOfMessages.setAdapter(adapter);
}

}