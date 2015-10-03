package examples.android.com.socialatchangi.model;

/**
 * Created by madhur on 17/01/15.
 */
public class ChatMessage {

    private String messageText;
    private Status messageStatus;
    private String author;
    private long messageTime;
    private Avatar avatar;

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageStatus(Status messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageText() {

        return messageText;
    }

    public Status getMessageStatus() {
        return messageStatus;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
