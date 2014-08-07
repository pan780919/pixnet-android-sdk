package net.pixnet.sdk.test;

import net.pixnet.sdk.test.dummy.TestList;

public class SwitchTestItemCommand {

    public static ItemDetailFragment getTestItemFragment(TestList.ItemId id){
        switch (id){
            case LOGIN:
                return new Login();
            case BLOG:
                return null;
            case ALBUM:
                return new Album();
            default:
                return null;
        }
    }

}
