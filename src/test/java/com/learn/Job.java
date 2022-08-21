package com.learn;

import cn.cast.list.ArrayList;
import cn.cast.list.List;

public class Job {
    public List<String> delLengthGT5(ArrayList<String> names){
        if (names == null){
            return new ArrayList<>();
        }
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            if (name == null){
                continue;
            }
            if (name.length() > 5){
                names.remove(i);
            }
        }
        return names;
    }

}