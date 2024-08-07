package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

/**
 * A sub-parser that will accept and consume anything
 */
public class EverythingSubParser extends SubParserNonBranching {
    public final boolean greedy;

    EverythingSubParser(boolean greedy){
        this.greedy = greedy;
    }
    
    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        if (greedy){
            return new SubParser[]{this, this.next};
        }
        else{
            return new SubParser[]{this.next, this};
        }
    }
}
