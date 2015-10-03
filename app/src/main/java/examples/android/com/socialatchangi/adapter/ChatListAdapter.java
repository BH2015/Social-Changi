package examples.android.com.socialatchangi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.pkmmte.view.CircularImageView;

import java.text.SimpleDateFormat;

import examples.android.com.socialatchangi.R;
import examples.android.com.socialatchangi.model.ChatMessage;
import examples.android.com.socialatchangi.model.Person;
import examples.android.com.socialatchangi.model.Status;
import examples.android.com.socialatchangi.widget.Emoji;
import in.co.madhur.chatbubblesdemo.AndroidUtilities;

/**
 * Created by madhur on 17/01/15.
 */
public class ChatListAdapter extends FirebaseListAdapter<ChatMessage> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private Person mPerson;
    private Context context;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm");

    public ChatListAdapter(Query ref, Context context, Person mPerson) {
        super(ref, ChatMessage.class, context);
        this.context = context;
        this.mPerson = mPerson;
    }

    @Override
    public View populateView(View convertView, ChatMessage message) {
        View v = null;
        ViewHolder1 holder1;
        ViewHolder2 holder2;

        if (!message.getAuthor().equals(mPerson.getDisplayName())) {
            if (convertView == null) {
                v = LayoutInflater.from(context).inflate(R.layout.chat_user1_item, null, false);
                holder1 = new ViewHolder1();


                holder1.messageTextView = (TextView) v.findViewById(R.id.message_text);
                holder1.timeTextView = (TextView) v.findViewById(R.id.time_text);
                holder1.replyAuthor = (TextView) v.findViewById(R.id.chat_company_reply_author);
                holder1.replyAvatar = (CircularImageView) v.findViewById(R.id.reply_author_avatar);

                v.setTag(holder1);
            } else {
                v = convertView;
                holder1 = (ViewHolder1) v.getTag();

            }

            holder1.messageTextView.setText(Emoji.replaceEmoji(message.getMessageText(), holder1.messageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(16)));
            holder1.timeTextView.setText(SIMPLE_DATE_FORMAT.format(message.getMessageTime()));
            holder1.replyAuthor.setText(message.getAuthor());
            holder1.replyAvatar.setImageDrawable(context.getResources().getDrawable(message.getAvatar().getDrawableId()));

        } else {

            if (convertView == null) {
                v = LayoutInflater.from(context).inflate(R.layout.chat_user2_item, null, false);

                holder2 = new ViewHolder2();


                holder2.messageTextView = (TextView) v.findViewById(R.id.message_text);
                holder2.timeTextView = (TextView) v.findViewById(R.id.time_text);
                holder2.messageStatus = (ImageView) v.findViewById(R.id.user_reply_status);
                v.setTag(holder2);

            } else {
                v = convertView;
                holder2 = (ViewHolder2) v.getTag();

            }

            holder2.messageTextView.setText(Emoji.replaceEmoji(message.getMessageText(), holder2.messageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(16) ));
            //holder2.messageTextView.setText(message.getMessageText());
            holder2.timeTextView.setText(SIMPLE_DATE_FORMAT.format(message.getMessageTime()));

            if (message.getMessageStatus() == Status.DELIVERED) {
                holder2.messageStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_double_tick));
            } else if (message.getMessageStatus() == Status.SENT) {
                holder2.messageStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_single_tick));

            }


        }


        return v;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = (ChatMessage) getItem(position);
        return message.getAuthor().equals(mPerson.getDisplayName()) ? 0 : 1;
    }

    private class ViewHolder1 {
        public TextView messageTextView;
        public TextView timeTextView;
        public TextView replyAuthor;
        public CircularImageView replyAvatar;


    }

    private class ViewHolder2 {
        public ImageView messageStatus;
        public TextView messageTextView;
        public TextView timeTextView;

    }
}
