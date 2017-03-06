package com.emiancang.emiancang.hx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emiancang.emiancang.bean.QueryUser;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import com.hyphenate.util.DateUtils;
import com.emiancang.emiancang.App;
import com.emiancang.emiancang.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationActivity extends Activity {

	private ListView listView;

	private List<EMConversation> conversationList = new ArrayList<EMConversation>();
	private EaseConversationAdapater adapter;

	Map<Integer, QueryUser> liaisons = new HashMap();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		initHead();
	}

	private void initHead() {
		Util.setStatusBarColor(this,R.color.green);
	}

	@Override
	protected void onResume() {
		super.onResume();
		conversationList.clear();
		conversationList.addAll(loadConversationList());
		listView = (ListView) findViewById(R.id.listView);
		adapter = new EaseConversationAdapater(ConversationActivity.this, 1, conversationList);
		listView.setAdapter(adapter);
		final String st2 = getResources().getString(R.string.Cant_chat_with_yourself);
		listView.setOnItemClickListener((parent, view, position, id) -> {
			EMConversation conversation = adapter.getItem(position);
			String username = conversation.getUserName();
			if (username.equals(App.getInstance().getCurrentUserName()))
				Toast.makeText(ConversationActivity.this, st2, Toast.LENGTH_SHORT).show();
			else {
				// 进入聊天页面
				Intent intent = new Intent(ConversationActivity.this, ChatActivity.class);
				intent.putExtra("username", username);
				intent.putExtra("phone", "13810229671");
				intent.putExtra("flag", 1);
				if(liaisons.get(position) != null)
					intent.putExtra("info", liaisons.get(position));
				startActivity(intent);
			}
		});
	}

	/**
	 * 获取会话列表
	 *
	 * @return +
	 */
	protected List<EMConversation> loadConversationList() {
		// 获取所有会话，包括陌生人
		Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
		// 过滤掉messages size为0的conversation
		/**
		 * 如果在排序过程中有新消息收到，lastMsgTime会发生变化 影响排序过程，Collection.sort会产生异常
		 * 保证Conversation在Sort过程中最后一条消息的时间不变 避免并发问题
		 */
		List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
		synchronized (conversations) {
			for (EMConversation conversation : conversations.values()) {
				if (conversation.getAllMessages().size() != 0) {
					sortList.add(
							new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));

				}
			}
		}
		try {
			sortConversationByLastChatTime(sortList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<EMConversation> list = new ArrayList<EMConversation>();
		for (Pair<Long, EMConversation> sortItem : sortList) {
			list.add(sortItem.second);
		}
		return list;
	}

	/**
	 * 根据最后一条消息的时间排序
	 *
	 */
	private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
		Collections.sort(conversationList, (con1, con2) -> {

            if (con1.first == con2.first) {
                return 0;
            } else if (con2.first > con1.first) {
                return 1;
            } else {
                return -1;
            }
        });
	}

	public class EaseConversationAdapater extends ArrayAdapter<EMConversation> {
		private List<EMConversation> conversationList;
		private List<EMConversation> copyConversationList;

		private boolean notiyfyByFilter;

		protected int primaryColor;
		protected int secondaryColor;
		protected int timeColor;
		protected int primarySize;
		protected int secondarySize;
		protected float timeSize;

		public EaseConversationAdapater(Context context, int resource, List<EMConversation> objects) {
			super(context, resource, objects);
			conversationList = objects;
			copyConversationList = new ArrayList<EMConversation>();
			copyConversationList.addAll(objects);
		}

		@Override
		public int getCount() {
			return conversationList.size();
		}

		@Override
		public EMConversation getItem(int arg0) {
			if (arg0 < conversationList.size()) {
				return conversationList.get(arg0);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_conversation, parent, false);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.unreadLabel = (TextView) convertView.findViewById(R.id.unread_msg_number);
				holder.message = (TextView) convertView.findViewById(R.id.message);
				holder.time = (TextView) convertView.findViewById(R.id.time);
 				holder.msgState = convertView.findViewById(R.id.msg_state);
				holder.userAvatar = (SimpleDraweeView) convertView.findViewById(R.id.avatar);
 				convertView.setTag(holder);
			}
			// 获取与此用户/群组的会话
			EMConversation conversation = getItem(position);
			// 获取用户username或者群组groupid
			String username = conversation.getUserName();
// 			holder.name.setText(username);
			if (conversation.getUnreadMsgCount() > 0) {
				// 显示与此用户的消息未读数
				holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
				holder.unreadLabel.setVisibility(View.VISIBLE);
			} else {
				holder.unreadLabel.setVisibility(View.INVISIBLE);
			}
			if (conversation.getAllMsgCount() != 0) {
				// 把最后一条消息的内容作为item的message内容
				EMMessage lastMessage = conversation.getLastMessage();
				String content = lastMessage.getBody().toString();
				holder.message.setText(content.split("\"")[1]);
				holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
				if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
					holder.msgState.setVisibility(View.VISIBLE);
				} else {
					holder.msgState.setVisibility(View.GONE);
				}
				holder.userAvatar.setImageDrawable(getResources().getDrawable(R.drawable.default_avatar));
			}
			UserService api = ApiUtil.createDefaultApi(UserService.class);
			ViewHolder finalHolder = holder;
			ApiUtil.doDefaultApi(api.getNameAndTx(username), data -> {
				finalHolder.name.setText(data.geteSjzcXm());
				if(TextUtils.isEmpty(data.geteYhtx()))
					finalHolder.userAvatar.setImageDrawable(getResources().getDrawable(R.drawable.default_avatar));
				else
					finalHolder.userAvatar.setImageURI(Uri.parse(data.geteYhtx()));
				liaisons.put(position, data);
			});

			return convertView;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			if (!notiyfyByFilter) {
				copyConversationList.clear();
				copyConversationList.addAll(conversationList);
				notiyfyByFilter = false;
			}
		}

		public void setPrimaryColor(int primaryColor) {
			this.primaryColor = primaryColor;
		}

		public void setSecondaryColor(int secondaryColor) {
			this.secondaryColor = secondaryColor;
		}

		public void setTimeColor(int timeColor) {
			this.timeColor = timeColor;
		}

		public void setPrimarySize(int primarySize) {
			this.primarySize = primarySize;
		}

		public void setSecondarySize(int secondarySize) {
			this.secondarySize = secondarySize;
		}

		public void setTimeSize(float timeSize) {
			this.timeSize = timeSize;
		}

	}

	private static class ViewHolder {
		/** 和谁的聊天记录 */
		TextView name;
		/** 消息未读数 */
		TextView unreadLabel;
		/** 最后一条消息的内容 */
		TextView message;
		/** 最后一条消息的时间 */
		TextView time;
  		/** 最后一条消息的发送状态 */
		View msgState;
		/** 整个list中每一行总布局 */
		SimpleDraweeView userAvatar;
 
	}

}
