package com.sprc.utils;

import java.util.List;
import java.util.Map;

public class UtilsHw {
    public static boolean hasNullParameters(Map<String, String> map, List<String> keys) {
        for (String key : keys)
            if (map.get(key) == null)
                return true;
        return false;
    }
}
