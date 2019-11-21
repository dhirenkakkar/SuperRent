package ca.ubc.cs304.model;

public class VehicleTypes {
    private String vtname;
    private String features;
    private float wrate; // -1 is null
    private float drate; // -1 is null
    private float hrate; // -1 is null
    private float wirate; // -1 is null
    private float dirate; // -1 is null
    private float hirate; // -1 is null
    private float krate; // -1 is null

    public String getVtname() {
        return vtname;
    }

    public void setVtname(String vtname) {
        this.vtname = vtname;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public float getWrate() {
        return wrate;
    }

    public void setWrate(float wrate) {
        this.wrate = wrate;
    }

    public float getDrate() {
        return drate;
    }

    public void setDrate(float drate) {
        this.drate = drate;
    }

    public float getHrate() {
        return hrate;
    }

    public void setHrate(float hrate) {
        this.hrate = hrate;
    }

    public float getWirate() {
        return wirate;
    }

    public void setWirate(float wirate) {
        this.wirate = wirate;
    }

    public float getDirate() {
        return dirate;
    }

    public void setDirate(float dirate) {
        this.dirate = dirate;
    }

    public float getHirate() {
        return hirate;
    }

    public void setHirate(float hirate) {
        this.hirate = hirate;
    }

    public float getKrate() {
        return krate;
    }

    public void setKrate(float krate) {
        this.krate = krate;
    }
}
