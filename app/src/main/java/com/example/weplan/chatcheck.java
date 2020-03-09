package com.example.weplan;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.fragment.MobiComQuickConversationFragment;

public class chatcheck extends FragmentActivity {
    ConversationUIService conversationUIService;
    MobiComQuickConversationFragment mobiComQuickConversationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatcheck);

        conversationUIService=new ConversationUIService(this);
        mobiComQuickConversationFragment=conversationUIService.getQuickConversationFragment();


    }
}
