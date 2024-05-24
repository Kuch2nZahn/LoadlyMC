package io.github.kuchenzahn.loadlybukkit.message;

public class Message {
    private String content;
    private int priority;
    private MessageHandler.MessageDisplay display;
    private MessageHandler.MessageType type;
    private MessageHandler.MessageReceiver receiver;

    public Message(String content, MessageHandler.MessageType type, MessageHandler.MessageReceiver receiver){
        this.content = content;
        this.priority = type.getPriority();
        this.display = MessageHandler.MessageDisplay.CHAT;
        this.type = type;
        this.receiver = receiver;
    }

    public Message(String content, MessageHandler.MessageDisplay display, MessageHandler.MessageType type, MessageHandler.MessageReceiver receiver) {
        this.content = content;
        this.priority = type.getPriority();
        this.display = display;
        this.type = type;
        this.receiver = receiver;
    }

    public Message(String content, int priority, MessageHandler.MessageDisplay messageDisplay, MessageHandler.MessageType type, MessageHandler.MessageReceiver messageReceiver) {
        this.content = content;
        this.priority = priority;
        this.display = messageDisplay;
        this.type = type;
        this.receiver = messageReceiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageHandler.MessageDisplay getDisplay() {
        return display;
    }

    public MessageHandler.MessageType getType() {
        return type;
    }

    public MessageHandler.MessageReceiver getReceiver() {
        return receiver;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public int getPriority() {
        return priority;
    }
}
