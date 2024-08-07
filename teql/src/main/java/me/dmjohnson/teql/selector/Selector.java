package me.dmjohnson.teql.selector;

import me.dmjohnson.teql.parsing.SubParser;

public interface Selector {
    public SubParser buildParser();
}
