package com.example.albert.spyapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.albert.spyapp.utils.Urls;
import com.example.albert.spyapp.requests.ServerRequest;

import java.util.concurrent.locks.ReentrantLock;

public class TestOnlineService extends IntentService {
    boolean connected =false;
    ReentrantLock lock=new ReentrantLock();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public TestOnlineService(){
        super(TestOnlineService.class.getSimpleName());
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        testConnection();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }



    private void sendBroadcastMessage(boolean connected) {
        try {
            lock.lock();
            Intent intent = new Intent();
            intent.setAction("com.example.albert.spyapp;");

            intent.putExtra("connected", connected);

            sendBroadcast(intent);
        }catch (Exception ex)
        {
//            Log.d("exception while sending","exec");
        }

        finally {
            lock.unlock();
        }


    }

    public void testConnection() {
        final String url = Urls.TEST.url;

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
//                    Log.d("While", "started");
                    try {
                        lock.lock();
                        ServerRequest serverRequest = new ServerRequest(Urls.TEST.url,getApplicationContext(),false);
                        if (!serverRequest.getReturnedValue().equals("error")) {
                            serverRequest.makeTestRequest();
//                            Log.d("serverRequest", serverRequest.getReturnedValue());
                            if (serverRequest.getReturnedValue().equals("{\"success\":\"true\"}")) {
                                connected = true;
//                                Log.d("connected?", "true");
//                                Log.d("Sending Broadcast", "true");
                            } else {
                                connected = false;
//                                Log.d("connected?", "false");
                            }
                        }
                        Thread.sleep(1000);
                    }
                    catch(SecurityException s)
                        {
                            connected = false;
                            s.getCause();
//                            Log.d("Securityexception", s.getMessage());
                        }
                    catch(Exception ex)
                        {
                            connected = false;

//                            Log.d("exception", ex.getLocalizedMessage());
                        }
                        finally {
                        lock.unlock();
                        sendBroadcastMessage(connected);
                    }
                }

            }
        }).start();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                sendBroadcastMessage(connected);
            }
        });

    }
}
