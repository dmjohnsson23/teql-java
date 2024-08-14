package me.dmjohnson.teql.parsing;

import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.lang3.tuple.Pair;

import me.dmjohnson.teql.Context;
import me.dmjohnson.teql.Cursor;

public class Parser {
    private StartSubParser[] startStates;
    
    public Parser(StartSubParser[] startStates){
        this.startStates = startStates;
    }

    /**
     * An unanchored search through the context for all matching sections.
     * @param context
     * @return An array of Mark arrays; one inner array for each match. You can convert the Marks 
     * to an AST, a set of capture groups, or whatever you need.
     */
    public Mark[][] find_all(Context context){
        LinkedList<Mark[]> output = new LinkedList<Mark[]>();
        LinkedList<ParserState> currentStates = new LinkedList<ParserState>();
        LinkedList<ParserState> nextStates = new LinkedList<ParserState>();
        for (Pair<Cursor, Character> pair : context) {
            // If the first item is a match, add it to the output and reset the state to find the next non-overlapping match
            if (currentStates.size() >= 1 && currentStates.getFirst().sub.isFinal()){
                System.out.println("Found the end!");
                output.add(currentStates.getFirst().feed(pair.getLeft(), pair.getRight())[0].getMarks());
                currentStates.clear();
                currentStates.clear();
            }
            // Append the start states each loop to allow unanchored matches
            for (SubParser subParser : startStates) {
                ParserState state = new ParserState(subParser);
                if (subParser.isEpsilon()){
                    currentStates.addAll(Arrays.asList(state.feed(pair.getLeft(), pair.getRight())));
                }
                else{
                    currentStates.add(state);
                }
            }
            // Loop through all current states and feed the next character
            for (ParserState state : currentStates) {
                nextStates.addAll(Arrays.asList(
                    state.feed(pair.getLeft(), pair.getRight())
                ));
            }
            // Otherwise, rotate the state lists
            currentStates = nextStates;
            nextStates = new LinkedList<ParserState>();
        }
        return output.toArray(new Mark[output.size()][]);
    }
}
