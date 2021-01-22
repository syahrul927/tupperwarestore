package com.example.tupperwarestore.model.resp;

import java.util.List;

public class VariantResp {
    private List<String> colors;

    public List<String> getColor() {
        return colors;
    }

    public void setColor(List<String> colors) {
        this.colors = colors;
    }

    public VariantResp() {
    }

    public VariantResp(List<String> colors) {
        this.colors = colors;
    }
}
