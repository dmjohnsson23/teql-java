package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

public class RepeatSubParser extends SubParserNonBranching{
    public final SubParser internal;
    public final Integer min;
    public final Integer max;
    public final boolean greedy;

    public RepeatSubParser(SubParser internal, Integer min, Integer max, boolean greedy){
        this.internal = internal;
        this.min = min;
        this.max = max;
        this.greedy = greedy;
    }
    public RepeatSubParser(SubParser internal, Integer min, Integer max){
        this.internal = internal;
        this.min = min;
        this.max = max;
        this.greedy = true;
    }
    public RepeatSubParser(SubParser internal, int count){
        this.internal = internal;
        this.min = count;
        this.max = count;
        this.greedy = true;
    }
    public RepeatSubParser(SubParser internal, boolean greedy){
        this.internal = internal;
        this.min = null;
        this.max = null;
        this.greedy = greedy;
    }
    public RepeatSubParser(SubParser internal){
        this.internal = internal;
        this.min = null;
        this.max = null;
        this.greedy = true;
    }

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        Integer newMin = min;
        Integer newMax = max;
        if (newMin != null && newMin != 0) newMin--;
        if (newMax != null) newMax--;
        SubParser newInternal;
        if (newMax == null || newMax > 0){
            // No max, or max not yet reached
            // Internal feed should be followed by another repeater with one less min/max
            newInternal = internal.then(new RepeatSubParser(internal, newMin, newMax, greedy).then(next));
        }
        else{
            // Max reached, so just a straight call of the internal subparser, leading into the next subparser
            newInternal = internal.then(next);
        }
        if (newMin == null || newMin == 0){
            // No minimum, or minimum already reached
            if (greedy){
                return new SubParser[]{newInternal, next};
            }
            else{
                return new SubParser[]{next, newInternal};
            }
        }
        else{
            // Minimum not yet reached
            return new SubParser[]{newInternal};
        }
    }

    @Override
    public boolean isEpsilon() {
        return true;
    }

}
