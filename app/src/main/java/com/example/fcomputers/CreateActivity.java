package com.example.fcomputers;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // nuevo import de toolbar
//import android.widget.Toolbar; //antiguo toolbar
import android.os.Bundle;

import com.example.fcomputers.models.ComputerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fcomputers.databinding.ActivityCreateBinding;
import com.google.firebase.firestore.DocumentReference;

public class CreateActivity extends BaseActivity {
    FloatingActionButton fab_create_save, fab_create_clear, fab_create_back;
    ImageView iv_create_image;
    TextView tv_create_click_image;
    EditText et_create_serial, et_create_brand, et_create_description;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        super.init();
        init();

        fab_create_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToList();
            }
        });

        fab_create_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        fab_create_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serial, description, brand;
                boolean active;

                serial = et_create_serial.getText().toString();
                description = et_create_description.getText().toString();
                brand = et_create_brand.getText().toString();

                if(serial.isEmpty() || description.isEmpty() || brand.isEmpty()){
                    makeSimpleAlertDialog("Info", "Please fill all fields");
                }else{
                    model = new ComputerModel();
                    model.setActive(true);
                    model.setDescription(description);
                    model.setBrand(brand);
                    model.setSerial(serial);

                    save(model);

                }

            }
        });
    }

    private void save(ComputerModel model) {
        if (collectionReference != null){
            collectionReference.add(model)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                if (task.getResult() != null) {
                                    makeSimpleAlertDialog("Success","Computer Saved");
                                }else{
                                    makeSimpleAlertDialog("Warning", "Computer not saved");
                                }
                            }else{
                                makeSimpleAlertDialog("Error", task.getException().getMessage());
                            }
                        }
                    });
        }else{
            makeSimpleAlertDialog("Error", "No Database Connection");
        }
    }

    protected void init(){
            fab_create_back = findViewById(R.id.fab_create_back);
            fab_create_save = findViewById(R.id.fab_create_save);
            fab_create_clear = findViewById(R.id.fab_create_clear);
            iv_create_image = findViewById(R.id.iv_create_image);
            tv_create_click_image = findViewById(R.id.tv_create_click_image);
            et_create_serial = findViewById(R.id.et_create_serial);
            et_create_brand = findViewById(R.id.et_create_brand);
            et_create_description = findViewById(R.id.et_create_description);
    }

    private void clear(){
        et_create_brand.setText("");
        et_create_description.setText("");
        et_create_serial.setText("");

        et_create_serial.requestFocus();

        iv_create_image.setImageResource(R.drawable.id_computer_black_18dp);
    }

}