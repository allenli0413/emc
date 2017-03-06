package com.emiancang.emiancang.hx.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.emiancang.emiancang.bean.QueryUser;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.hx.Constant;
import com.emiancang.emiancang.hx.utils.EaseCommonUtils;


import java.util.List;

public class ChatActivity extends Activity {
    private ImageView leftImage;
    private ListView listView;
    private int chatType = 1;
    private String toChatUsername;
    private String key;
    private Button btn_send;
    private EditText et_content;
    private List<EMMessage> msgList;
    MessageAdapter adapter;
    private EMConversation conversation;
    protected int pagesize = 20;

    //区分是客服人员还是普通用户，客服人员为1，普通用户为0
    int flag = 0;
    //会话列表传来的对话人信息
    QueryUser info;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
        Util.setStatusBarColor(this,R.color.green);
        toChatUsername = this.getIntent().getStringExtra("username");
        key = this.getIntent().getStringExtra("key");
        flag = getIntent().getIntExtra("flag", 0);
        info = (QueryUser) getIntent().getSerializableExtra("info");
        leftImage = (ImageView) this.findViewById(R.id.left_image);
        leftImage.setOnClickListener(v -> finish());
        TextView tv_toUsername = (TextView) this.findViewById(R.id.tv_toUsername);
        if(flag == 1 && info != null)
            tv_toUsername.setText(info.geteSjzcXm());
        else
            tv_toUsername.setText(toChatUsername);
        listView = (ListView) this.findViewById(R.id.listView);
        btn_send = (Button) this.findViewById(R.id.btn_send);
        et_content = (EditText) this.findViewById(R.id.et_content);
        getAllMessage();
        msgList = conversation.getAllMessages();
        adapter = new MessageAdapter(ChatActivity.this);
        adapter.setMsgs(msgList);
        listView.setAdapter(adapter);
        listView.setSelection(listView.getCount() - 1);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        btn_send.setOnClickListener(v -> {
            String content = et_content.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {

                return;
            }
            setMesaage(content);
        });
    }

    protected void getAllMessage() {
        // 获取当前conversation对象
        String text = "";
        if(!TextUtils.isEmpty(key))
            text = key;
        else
            text = toChatUsername;
        conversation = EMClient.getInstance().chatManager().getConversation(text,
                EaseCommonUtils.getConversationType(chatType), true);
        // 把此会话的未读数置为0
        conversation.markAllMessagesAsRead();
        // 初始化db时，每个conversation加载数目是getChatOptions().getNumberOfMessagesLoaded
        // 这个数目如果比用户期望进入会话界面时显示的个数不一样，就多加载一些
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }

    private void setMesaage(String content) {
        String text = "";
        if(!TextUtils.isEmpty(key))
            text = key;
        else
            text = toChatUsername;
        // 创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, text);
        // 如果是群聊，设置chattype，默认是单聊
        if (chatType == Constant.CHATTYPE_GROUP)
            message.setChatType(ChatType.GroupChat);
        // 发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        msgList.add(message);
        adapter.setMsgs(msgList);
        adapter.notifyDataSetChanged();
        if (msgList.size() > 0) {
            listView.setSelection(listView.getCount() - 1);
        }
        et_content.setText("");
        et_content.clearFocus();
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {

            runOnUiThread(() -> {
                for (EMMessage message : messages) {
                    String username = null;
                    // 群组消息
                    if (message.getChatType() == ChatType.GroupChat || message.getChatType() == ChatType.ChatRoom) {
                        username = message.getTo();
                    } else {
                        // 单聊消息
                        username = message.getFrom();
                    }
                    // 如果是当前会话的消息，刷新聊天页面
                    if (flag == 0){
                        if (username.equals(key)) {
                            msgList.addAll(messages);
                            adapter.setMsgs(msgList);
                            adapter.notifyDataSetChanged();
                            if (msgList.size() > 0) {
                                listView.setSelection(listView.getCount() - 1);
                            }
                        }
                    }
                    if(flag == 1){
                        if (username.equals(toChatUsername)) {
                            msgList.addAll(messages);
                            adapter.setMsgs(msgList);
                            adapter.notifyDataSetChanged();
                            if (msgList.size() > 0) {
                                listView.setSelection(listView.getCount() - 1);
                            }
                        }
                    }
                }
            });

            // 收到消息
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            // 收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            // 收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            // 收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            // 消息状态变动
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @SuppressLint("InflateParams")
    class MessageAdapter extends BaseAdapter {
        private List<EMMessage> msgs;
        private Context context;
        private LayoutInflater inflater;

        public MessageAdapter(Context context_) {
            this.context = context_;
            inflater = LayoutInflater.from(context);
        }

        public List<EMMessage> getMsgs() {
            return msgs;
        }

        public void setMsgs(List<EMMessage> msgs) {
            this.msgs = msgs;
        }

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public EMMessage getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            EMMessage message = getItem(position);
            return message.direct() == EMMessage.Direct.RECEIVE ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EMMessage message = getItem(position);
            int viewType = getItemViewType(position);
            if (convertView == null) {
                if (viewType == 0) {
                    convertView = inflater.inflate(R.layout.item_message_received, parent, false);
                } else {
                    convertView = inflater.inflate(R.layout.item_message_sent, parent, false);
                }
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.tv = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                holder.iv = (SimpleDraweeView) convertView.findViewById(R.id.iv_userhead);
                convertView.setTag(holder);
            }

            EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
            holder.tv.setText(txtBody.getMessage());
            if(viewType == 0) {
                if(info == null)
                    holder.iv.setImageDrawable(getResources().getDrawable(R.drawable.default_avater));
                else
                    holder.iv.setImageURI(Uri.parse(info.geteYhtx()));
            }else
                holder.iv.setImageURI(Uri.parse(UserManager.getInstance().getUser().getEYhtx()));
            return convertView;
        }

    }

    public static class ViewHolder {

        TextView tv;
        SimpleDraweeView iv;

    }


}
