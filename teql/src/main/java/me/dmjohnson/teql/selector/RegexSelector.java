package me.dmjohnson.teql.selector;

import me.dmjohnson.teql.parsing.SubParser;

public class RegexSelector implements Selector{
    public final String pattern;

    public RegexSelector(String pattern){
        this.pattern = pattern;
    }

    @Override
    public SubParser buildParser() {
        return null; // TODO
    }
    
}
