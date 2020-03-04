package com.example.weplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private List<Messages> messagesList;

    public MessageAdapter(List<Messages> messagesList) {
        this.messagesList=messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //set complete view of a message (gets complete view of message layout)
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_layout,parent,false);

        firebaseAuth= FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {

        String senderId=firebaseAuth.getCurrentUser().getUid();
        Messages messages=messagesList.get(position);

        final String fromUserId=messages.getFrom();
        final String messageType=messages.getType();

        //now set what the message is
        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.hasChild("image"))        //check if user has image
                {
                    //updates receiver image in messages
                    String recevierImage=dataSnapshot.child("image").getValue().toString();
                    //  Picasso.get().load(recevierImage).placeholder(R.drawable.ic_person_black_24dp).into(holder.receiverProfileImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.receiverMessage.setVisibility(View.GONE);
        holder.receiverProfileImage.setVisibility(View.GONE);
        holder.senderMessage.setVisibility(View.GONE);
        holder.messageImageSender.setVisibility(View.GONE);
        holder.messageImageReceiver.setVisibility(View.GONE);


        if(messageType.equals("Text"))
        {
//            holder.receiverMessage.setVisibility(View.INVISIBLE);
//            holder.receiverProfileImage.setVisibility(View.INVISIBLE);
//            holder.senderMessage.setVisibility(View.INVISIBLE);


            if(fromUserId.equals(senderId))
            {
                holder.senderMessage.setVisibility(View.VISIBLE);
                holder.senderMessage.setBackgroundResource(R.drawable.sender_messages_layout);
                holder.senderMessage.setText(messages.getMessage()+ "\n \n"+ messages.getTime()+" - "+messages.getDate());
            }
            else
            {
                holder.receiverMessage.setVisibility(View.VISIBLE);
                holder.receiverProfileImage.setVisibility(View.VISIBLE);

                holder.receiverMessage.setBackgroundResource(R.drawable.receiver_messages_layout);
                holder.receiverMessage.setText(messages.getMessage()+ "\n \n"+ messages.getTime()+" - "+messages.getDate());
            }
        }

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView senderMessage,receiverMessage;
        public CircleImageView receiverProfileImage;
        public ImageView messageImageSender,messageImageReceiver;

        public MessageViewHolder(@NonNull View itemView) {

            super(itemView);

            senderMessage=itemView.findViewById(R.id.sender_messages);
            receiverProfileImage=itemView.findViewById(R.id.receiver_image);
            receiverMessage=itemView.findViewById(R.id.receiver_messages);
            messageImageSender=itemView.findViewById(R.id.message_sender_image);
            messageImageReceiver=itemView.findViewById(R.id.message_receiver_image);

        }
    }
}

