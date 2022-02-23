package com.example.emailwithattachment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PickFromGallery = 101;
    EditText editText, editText2, editText3;
    Button button, button2;
    TextView textView;
    String to, subject, message, attachment;
    Uri URI = null;
    int columnIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PickFromGallery && resultCode == RESULT_OK) {
            URI = data.getData();
            textView.setText(String.format("Attached File:\n%s", URI));
        }
    }
    public void openFolder() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent,
                "Complete action using.."), PickFromGallery);
    }
    public void sendEmail() {
        try {
            to = editText.getText().toString();
            subject = editText2.getText().toString();
            message = editText3.getText().toString();
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (URI != null)
                intent.putExtra(Intent.EXTRA_STREAM, URI);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            this.startActivity(Intent.createChooser(intent,
                    "Sending E-mail.."));
        } catch (Throwable throwable) {
            Toast.makeText(this, "Request failed! Try again."
                    + throwable.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
