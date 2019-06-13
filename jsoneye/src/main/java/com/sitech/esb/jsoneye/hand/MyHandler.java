package com.sitech.esb.jsoneye.hand;

import com.sitech.esb.jsoneye.Handler;
import com.sitech.esb.jsoneye.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @{USER}
 * @{DATE}
 */
public class MyHandler implements Handler {

    public Result different(final Collection<String> c1, final Collection<String> c2) {
        Iterator<String> iterator = c2.iterator();
        while (iterator.hasNext()) {
            String current = iterator.next();
            //如果benchmarkSet也包含这个数，那么两个set 都删除这个数
            if (c1.contains(current)) {
                iterator.remove();
                c1.remove(current);
            }
        }
        return new Result() {
            public List<String> lack() {
                return new ArrayList<String>(c1);
            }

            public List<String> remain() {
                return new ArrayList<String>(c2);
            }

        };
    }
}
