package com.example.myfavoritebooks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostHolder> {
    private ArrayList<String> userMails;
    private ArrayList<String> bookNames;
    private ArrayList<String> authors;
    private ArrayList<String> abouts;
    private ArrayList<String> images;
    private Context context;

    public RecyclerAdapter(ArrayList<String> userMails, ArrayList<String> bookNames, ArrayList<String> authors, ArrayList<String> abouts, ArrayList<String> images, Context context) {
        this.userMails = userMails;
        this.bookNames = bookNames;
        this.authors = authors;
        this.abouts = abouts;
        this.images = images;
        this.context=context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycleriew_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, final int position) {
        holder.userMailText.setText(userMails.get(position));
        holder.bookNameText.setText(bookNames.get(position));
        holder.authorText.setText(authors.get(position));
        if(abouts.get(position).length()>100){
            holder.aboutText.setText(abouts.get(position).substring(0,100)+"...");
        }
        else {
            holder.aboutText.setText(abouts.get(position));
        }
        Picasso.get().load(images.get(position)).resize(400,400).centerCrop().rotate(90).into(holder.bookImage);
        holder.bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(abouts.get(position));
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("userMail",userMails.get(position));
                intent.putExtra("bookName",bookNames.get(position));
                intent.putExtra("author",authors.get(position));
                intent.putExtra("about",abouts.get(position));
                intent.putExtra("imageUrl",images.get(position));
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return userMails.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView userMailText,authorText,bookNameText,aboutText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.bookImage);
            userMailText = itemView.findViewById(R.id.userMailText);
            authorText = itemView.findViewById(R.id.authorText);
            bookNameText = itemView.findViewById(R.id.bookNameText);
            aboutText = itemView.findViewById(R.id.aboutText);

        }


    }
}
