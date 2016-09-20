package com.haixiao.jnidemo;

/**
 * Copyright (c) 2016 Wen-xintech Inc. All rights reserved.
 * <p>
 * Created by Hai Xiao on 8/14/16.
 * hai.xiao@wen-xintech.com
 */


public class NdkJniUtils {
    static {
        try {
            System.loadLibrary("wxcore");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public  native String getCLanguageString();

    public native float[] IirFilter(float[] pcgBuffer, int start, int size);

    public  native float calcHeartRate(float[] ecgBuffer);
}
