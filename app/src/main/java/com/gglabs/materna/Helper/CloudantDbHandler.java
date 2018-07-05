package com.gglabs.materna.Helper;

import android.os.AsyncTask;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.AllDocsResponse;

/**
 * Created by GG on 16/02/2018.
 */

public class CloudantDbHandler {

    private String TAG = "CloudantDbHandler";

    private static final CloudantDbHandler instance = new CloudantDbHandler();
    private CloudantOnResponseListener responseListener;
    private CloudantOnCompleteListener completeListener;

    private CloudantClient client;
    private Database db;

    private CloudantDbHandler() {
        client = ClientBuilder.account("d49048cb-8169-4410-bc13-8a59b647e1df-bluemix")
                .username("d49048cb-8169-4410-bc13-8a59b647e1df-bluemix")
                .password("6729a4cb6be1ab8a3a3b7ced7fff190f5258cb6afd16a850d5cbdba624a0a288")
                .build();
    }

    public static CloudantDbHandler getInstance() {
        return instance;
    }

    public void setOnResponseListener(CloudantOnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void setOnCompleteListener(CloudantOnCompleteListener completeListener) {
        this.completeListener = completeListener;
    }

/*    public void load(final String documentId, String dbName) {
        db = client.database(dbName, false);

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return db.find(Object.class, documentId);
                } catch (NoDocumentException nde) {
                    nde.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                responseListener.onObjectArrival(result);
            }
        }.execute();
    }*/

    public void loadAll(String dbName) {
        db = client.database(dbName, false);

        new AsyncTask<Void, Void, AllDocsResponse>() {
            @Override
            protected AllDocsResponse doInBackground(Void... voids) {
                try {
                    return db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse();
                } catch (Exception nde) {
                    nde.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(AllDocsResponse result) {
                super.onPostExecute(result);
                responseListener.onObjectArrival(result);
            }
        }.execute();
    }

    public void save(final Object obj, final String dbName) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                db = client.database(dbName, true);
                return db.save(obj).getId();
            }

            @Override
            protected void onPostExecute(String idCreated) {
                super.onPostExecute(idCreated);
                completeListener.onComplete(!idCreated.isEmpty());
            }
        }.execute();
    }
}
