package net.pixnet.sdk.test;

import net.pixnet.sdk.test.dummy.TestList;

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
            default:
                return null;
        }
    }

}
