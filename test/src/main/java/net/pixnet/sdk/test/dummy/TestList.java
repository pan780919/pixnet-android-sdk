package net.pixnet.sdk.test.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TestList {

    public static enum ItemId{
        LOGIN,
        ACCOUNT,
        BLOG,
        ALBUM,
        BLOCK
    }

    /**
     * An array of sample (dummy) items.
     */
    public static List<TestItem> ITEMS = new ArrayList<TestItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<ItemId, TestItem> ITEM_MAP = new HashMap<ItemId, TestItem>();

    static {
        addItem(new TestItem(ItemId.LOGIN, "LOGIN"));
        addItem(new TestItem(ItemId.ACCOUNT, "ACCOUNT"));
        addItem(new TestItem(ItemId.BLOG, "BLOG"));
        addItem(new TestItem(ItemId.ALBUM, "ALBUM"));
        addItem(new TestItem(ItemId.BLOCK,"BLOCK"));
    }

    private static void addItem(TestItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TestItem {
        public ItemId id;
        public String content;

        public TestItem(ItemId id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
