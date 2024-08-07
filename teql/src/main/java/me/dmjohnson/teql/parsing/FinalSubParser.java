package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

public class FinalSubParser extends SubParser {

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        markList.add(new Mark(cursor, "final"));
        return new SubParser[0];
    }
    
    @Override
    public boolean isFinal() {
        return true;
    }
}
