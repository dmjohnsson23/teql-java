package me.dmjohnson.teql.parsing;

import java.util.Collections;
import java.util.Map;

import me.dmjohnson.teql.Cursor;

/**
 * An object marking the position in a string where an event occurred, along with any relevant metadata associated with the event.
 */
class Mark{
    public final Cursor cursor;
    public final String name;
    public final Map<String,Object> attributes;

    public Mark(Cursor cursor, String name){
        this.cursor = cursor;
        this.name = name;
        this.attributes = null;
    }
    public Mark(Cursor cursor, String name, Map<String,Object> attributes){
        this.cursor = cursor;
        this.name = name;
        this.attributes = Collections.unmodifiableMap(attributes);
    }
}
