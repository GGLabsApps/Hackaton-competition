package com.gglabs.materna.Helper;

import com.cloudant.client.api.views.AllDocsResponse;

/**
 * Created by GG on 19/02/2018.
 */

public interface CloudantOnResponseListener {

    void onObjectArrival(AllDocsResponse response);

//    void onListArrival(T<Task> list);
}