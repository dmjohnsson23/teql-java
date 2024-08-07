package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

abstract public class SubParserAnchor extends SubParserNonBranching{
    abstract boolean accepts(Cursor cursor, Character next_char);
    
    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        if (accepts(cursor, next_char)){
            return new SubParser[]{next};
        }
        else{
            return new SubParser[0];
        }
    }
    
    @Override
    public boolean isEpsilon() {
        return true;
    }
}
