package me.dmjohnson.teql.parsing;

import me.dmjohnson.teql.Cursor;

public class LineEndAnchorSubParser extends SubParserAnchor{
    @Override
    boolean accepts(Cursor cursor, Character next_char) {
        // TODO multi-character line endings like \r\n?
        return cursor.eof || (next_char != null && next_char.equals('\n'));
    }
}
