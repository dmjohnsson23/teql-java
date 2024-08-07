package me.dmjohnson.teql.selector;

import me.dmjohnson.teql.Cursor;

public class Selection {
    public final Cursor start;
    public final Cursor end;
    public Selection(Cursor start, Cursor end){
        this.start = start;
        this.end = end;
    }
}
