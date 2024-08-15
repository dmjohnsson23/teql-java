package me.dmjohnson.teql.parsing;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dmjohnson.teql.Cursor;

/**
 * An object marking the position in a string where an event occurred, along with any relevant metadata associated with the event.
 */
public class MarkSubParser extends SubParserNonBranching{
    public final String name;
    public final Map<String,Object> attributes;

    public MarkSubParser(String name){
        this.name = name;
        this.attributes = null;
    }
    public MarkSubParser(String name, String id){
        this.name = name;
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("id", id);
        this.attributes = Collections.unmodifiableMap(attributes);
    }
    public MarkSubParser(String name, Map<String,Object> attributes){
        this.name = name;
        this.attributes = Collections.unmodifiableMap(attributes);
    }

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        markList.add(new Mark(cursor, name, attributes));
        return next.feed(cursor, next_char, markList);
    }

    @Override
    public String toString() {
        return "MarkSubParser(name: "+name+")";
    }
}
