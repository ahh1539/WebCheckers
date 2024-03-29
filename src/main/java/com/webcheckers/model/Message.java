package com.webcheckers.model;

import java.io.Serializable;

/**
 * A Message for either informational or error message purposes
 */
public class Message implements Serializable {

    //
    // Attributes
    //

    public enum Type {info, error}

    private Type type;
    private String text;

    /**
     * Create a Message, either Informational or Error type, with provided text
     * @param type
     *      Type {@link Type} of the message, either info or error
     * @param text
     *      The String text content of the Message
     */
    public Message(Type type, String text){
        this.type = type;
        this.text = text;
    }

    /**
     * Gets the type of the Message
     * @return
     *      Type {@link Type} of the message, info or error
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Gets the text content of the Message
     * @return
     *      The String text content of the Message.
     */
    public String getText(){
        return this.text;
    }

}
