package baway.com.justdoit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import baway.com.justdoit.ConversationActivity;
import baway.com.justdoit.R;

public class ConversationListFragment extends Fragment implements AdapterView.OnItemClickListener {

    EaseConversationList easeConversationList;
    List<EMConversation> conversationList = new ArrayList<>();
    EaseChatInputMenu inputMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.conversation_list_fragment, null);


        //获取所有会话列表
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

            conversationList.add(entry.getValue());
        }
        //会话列表控件
        easeConversationList = (EaseConversationList) root.findViewById(R.id.list);
        easeConversationList.setOnItemClickListener(this);
        //初始化，参数为会话列表集合
        easeConversationList.init(conversationList);

        return root;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        EMConversation emConversation = (EMConversation) parent.getAdapter().getItem(position);

        intent.putExtra("name", emConversation.getLastMessage().getUserName());
        startActivity(intent);
    }

    /**
     * 刷新数据
     */
    public void refreshLayout() {

        conversationList.clear();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<EMConversation> conversationList = new ArrayList<>();
        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

            conversationList.add(entry.getValue());
        }
        easeConversationList.init(conversationList);

    }


}
