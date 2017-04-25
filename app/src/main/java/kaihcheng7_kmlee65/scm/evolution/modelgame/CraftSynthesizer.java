package kaihcheng7_kmlee65.scm.evolution.modelgame;

import android.content.Context;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.database.FormulaDBHelper;


public class CraftSynthesizer {
    private FormulaDBHelper dbHelper;
    public Context c;

    public CraftSynthesizer(Context c) {
        this.dbHelper = new FormulaDBHelper(c);
        this.c = c;
    }

    public ArrayList<String> synthesize(String nameA, String nameB) {
        String nameC = this.dbHelper.getFormula(nameA, nameB);
        ArrayList<String> result = new ArrayList<String>();
        if (nameC != null) {
            for (String name:  nameC.split("\\+")) {
                name = name.toLowerCase();
                result.add(name);
            }
        } else {
            result = null;
        }
        return result;
    }
}
