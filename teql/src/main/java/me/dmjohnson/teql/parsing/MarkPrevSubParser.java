package me.dmjohnson.teql.parsing;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dmjohnson.teql.Cursor;

/**
 * An object marking the position in a string where an event occurred, along with any relevant metadata associated with the event.
 */
public class MarkPrevSubParser extends MarkSubParser{
    public MarkPrevSubParser(String name){
        super(name);
    }
    public MarkPrevSubParser(String name, String id){
        super(name, id);
    }
    public MarkPrevSubParser(String name, Map<String,Object> attributes){
        super(name, attributes);
    }

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        markList.add(new Mark(cursor, name, attributes));
        return new SubParser[]{next};
    }

    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public String toString() {
        return "MarkPrevSubParser(name: "+name+")";
    }
}
