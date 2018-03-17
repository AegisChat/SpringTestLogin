package atchat.aegis.com.myapplication.FCMessaging;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import application.Message.FoundPartnerMessage;
import application.Message.GetNewMessagesMessage;
import application.Message.Message;
import application.Message.MessageInterface;
import application.Message.TextMessage;
import application.Users.LoggedInUserContainer;
import atchat.aegis.com.myapplication.R;

/**
 * Created by Avi on 2018-03-02.
 */

public class FCMNotifications extends FirebaseMessagingService {

    private String website;

    public FCMNotifications(){
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        website = getString(R.string.localhost);

        Log.i("FCMNotifications" , "Message has been recieved");
        if(remoteMessage.getData().size() > 0){
            Log.i("FCMNotifications" , "data: " + remoteMessage.getData());
        }
        parseMessages();
    }

    public void parseMessages(){
        try {
            List<Message> messages = (new NewMessageReciever().execute()).get();
            if(messages != null){
                Iterator<Message> iterator = messages.iterator();
                while(iterator.hasNext()){
                    MessageInterface message = iterator.next();
                    if(message instanceof TextMessage){
                        TextMessage textMessage =  (TextMessage) message;
                        Log.i("TextMessage" , textMessage.getContext());
                    }else if(message instanceof FoundPartnerMessage){
                        Log.i("FoundPartnerMessage" , "New Partner Found");
                        FoundPartnerMessage foundPartnerMessage = (FoundPartnerMessage) message;
//                        BottomNavigationMenue bottomNavigationMenue =(BottomNavigationMenue) getApplicationContext();
//                        bottomNavigationMenue.startTextMessageFragment(foundPartnerMessage);
                        Intent intent = new Intent("FoundPartnerMessage");
                        intent.putExtra("FoundPartnerMessage", foundPartnerMessage);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    }
                }
            }else{

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class NewMessageReciever extends AsyncTask<Void, Void, ArrayList<Message>> {

        @Override
        protected ArrayList<Message> doInBackground(Void... voids) {
            final String url = website+"userActions/getNewMessages";
            GetNewMessagesMessage getNewMessagesMessage = new GetNewMessagesMessage();
            getNewMessagesMessage.setSender(LoggedInUserContainer.getInstance().getUser().getId());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ArrayList<Message> newMessages = null;
            try {
                GetNewMessagesMessage result = restTemplate.postForObject(url, getNewMessagesMessage, GetNewMessagesMessage.class);
                newMessages = result.getMessages();
            }catch(NullPointerException e){
                Log.i("FCMNotifications", "No Messages");
            }
            return newMessages;
        }
    }
}
