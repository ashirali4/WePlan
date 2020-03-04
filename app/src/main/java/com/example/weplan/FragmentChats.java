package com.example.weplan;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChats extends Fragment {


    private View chatsView;
    private RecyclerView chatList;


    private DatabaseReference databaseReference,usersRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    public FragmentChats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatsView = inflater.inflate(R.layout.fragment_fragment_chats, container, false);

        chatList=chatsView.findViewById(R.id.chats_list);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Inbox").child(currentUserId);
        usersRef=FirebaseDatabase.getInstance().getReference().child("Users");


        return chatsView;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Model> options=new FirebaseRecyclerOptions.Builder<Model>().
                setQuery(databaseReference,Model.class).build();

        FirebaseRecyclerAdapter<Model,ChatsViewHolder> adapter= new FirebaseRecyclerAdapter<Model, ChatsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsViewHolder holder, int position, @NonNull Model model) {


                final String userIds=getRef(position).getKey();
                //converted this to array because otherwise it will always return the last image of a contacs list from firebase
                final String[] imageReturned = {"defaulImage"};

                usersRef.child(userIds).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.hasChild("image")) {
                                imageReturned[0] = dataSnapshot.child("image").getValue().toString();
                                Picasso.get().load(imageReturned[0]).into(holder.dp);

                            }

                            final String nameReturned = dataSnapshot.child("name").getValue().toString();
                            //  final String statusReturned = dataSnapshot.child("status").getValue().toString();
                            holder.userName.setText(nameReturned);
                            holder.lastSeen.setText("last seen:" + "\n" + "date" + "time");

                            if(dataSnapshot.child("userState").hasChild("state"))
                            {
                                String state=dataSnapshot.child("userState").child("state").getValue().toString();
                                String date=dataSnapshot.child("userState").child("date").getValue().toString();
                                String time=dataSnapshot.child("userState").child("time").getValue().toString();

                                if(state.equals("online"))
                                {
                                    holder.onlineIcon.setVisibility(View.VISIBLE);
                                    holder.lastSeen.setText("Online");
                                }
                                else if(state.equals("offline"))
                                {
                                    holder.onlineIcon.setVisibility(View.INVISIBLE);
                                    holder.lastSeen.setText("last seen :" + date +" "+ time);

                                }

                            }
                            else {
                                holder.lastSeen.setText("offline");
                            }


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent=new Intent(getContext(),ChatActivity.class);
                                    intent.putExtra("receiverUid", userIds);
                                    intent.putExtra("clickedUserName", nameReturned);
                                    intent.putExtra("receiverImage", imageReturned[0]);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                    usersRef.child(userIds).addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            if(dataSnapshot.hasChild("image"))
//                            {
//                                String imageReturned=dataSnapshot.child("image").getValue().toString();
//                                Picasso.get().load(imageReturned).into(holder.dp);
//
//                            }
//
//                            String nameReturned=dataSnapshot.child("name").getKey().toString();
//                            holder.userName.setText(nameReturned);
//                            holder.lastSeen.setText("last seen:"+"\n"+"date"+"time");
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
            }


            @NonNull
            @Override
            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display, parent, false);
                return new ChatsViewHolder(view);
            }

        };

        chatList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class ChatsViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView dp;
        TextView userName,lastSeen;
        ImageView onlineIcon;
        public ChatsViewHolder(@NonNull View itemView) {

            super(itemView);

            dp=itemView.findViewById(R.id.users_profile_image);
            userName=itemView.findViewById(R.id.user_name);
            lastSeen=itemView.findViewById(R.id.last_seen);
            onlineIcon=itemView.findViewById(R.id.user_status);

        }
    }

}
