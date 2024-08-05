package me.dmjohnson.teql.selector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dmjohnson.teql.Context;

public class RegexSelector implements Selector{
    public final Pattern pattern;

    public RegexSelector(Pattern pattern){
        this.pattern = pattern;
    }

    public RegexSelector(String pattern){
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public Selection find_one(Context context) {
        Matcher matcher = this.pattern.matcher(context.data);
        matcher.region((int)context.start, 10000); // TODO this cast is probably going to cause grief...
        if (matcher.find()){
            return new Selection(matcher.start(), matcher.end());
        }
        else return null;
    }
    
}
