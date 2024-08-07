package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

public class StartSubParser extends SubParser {
    public SubParser[] branches;
    public final String id;
    public StartSubParser(SubParser[] branches, String id){
        this.branches = branches;
        this.id = id;
    }

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        markList.add(new Mark(cursor, "start", id));
        return branches;
    }
    
    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public String toString() {
        return "StartSubParser(id: "+id+")";
    }
}