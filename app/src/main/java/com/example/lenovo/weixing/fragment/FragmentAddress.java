package com.example.lenovo.weixing.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.weixing.MyView.MyLetterView;
import com.example.lenovo.weixing.R;
import com.example.lenovo.weixing.adapter.AddressAdapter;
import com.example.lenovo.weixing.object.ContractsData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FragmentAddress extends Fragment implements MyLetterView.OnTouchingLetterChangedListener{

	private TextView overlay;
	private Handler handler;
	private Dialog mLetterDialog;
	private ListView contacts_list;
	private MyLetterView letter_list;
	private List<String> contactsList=new ArrayList<String>();
	private List<ContractsData> nameList=new ArrayList<ContractsData>();
	private String[] letterList={"↑","☆","A","B","C","D","E","F","G","H","I","J"
            ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
	private String[] name={"A","B","C","D","E","F","G","H","I","J"
			,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private OverlayThread overlayThread;
	private ContractsData contractsData;
	AddressAdapter addressAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.address, null);
		contacts_list= (ListView) view.findViewById(R.id.list_contacts);

		initData();

		addressAdapter=new AddressAdapter(getContext(),R.layout.item_address,nameList);
		contacts_list.setAdapter(addressAdapter);


		letter_list= (MyLetterView) view.findViewById(R.id.list_letter);
		letter_list.setOnTouchingLetterChangeListener(this);
		handler = new Handler();
		overlayThread = new OverlayThread();
		initOverlay();
		return view;
	}

	private void initData() {
		Comparator<ContractsData> comparator = new Comparator<ContractsData>() {
			@Override
			public int compare(ContractsData o1, ContractsData o2) {
				return o1.name.compareTo(o2.name);
			}
		};
		for (int i = 0; i < 10; i++) {
			contractsData=new ContractsData();
			contractsData.setResId(R.drawable.queen);
			contractsData.setName(name[(int)(Math.random()*26)]+"myname");
			nameList.add(contractsData);
		}

		Collections.sort(nameList,comparator);
		for (int i = 0; i < 10; i++) {
			Log.e("FragmentAddress", nameList.get(i).getName() );
		}
	}



	//��ʼ������ƴ������ĸ������ʾ��
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}
	public void onTouchingLetterChanged(final String s) {
		overlay.setText(s);
		overlay.setTextColor(getResources().getColor(R.color.colorWhite));
		overlay.setBackgroundColor(getResources().getColor(R.color.colorGray));
		overlay.setVisibility(View.VISIBLE);
		handler.postDelayed(overlayThread, 1500);

		int index=addressAdapter.getPositionForSection(s);
		contacts_list.setSelection(index);
	}

	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}




}
