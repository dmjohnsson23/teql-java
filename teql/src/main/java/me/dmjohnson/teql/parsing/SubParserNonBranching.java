package me.dmjohnson.teql.parsing;

public abstract class SubParserNonBranching extends SubParser{
    protected SubParser next;

    public SubParser then(SubParser next){
        try {
            SubParserNonBranching other = (SubParserNonBranching)this.clone();
            other.next = next;
            return other;
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
            throw new RuntimeException("Could not build parser; `clone` not supported");
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
