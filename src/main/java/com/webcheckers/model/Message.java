package com.webcheckers.model;

/**
 * A Message for either informational or error message purposes
 */
public class Message {

    //
    // Attributes
    //

    public enum Type {INFO, ERROR}

    private Type type;
    private String text;

    /**
     * Create a Message, either Informational or Error type, with provided text
     * @param type
     *      Type {@link Type} of the message, either INFO or ERROR
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
     *      Type {@link Type} of the message, INFO or ERROR
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
