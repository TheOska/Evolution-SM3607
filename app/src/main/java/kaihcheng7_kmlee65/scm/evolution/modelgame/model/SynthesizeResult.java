package kaihcheng7_kmlee65.scm.evolution.modelgame.model;

import java.util.ArrayList;

public class SynthesizeResult {
    public boolean success;
    public ArrayList<String> crafts;

    public SynthesizeResult() {
        success = false;
        crafts = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getCrafts() {
        return crafts;
    }

    public void setCrafts(ArrayList<String> crafts) {
        this.crafts = crafts;
    }
}

