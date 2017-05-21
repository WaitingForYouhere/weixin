package com.example.lenovo.weixing.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.adapter.ChatListAdapter;
import com.example.lenovo.weixing.object.Friend;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity implements View.OnClickListener{
    private List<Friend> chatlist=new ArrayList<Friend>();
    private Friend friend;

    private ImageButton bt_back;
    private ImageButton bt_search;
    private ImageButton bt_delete;
    private Button bt_group;
    private Button bt_more;
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;
    private Button bt_5;
    private Button bt_6;
    private EditText input;
    private ListView groupList;
    private ListView chatList;
    private TextView inputText;

    private LinearLayout find_content;
    private LinearLayout find_group;
    private LinearLayout find_chat;
    private LinearLayout search_else;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();


    }

    private void initView() {
        bt_back= (ImageButton) findViewById(R.id.back);
        bt_search= (ImageButton) findViewById(R.id.searchby_input);
        bt_delete= (ImageButton) findViewById(R.id.say_or_delete);
        bt_group= (Button) findViewById(R.id.search_more_group);
        bt_more= (Button) findViewById(R.id.search_chat);
        bt_1=(Button) findViewById(R.id.bt_1);
        bt_2=(Button) findViewById(R.id.bt_2);
        bt_3=(Button) findViewById(R.id.bt_3);
        bt_4=(Button) findViewById(R.id.bt_4);
        bt_5=(Button) findViewById(R.id.bt_5);
        bt_6=(Button) findViewById(R.id.bt_6);
        search_else= (LinearLayout) findViewById(R.id.search_else);
        input= (EditText) findViewById(R.id.search_input);
        groupList =(ListView) findViewById(R.id.chat_together_list);
        chatList=(ListView) findViewById(R.id.find_chat_list);
        inputText=(TextView) findViewById(R.id.input_text);
        find_content=(LinearLayout) findViewById(R.id.search_content);
        find_group=(LinearLayout) findViewById(R.id.search_group_layout);
        find_chat=(LinearLayout) findViewById(R.id.search_chat_layout);

        bt_back.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_group.setOnClickListener(this);
        bt_more.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        search_else.setOnClickListener(this);

        initData();
        ChatListAdapter adapter=new ChatListAdapter(this,R.layout.chat_item,chatlist);
        groupList.setAdapter(adapter);
        chatList.setAdapter(adapter);


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputText.setText(s);
                    find_content.setVisibility(View.GONE);
                    find_group.setVisibility(View.VISIBLE);
                    find_chat.setVisibility(View.VISIBLE);
                    search_else.setVisibility(View.VISIBLE);
                    bt_delete.setBackground(getResources().getDrawable(R.drawable.delete,null));


            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(input.getText().toString().trim())){
                    find_content.setVisibility(View.VISIBLE);
                    find_group.setVisibility(View.GONE);
                    find_chat.setVisibility(View.GONE);
                    search_else.setVisibility(View.GONE);
                    bt_delete.setBackground(getResources().getDrawable(R.drawable.mic,null));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Searchbyclassify.class);
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.searchby_input:
                String text=input.getText().toString();
                inputText.setText(text);
                getSearch();
                break;
            case R.id.say_or_delete:
                input.setText("");
                break;
            case R.id.search_more_group:
                break;
            case R.id.search_chat:
                break;
            case R.id.search_else:
                intent.putExtra("button","haha");
                startActivity(intent);
                break;
            case R.id.bt_1:
                intent.putExtra("button","bt_1");
                startActivity(intent);
                break;
            case R.id.bt_2:
                intent.putExtra("button","bt_2");
                startActivity(intent);
                break;
            case R.id.bt_3:
                intent.putExtra("button","bt_3");
                startActivity(intent);
                break;
            case R.id.bt_4:
                intent.putExtra("button","bt_4");
                startActivity(intent);
                break;
            case R.id.bt_5:
                intent.putExtra("button","bt_5");
                startActivity(intent);
                break;
            case R.id.bt_6:
                intent.putExtra("button","bt_6");
                startActivity(intent);
                break;
        }
    }

    private void getSearch() {
    }
    private void initData() {
        for (int i = 0; i <2 ; i++) {
            friend=new Friend();
            friend.setImageId(R.drawable.king);
            friend.setNickname("cst");
            friend.setLatestMessage("早点休息！");
            friend.setChatTime("昨天");
            chatlist.add(friend);
        }
    }
}
