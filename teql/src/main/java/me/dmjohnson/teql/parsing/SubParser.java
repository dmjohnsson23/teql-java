package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

/**
 * 
 */
public abstract class SubParser implements Cloneable{
    /**
     * Feed in the next character in the string to be parsed, returning an array of valid states 
     * for the next character.
     * 
     * @param cursor The current cursor position - the cursor *before* the current character.
     * @param next_char The character after the current cursor. May be null if we are at EOF.
     * @param markList A list of marks in the string, which this method may append to
     * @return All possible next parse states from this one, given the input character. This will 
     * be an empty array if this state does not match, and may contain an intermediate version of 
     * itself if more input is needed to determine a match. It may also, of course, contain future 
     * possible states in the event of a match. States should be in order of preference.
     */
    public abstract SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList);


    /**
     * 
     * @return If true, this parser is an epsilon transition; e.g. it does not consume symbols. 
     * This is used for branching. If false, this is a normal parser that consumes symbols.
     */
    public boolean isEpsilon(){
        return false;
    }

    /**
     * 
     * @return If true, this parser represents the final accepting state.
     */
    public boolean isFinal(){
        return false;
    }

    abstract public SubParser then(SubParser next);
}
