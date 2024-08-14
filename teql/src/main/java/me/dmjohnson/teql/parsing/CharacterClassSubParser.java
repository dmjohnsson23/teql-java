package me.dmjohnson.teql.parsing;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import me.dmjohnson.teql.Cursor;

public class CharacterClassSubParser extends SubParserNonBranching {
    private Set<Character> characters;
    private boolean inverted = false;

    protected CharacterClassSubParser(){
        this.characters = new TreeSet<Character>();
    }
    public CharacterClassSubParser(Set<Character> characters){
        this.characters = characters;
    }
    public CharacterClassSubParser(Collection<Character> characters){
        this();
        add(characters);
    }
    public CharacterClassSubParser(Character[] characters){
        this();
        add(characters);
    }
    public CharacterClassSubParser(String characters){
        this();
        add(characters);
    }
    public CharacterClassSubParser(char rangeStart, char rangeEnd){
        this();
        add(rangeStart, rangeEnd);
    }
    public static CharacterClassSubParser parse(String characterClass){
        CharacterClassSubParser cc = new CharacterClassSubParser();
        Character lastChar = null;
        char current;
        int i = 0;
        boolean inRange = false;
        while(i < characterClass.length()){
            current = characterClass.charAt(i);
            if (current == '\\'){
                // Escape character
                i++;
                current = characterClass.charAt(i);
                if (current == 'n') current = '\n';
                else if (current == 'r') current = '\r';
                else if (current == 't') current = '\t';
                // else if (current == 'v') current = '\v';
                else if (current == 'f') current = '\f';
                else if (current == '0') current = '\0';
                else if (current == '\\') ;
                else if (current == 'x') {
                    // Next two characters make a hex code
                    cc.add(Character.toChars(Integer.parseInt(characterClass.substring(i, i+2), 16)));
                    i += 2;
                    continue;
                }
                else if (current == 'u') {
                    // Next four characters for a unicode codepoint
                    cc.add(Character.toChars(Integer.parseInt(characterClass.substring(i, i+4), 16)));
                    i += 4;
                    continue;
                }
                else if (current == 's'){
                    cc.add(new char[]{' ', '\t', '\r', '\n', '\f', /*'\v'*/});
                    cc.add('\u2000', '\u200a');
                    cc.add(new char[]{'\u00a0', '\u1680', '\u2028', '\u2029', '\u202f', '\u205f', '\u3000', '\ufeff'});
                    continue;
                }
                else if (current == 'w'){
                    cc.add('0', '9');
                    cc.add('a', 'z');
                    cc.add('A', 'Z');
                    cc.add('_');
                    continue;
                }
                else if (current == 'd'){
                    cc.add('0', '9');
                    continue;
                }
            }
            if (current == '-' && lastChar != null){
                // Range
                inRange = true;
            }
            else if (inRange){
                cc.add(lastChar, current);
                inRange = false;
            }
            else{
                cc.add(current);
            }
            lastChar = current;
            i++;
        }
        return cc;
    }
    public static CharacterClassSubParser digit(){
        return new CharacterClassSubParser('0', '9');
    }
    public static CharacterClassSubParser whitespace(){
        CharacterClassSubParser cc = new CharacterClassSubParser();
        cc.add(new char[]{' ', '\t', '\r', '\n', '\f', /*'\v'*/});
        cc.add('\u2000', '\u200a');
        cc.add(new char[]{'\u00a0', '\u1680', '\u2028', '\u2029', '\u202f', '\u205f', '\u3000', '\ufeff'});
        return cc;
    }
    public static CharacterClassSubParser word(){
        CharacterClassSubParser cc = new CharacterClassSubParser();
        cc.add('0', '9');
        cc.add('a', 'z');
        cc.add('A', 'Z');
        cc.add('_');
        return cc;
    }

    
    protected void add(char toAdd){
        characters.add(toAdd);
    }
    protected void add(Collection<Character> toAdd){
        characters.addAll(toAdd);
    }
    protected void add(Character[] toAdd){
        characters.addAll(Arrays.asList(toAdd));
    }
    protected void add(char[] toAdd){
        characters.addAll(Arrays.asList(ArrayUtils.toObject(toAdd)));
    }
    protected void add(String toAdd){
        characters.addAll(Arrays.asList(ArrayUtils.toObject(toAdd.toCharArray())));
    }
    protected void add(char rangeStart, char rangeEnd){
        for(char c = rangeStart; c <= rangeEnd; c++){
            characters.add(c);
        }
    }

    public CharacterClassSubParser union(CharacterClassSubParser other){
        Set<Character> unionCharacters = new TreeSet<Character>(this.characters);
        unionCharacters.addAll(other.characters);
        return new CharacterClassSubParser(unionCharacters);
    }
    public CharacterClassSubParser invert(){
        CharacterClassSubParser invertedCopy;
        try {
            invertedCopy = (CharacterClassSubParser) this.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen
            throw new RuntimeException("Could not build parser; `clone` not supported");
        }
        invertedCopy.inverted = !invertedCopy.inverted;
        return invertedCopy;
    }


    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        if (next_char != null && (characters.contains(next_char) ^ inverted)){
            return new SubParser[]{this.next};
        }
        // No match
        return new SubParser[0];
    }

    @Override
    public String toString() {
        return "CharacterClassSubParser(\""+characters.toArray().toString()+"\")";
    }
}
