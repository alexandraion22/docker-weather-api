package com.sprc.utils;

import com.sprc.tema2.temperatures.Temperatures;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UtilsHw {
    public static boolean hasNullParameters(Map<String, String> map, List<String> keys) {
        for (String key : keys)
            if (map.get(key) == null)
                return true;
        return false;
    }

    public static List<Temperatures> filterListByFromAndUntil(List<Temperatures> temperatures, Date from, Date until) {
        if (from != null && until != null)
            return temperatures.stream()
                    .filter(t -> (isBeforeOrEqual(t.getTimestamp(), until) && isAfterOrEqual(t.getTimestamp(), from)))
                    .collect(Collectors.toList());

        if (from != null)
            return temperatures.stream().filter(t -> isAfterOrEqual(t.getTimestamp(), from))
                    .collect(Collectors.toList());

        if (until != null)
            return temperatures.stream().filter(t -> isBeforeOrEqual(t.getTimestamp(), until))
                    .collect(Collectors.toList());

        return temperatures;
    }

    public static Boolean isBeforeOrEqual(Date when, Date until) {
        return when.before(until);
    }

    public static Boolean isAfterOrEqual(Date when, Date from) {
        return when.after(from) || when.equals(from);
    }
}
