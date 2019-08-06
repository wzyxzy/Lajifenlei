package com.wzy.lajifenlei;

public interface MessageReceiver {
    void onSuccess(String message);

    void onFailure();
}
