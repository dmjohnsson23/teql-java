package me.dmjohnson.teql.parsing;

import me.dmjohnson.teql.Cursor;

public class LineStartAnchorSubParser extends SubParserAnchor{
    @Override
    boolean accepts(Cursor cursor, Character next_char) {
        return cursor.col_no == 1;
    }
}
