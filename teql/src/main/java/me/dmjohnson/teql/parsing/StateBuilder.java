package me.dmjohnson.teql.parsing;

public class StateBuilder {
    
    
    /**
     * Return a new state builder with the nodes of this builder followed by the nodes of the next
     * 
     * This is essentially '(this)(other)'.
     * 
     * @param other
     * @return
     */
    public StateBuilder concat(StateBuilder other){

    }

    /**
     * Return a new state builder that will match either of the component builders (this or the other).
     * 
     * This is the '(this)|(other)' regex operator
     * 
     * @param other
     * @return
     */
    public StateBuilder union(StateBuilder other){
        
    }

    /**
     * Make and optional version of the nodes within this builder.
     * 
     * This is the '(this)?' regex operator
     * @return
     */
    public StateBuilder optional(){

    }

    /**
     * Make and optional and infinitely repeating version of the nodes within this builder.
     * 
     * This is the '(this)*' regex operator.
     * 
     * @return
     */
    public StateBuilder optional_repeat(){

    }

    /**
     * Make an infinitely repeating version of the nodes within this builder.
     * 
     * This is the '(this)+' regex operator, though is implemented more like '(this)(this)*'.
     * @return
     */
    public StateBuilder repeat(){

    }

    /**
     * Make a finitely repeating version of the nodes in this builder.
     * 
     * This is the '(this){n}' regex operator, though is implemented as concatenation, e.g.: '(this)(this)(this)'
     * @param n
     * @return
     */
    public StateBuilder repeat(int n){

    }


    /**
     * Make a finitely repeating version of the nodes in this builder.
     * 
     * This is the '(this){n,m}' regex operator, though is implemented as concatenation with optional elements, e.g.: '(this)(this)(this)?'
     * @param n
     * @return
     */
    public StateBuilder repeat(int n, int m){

    }
}
