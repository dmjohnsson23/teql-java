package me.dmjohnson.teql.parsing;

import me.dmjohnson.teql.Cursor;

public class StartAnchorSubParser extends SubParserAnchor{
    @Override
    boolean accepts(Cursor cursor, Character next_char) {
        return cursor.char_pos == 0;
    }
}
