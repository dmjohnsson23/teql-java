package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

public class LiteralSubParser extends SubParserNonBranching {
    private String literal;
    public LiteralSubParser(String literal){
        this.literal = literal;
    }

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        if (next_char.equals(literal.charAt(0))){
            // Fed character matches
            if (literal.length() == 1){
                // Reached a full match
                return new SubParser[]{this.next};
            }
            // A partial, indeterminate match
            LiteralSubParser intermediate = new LiteralSubParser(literal.substring(1));
            return new SubParser[]{intermediate.then(this.next)};
        }
        // No match
        return new SubParser[0];
    }
}
