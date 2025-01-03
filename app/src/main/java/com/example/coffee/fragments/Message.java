package com.example.coffee.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffee.Adapter.Message_adapter;
import com.example.coffee.Models.Message_list;
import com.example.coffee.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class Message extends Fragment {



    RecyclerView recyclerView;
    TextView send;
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText editText;
    List<Message_list> messageLists;
    Message_adapter adapter;
    ImageView gallery;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        send=view.findViewById(R.id.send);
        editText=view.findViewById(R.id.messageEditText);
        gallery=view.findViewById(R.id.gellery);



        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opengallery();
            }
        });


        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        messageLists=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter=new Message_adapter(getContext(),messageLists);
        recyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messag = editText.getText().toString().trim();

                if (!messag.isEmpty()){
                     String timestamp = getCurrentTime();

                     Message_list messageList=new Message_list("user",messag,timestamp);
                     messageLists.add(messageList);
//                   adapter.notifyItemRangeInserted(messageLists.size() - 1);
                     recyclerView.scrollToPosition(messageLists.size() - 1);
                     editText.setText("");

                }

            }

        });
        
        return view;
    }
    private void opengallery() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);

        }else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();
            // Set the selected image to ImageView
        } else {
            Toast.makeText(getContext(), "Image selection failed", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }

}