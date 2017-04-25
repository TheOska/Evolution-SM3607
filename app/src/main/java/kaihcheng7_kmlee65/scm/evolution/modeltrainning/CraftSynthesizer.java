package kaihcheng7_kmlee65.scm.evolution.modeltrainning;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.database.FormulaDBHelper;
import kaihcheng7_kmlee65.scm.evolution.modeltrainning.model.CraftItem;


/**
 * Created by xiaotingtang on 25/10/2016.
 */

public class CraftSynthesizer {
    private FormulaDBHelper dbHelper;
    public Context c;

    public CraftSynthesizer(Context c) {
        this.dbHelper = new FormulaDBHelper(c);
        this.c = c;
    }

    public ArrayList<CraftItem> synthesize(String nameA, String nameB, ViewGroup.LayoutParams lparams) {
        String nameC = this.dbHelper.getFormula(nameA, nameB);
        ArrayList<CraftItem> result = new ArrayList<CraftItem>();
        if (nameC != null) {
            for (String name:  nameC.split("\\+")) {
                name = name.toLowerCase();
                try {
                    CraftItem item = new CraftItem(c);
                    item.setLayoutParams(lparams);
                    item.setBackground(c.getResources().getDrawable(c.getResources().getIdentifier(name, "drawable", c.getPackageName())));
                    item.setCraftDrawable(c.getResources().getDrawable(c.getResources().getIdentifier(name, "drawable", c.getPackageName())));
                    item.setCraftName(name);
                    item.setCraftTag(name);
                    item.setCraftProperty(name);
                    result.add(item);
                } catch (Resources.NotFoundException e) {
                    Log.e("resources", "Craft drawable not found");
                }
            }
        } else {
            result = null;
        }
        return result;
    }
}
