package kaihcheng7_kmlee65.scm.evolution.modelgame.utils;

import android.content.Context;
import android.view.ViewGroup;

import kaihcheng7_kmlee65.scm.evolution.modelgame.model.CraftItem;


/**
 * Created by xiaotingtang on 14/11/2016.
 */

public class CraftUtil {
    private static int craftItemSize = 100;
    public static CraftItem newCraft(Context context, String name) {
        CraftItem newCraftItemView = new CraftItem(context);
        newCraftItemView.setLayoutParams(new ViewGroup.LayoutParams(craftItemSize, craftItemSize));
//        newCraftItemView.setBackground(bottomBarCrafts.get(position).getCraftDrawable());
        newCraftItemView.setCraftDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(name, "drawable", "kaihcheng7_kmlee65.scm.evolution")));
        newCraftItemView.setCraftName(name);
        newCraftItemView.setCraftTag(name);
        newCraftItemView.setCraftProperty(name);
        return newCraftItemView;
    }
}
