package net.pixnet.sdk.response;

import java.util.ArrayList;

public class SubscriptionList extends BasicResponse{
    public ArrayList<Subscription> subscriptions;
    public int per_page;
    public int page;
    public int total;
}