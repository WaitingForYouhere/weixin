package com.example.lenovo.weixing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.weixing.Activity.ChatActivity;
import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.adapter.ChatListAdapter;
import com.example.lenovo.weixing.object.Friend;

import java.util.ArrayList;
import java.util.List;


public class FragmentChat extends Fragment implements AdapterView.OnItemClickListener {

	private List<Friend> chatlist=new ArrayList<Friend>();
	private Friend friend;

	private ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.chat, null);
		listView= (ListView) view.findViewById(R.id.chat_list);
        listView.setOnItemClickListener(this);
		initData();
		ChatListAdapter adapter=new ChatListAdapter(getContext(),R.layout.chat_item,chatlist);
		listView.setAdapter(adapter);
		return view;
	}
	private void initData() {
		for (int i = 0; i <10 ; i++) {
			friend=new Friend();
			friend.setImageId(R.drawable.king);
			friend.setNickname("cst");
			friend.setLatestMessage("早点休息！");
			friend.setChatTime("昨天");
			chatlist.add(friend);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		Activity mActivity=getActivity();
        Intent chatIntent=new Intent(getContext(), ChatActivity.class);
        chatIntent.putExtra("nickname","cst");
		Toast.makeText(getContext(),"position="+position,Toast.LENGTH_SHORT).show();
        startActivity(chatIntent);
	}
}
