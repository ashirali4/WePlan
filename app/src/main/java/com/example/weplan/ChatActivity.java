package com.example.weplan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


    private String receiverName,receiverId,receiverImageString,senderId;
    private String currentDate,currentTime;
    TextView receiverNameText,userInfo;
    private CircleImageView receiverImage;
    private Toolbar chatToolbar;

    private DatabaseReference Rootref;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private EditText MessaegInput;
    private ImageButton sendImgButton,sendFilesButton;
    private String selectedOption="",myUrl="";

    private  List<Messages> messagesList;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        firebaseAuth= FirebaseAuth.getInstance();
        senderId=firebaseAuth.getCurrentUser().getUid();
        Rootref= FirebaseDatabase.getInstance().getReference();


        Initialization();


        receiverNameText.setText(receiverName);
        // Picasso.get().load(receiverImageString).placeholder(R.drawable.ic_person_black_24dp).into(receiverImage);



        sendImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(MessaegInput.toString()))
                {
                    Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
                else
                    sendMessage();
            }
            private void sendMessage() {

                String messageSenderRef="Message/"+senderId+"/"+receiverId;
                String messageReceiverRef="Message/"+receiverId+"/"+senderId;

                DatabaseReference userMessageRef=Rootref.child("Message").child(senderId).child(receiverId).push();
                String messagePushKey=userMessageRef.getKey();


                Map message=new HashMap<>();
                message.put("message",MessaegInput.getText().toString());
                message.put("type","Text");
                message.put("to",receiverId);
                message.put("messageId",messagePushKey);    //in case if to del a message
                message.put("from",senderId);
                message.put("time",currentTime);
                message.put("date",currentDate);

                Map  messageBody=new HashMap<>();

                messageBody.put(messageSenderRef+"/"+messagePushKey,message);
                messageBody.put(messageReceiverRef+"/"+messagePushKey,message);

                Rootref.updateChildren(messageBody).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            MessaegInput.setText("");
                        }
                    }
                });
            }
        });


    }




    private void Initialization() {

        receiverId=getIntent().getExtras().get("receiverUid").toString();
        receiverName=getIntent().getExtras().get("clickedUserName").toString();
        receiverImageString=getIntent().getExtras().get("receiverImage").toString();


        progressDialog=new ProgressDialog(this);

        //to display image/name on toolbar

        chatToolbar=(Toolbar)findViewById(R.id.chat_toolbar);
        chatToolbar.setTitle("");
        setSupportActionBar(chatToolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView=layoutInflater.inflate(R.layout.chat_bar_layout,null);
        actionBar.setCustomView(actionBarView);


        //show receiver info on activity bar
        receiverNameText=findViewById(R.id.receiver_name);
        userInfo=findViewById(R.id.user_info);
        receiverImage=findViewById(R.id.users_profile_image);

        //message to be send and send button
        sendImgButton=findViewById(R.id.send_img_btn);
        sendFilesButton=findViewById(R.id.send_files_btn);
        MessaegInput=findViewById(R.id.message_input);

        //show messages in inbox chat
//for(int i=0;i<3;i++)
//{//mera msh ni mil rha ? any desk pa
//    Messages messages1=new Messages("fsd","heyy","text","hgh","hjh","jhj","ftyu","");
//    messagesList.add(messages1);
//}
//        messageAdapter=new MessageAdapter(messagesList);
        userMessagesList=findViewById(R.id.inbox_messages);
        messagesList = new ArrayList<>();
//        linearLayoutManager=new LinearLayoutManager(this);
//        userMessagesList.setLayoutManager(linearLayoutManager);
//        userMessagesList.setAdapter(messageAdapter);


        Calendar calendarDate=Calendar.getInstance();
        SimpleDateFormat currentDateFormat=new SimpleDateFormat("MMM dd,yyyy");
        currentDate=currentDateFormat.format(calendarDate.getTime());

        Calendar calendarTime=Calendar.getInstance();
        SimpleDateFormat currentTimeFormat=new SimpleDateFormat("hh:mm a");  //a is am or pm (12 hours)
        currentTime=currentTimeFormat.format(calendarTime.getTime());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Message").child(senderId).child(receiverId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                messagesList.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String message = snapshot.child("message").getValue().toString();
                        String type = snapshot.child("type").getValue().toString();
                        String to = snapshot.child("to").getValue().toString();
                        String messageId = snapshot.child("messageId").getValue().toString();
                        String from = snapshot.child("from").getValue().toString();
                        String time = snapshot.child("time").getValue().toString();
                        String date = snapshot.child("date").getValue().toString();

                        Messages messages = new Messages();
                        messages.setDate(date);
                        messages.setFrom(from);
                        messages.setMessage(message);
                        messages.setMessageId(messageId);
                        messages.setType(type);
                        messages.setTo(to);
                        messages.setTime(time);

                        messagesList.add(messages);
                    }
                    messageAdapter=new MessageAdapter(messagesList);
                    linearLayoutManager=new LinearLayoutManager(ChatActivity.this);
                    userMessagesList.setLayoutManager(linearLayoutManager);
                    userMessagesList.setAdapter(messageAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
