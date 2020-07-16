package com.example.myfavoritebooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    ArrayList<String> userMails;
    ArrayList<String> bookNames;
    ArrayList<String> authors;
    ArrayList<String> abouts;
    ArrayList<String> images;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_myfavoritebooks,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addnew){
            Intent intent = new Intent(HomeActivity.this,newPostActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.signout){
            firebaseAuth= FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent intent = new Intent(HomeActivity.this,SignInActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userMails=new ArrayList<>();
        bookNames=new ArrayList<>();
        authors = new ArrayList<>();
        abouts = new ArrayList<>();
        images = new ArrayList<>();
        getData();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(userMails,bookNames,authors,abouts,images,HomeActivity.this);
        recyclerView.setAdapter(recyclerAdapter);
    }
    public void getData(){
        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(HomeActivity.this, error.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                else if(value!=null){
                    for(DocumentSnapshot documentSnapshot:value.getDocuments()){
                        Map<String,Object> data = documentSnapshot.getData();
                            String email=(String)data.get("usermail");
                            String bookName=(String)data.get("bookname");
                            String author=(String)data.get("author");
                            String about=(String)data.get("about");
                            String downloadUrl=(String)data.get("downloadurl");
                            userMails.add(email);
                            bookNames.add(bookName);
                            authors.add(author);
                            abouts.add(about);
                            images.add(downloadUrl);

                            recyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}
