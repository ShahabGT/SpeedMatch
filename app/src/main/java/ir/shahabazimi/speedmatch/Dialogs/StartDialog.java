package ir.shahabazimi.speedmatch.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.shahabazimi.speedmatch.Classes.MyToast;
import ir.shahabazimi.speedmatch.Classes.UsernameListener;
import ir.shahabazimi.speedmatch.R;

public class StartDialog extends Dialog {

    private Context context;
    private UsernameListener listener;
    private Button start;
    private EditText name;

    public StartDialog(@NonNull Context context,UsernameListener listener) {
        super(context);

        this.context=context;
        this.listener=listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdialog);

        name = findViewById(R.id.startdialog_name);
        start = findViewById(R.id.startdialog_start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()){
                    listener.setUserName(name.getText().toString());
                    dismiss();
                }else{
                    MyToast.Create(context,context.getString(R.string.startdialog_error));
                }
            }
        });


    }

}
