package me.dmjohnson.teql.parsing;

import java.util.List;

import me.dmjohnson.teql.Cursor;

public class BranchSubParser extends SubParser {
    public SubParser[] branches;
    public BranchSubParser(SubParser[] branches){
        this.branches = branches;
    }

    @Override
    public SubParser[] feed(Cursor cursor, Character next_char, List<Mark> markList) {
        return branches;
    }
    
    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public String toString() {
        return "BranchSubParser()";
    }

    public BranchSubParser then(SubParser next){
        SubParser[] newBranches = new SubParser[branches.length];
        for (int i = 0; i < branches.length; i++) {
            newBranches[i] = branches[i].then(next);
        }
        return new BranchSubParser(newBranches);
    }
}