package net.pixnet.sdk.test;

import net.pixnet.sdk.test.dummy.TestList;

public class SwitchTestItemCommand {

    public static ItemDetailFragment getTestItemFragment(TestList.ItemId id){
        ItemDetailFragment f;
        switch (id){
            case LOGIN:
                return null;
            case BLOG:
                return null;
            case ALBUM:
                return new Album();
            default:
                return null;
        }
    }

}
