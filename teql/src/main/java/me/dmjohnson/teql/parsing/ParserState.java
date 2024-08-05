package me.dmjohnson.teql.parsing;

import java.util.Arrays;
import java.util.LinkedList;

import me.dmjohnson.teql.Cursor;

public class ParserState {
    protected LinkedList<Mark> marks;
    public final SubParser sub;

    public ParserState(SubParser sub){
        this.sub = sub;
        this.marks = new LinkedList<Mark>();
    }
    public ParserState(SubParser sub, LinkedList<Mark> marks){
        this.sub = sub;
        this.marks = (LinkedList<Mark>)marks.clone();
    }

    // * iterate though all possible states, feeding them the next character
    // * Add all returned states *in order* to the new state list
    // * If the topmost state is a final state, parsing is done (keep going if there is a final state later in the list)
    /**
     * Feed the next character into the parsers
     * 
     * @param cursor The current cursor position - the cursor *before* the current character
     * @param next_char
     * @return A list of all potential next states, in order of precedence.
     */
    public ParserState[] feed(Cursor cursor, Character next_char){
        SubParser[] next = sub.feed(next_char, cursor, marks);
        if (next.length == 0){
            return new ParserState[0];
        }
        // The sub-parser accepted the state
        LinkedList<ParserState> outputStates = new LinkedList<ParserState>();
        for (int i = 0; i < next.length; i++) {
            ParserState newState = new ParserState(next[i], this.marks);
            if (next[i].isEpsilon()){
                // If the state is an epsilon transition, recursively resolve it before adding
                outputStates.addAll(Arrays.asList(newState.feed(cursor, next_char)));
            }
            else{
                // Otherwise, just return the new state
                outputStates.add(newState);
            }
            if (outputStates.getLast().sub.isFinal()){
                // Optimization - if a final state is reached, all states afterward can immediately be discarded
                // This works because any matches found after this would have lower precedence anyway.
                break;
            }
        }
        return outputStates.toArray(new ParserState[outputStates.size()]);
    }

    public Mark[] getMarks(){
        return marks.toArray(new Mark[marks.size()]);
    }
}
