package net.pixnet.sdk.response;

import java.util.ArrayList;

public class Subscription_groupList extends BaseListResponse {
    public ArrayList<Subscription_group> subscription_groups;
    public int total;

    public Subscription_groupList(String str) {
        super(str);
    }
}