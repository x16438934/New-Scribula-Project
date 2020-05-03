package com.getscriba.sampleapp.android.game.game.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

//entry.410215655
//        entry.135689214
//        entry.974849132

public class GoogleFormUploader {
    private ArrayList<Entry> mEntries;
    private String formId;

    /**************************
     * Constructor
     * @param formId - the id for your form.
     * Look at the source of the live form to find it.
     **************************/
    public GoogleFormUploader(String formId){
        this.formId = formId;
        mEntries = new ArrayList<>();
    }
    /*****************************************
     * Add an entry to the list to get uploaded
     * @param id - entry id. Look at the source
     * of the 'live form' to find your entry id's.
     * @param data - data for this column
     *****************************************/
    public void addEntry(String id, String data){
        mEntries.add(new Entry(id, data));
    }

    /***************************************
     * Upload entries to the form. This method
     * is Asynchronous, it will return immediately.
     * It will call onUploadComplete() and pass
     * the response that it got btn_back from google
     * server.
     ***************************************/
    public void upload(){
        //Log.i("Uploader", getFormUrl());
        //Log.i("Uploader", getData());
        Thread t = new Thread(() -> {
            HttpRequest mReq = new HttpRequest();
            String response = null;
            try {
                response = mReq.sendPost(getFormUrl(), getData());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            onUploadComplete(response);
        });

        t.start();
    }

    private String getFormUrl(){
        return "https://docs.google.com/forms/d/e/" + formId +"/formResponse";
        }


    private String getData() throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        for(Entry cur: mEntries){
            data.append("entry.");
            data.append(cur.getEntryId());
            data.append("=");
            try {
                data.append(URLEncoder.encode(cur.getData(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                data.append(URLEncoder.encode(cur.getData(), java.nio.charset.StandardCharsets.UTF_8.toString()));
            }
            data.append("&");
        }
        data.deleteCharAt(data.length() - 1);
        return data.toString();
    }

    public String getFormId() {
        return formId;
    }
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /********************************************
     * Default behavior is do nothing. Override this
     * if you want to get a callback when the upload
     * is complete. Check the response to termine
     * success of failure.
     * @param response - the response that was sent btn_back
     * by the google docs server.
     *********************************************/
    private void onUploadComplete(String response) {

    }
}
