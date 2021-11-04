package com.example.chatapplication.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS  = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERANCE_NAME ="chatAppPreferance";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID  = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN ="fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID ="senderId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME ="senderName";
    public static final String KEY_RECEIVER_NAME ="receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABLITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MCG_CONTENT_TYPE ="Content-Type";
    public static final String REMOTE_MCG_DATE ="date";
    public static final String REMOTE_MCG_REGISTRATION_IDS = "registration_ids";

    public static HashMap<String , String> remoteMsgHeaders = null;
    public static HashMap<String , String> getRemoteMsgHeaders()
    {
        if(remoteMsgHeaders == null )
        {
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(REMOTE_MSG_AUTHORIZATION,"key=AAAAlBPtz5o:APA91bF9N6jeMMHt3MzU7tQQ7_gIdk3cDNyuuqSCObn_RG-0_fl4EdRf3h90yy0EVA4FjF40SwDcZTH4Lwlz7Mx5BrXr3qAx9kZrpT_XZsHK-3TMizXWyqIPAdmwl9DdbIHdF6vpuiJT");
            remoteMsgHeaders.put(REMOTE_MCG_CONTENT_TYPE,"application/json");
        }
        return remoteMsgHeaders;
    }



}
