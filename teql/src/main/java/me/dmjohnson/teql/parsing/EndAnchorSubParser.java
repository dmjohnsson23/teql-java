package me.dmjohnson.teql.parsing;

import me.dmjohnson.teql.Cursor;

public class EndAnchorSubParser extends SubParserAnchor{
    @Override
    boolean accepts(Cursor cursor, Character next_char) {
        return cursor.eof;
    }
}
