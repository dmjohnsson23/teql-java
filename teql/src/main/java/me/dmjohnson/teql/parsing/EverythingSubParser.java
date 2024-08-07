package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

/**
 * A sub-parser that will accept and consume anything
 */
public class EverythingSubParser extends SubParserNonBranching {
    public final boolean greedy;

    public EverythingSubParser(boolean greedy){
        this.greedy = greedy;
    }
    
    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        // FIXME this performs really poorly if it is the first subparser in the graph and we are not anchored to the string start
        if (next_char == null){
            // We've reached the end of the file and can't match anymore
            return new SubParser[]{this.next};
        }
        else if (greedy){
            return new SubParser[]{this, this.next};
        }
        else{
            return new SubParser[]{this.next, this};
        }
    }
}
