package com.markeloff.practise.Retrofit.data;

import java.util.List;

/**
 * Created by Hai Xiao on 2016/3/7.
 */
public class Curator {
    public String title;
    public int total;
    public List<Dataset> dataset;

    public class Dataset {
        public String curator_title;
        public String curator_tagline;
    }
}
