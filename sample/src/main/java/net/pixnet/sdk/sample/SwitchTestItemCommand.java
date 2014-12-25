package net.pixnet.sdk.sample;

import net.pixnet.sdk.sample.dummy.TestList;

public class SwitchTestItemCommand {

    public static ItemDetailFragment getTestItemFragment(TestList.ItemId id){
        switch (id){
            case LOGIN:
                return new Login();
            case ACCOUNT:
                return new Account();
            case BLOG:
                return new Blog();
            case ALBUM:
                return new Album();
            case BLOCK:
                return new Block();
            case GUESTBOOK:
                return new GuestBook();
            case FRIEND:
                return new Friend();
            default:
                return null;
        }
    }

}
