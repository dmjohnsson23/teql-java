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
    
    @SuppressWarnings("unchecked")
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
     * @param cursor The current cursor position - the cursor *before* the current character.
     * @param next_char The character after the current cursor. May be null if we are at EOF.
     * @param exhaustive If true, exhaustively search for all possible matches, even those with 
     * lower precedence. (This is *not* needed to allow multiple *non-overlapping* matches, but 
     * *is* needed to allow multiple *overlapping* matches.) There is a performance penalty for
     * using this option.
     * @return A list of all potential next states, in order of precedence.
     */
    public ParserState[] feed(Cursor cursor, Character next_char, boolean exhaustive){
        if (sub.isFinal()){
            // We've already matched; leave the match as is and retain this state
            return new ParserState[]{this};
        }
        SubParser[] next = sub.feed(cursor, next_char, marks);
        if (next.length == 0){
            return new ParserState[0];
        }
        // The sub-parser accepted the state
        LinkedList<ParserState> outputStates = new LinkedList<ParserState>();
        for (int i = 0; i < next.length; i++) {
            ParserState newState = new ParserState(next[i], this.marks);
            if (next[i].isFinal()){
                // We reached the final accepting state; call feed one last time so it can add a mark
                next[i].feed(cursor, next_char, this.marks);
                outputStates.add(new ParserState(next[i], this.marks));
            }
            else if (next[i].isEpsilon()){
                // If the state is an epsilon transition, recursively resolve it before adding
                outputStates.addAll(Arrays.asList(newState.feed(cursor, next_char)));
            }
            else{
                // Otherwise, just return the new state
                outputStates.add(newState);
            }
            if (outputStates.size() < 1) continue;
            if (!exhaustive && i == 0 && outputStates.getFirst().sub.isFinal()){
                // A matching state at the first position indicates that a match at the highest 
                // possible precedence was found. No further evaluation is needed.
                break;
            }
            if (!exhaustive && outputStates.getLast().sub.isFinal()){
                // Optimization - if a final state is reached, all states afterward can immediately be discarded
                // This works because any matches found after this would have lower precedence anyway.
                break;
            }
        }
        return outputStates.toArray(new ParserState[outputStates.size()]);
    }

    public ParserState[] feed(Cursor cursor, Character next_char){
        return this.feed(cursor, next_char, false);
    }
    public Mark[] getMarks(){
        return marks.toArray(new Mark[marks.size()]);
    }

    @Override
    public String toString() {
        return "ParserState("+sub+")";
    }
}
