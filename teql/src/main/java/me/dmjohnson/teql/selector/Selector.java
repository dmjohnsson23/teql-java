package me.dmjohnson.teql.selector;

import me.dmjohnson.teql.Context;

public interface Selector {
    public Selection find_one(Context context);
    // public Selection[] find_all(Context context);
}
