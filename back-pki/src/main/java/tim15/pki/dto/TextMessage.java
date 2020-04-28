package tim15.pki.dto;

import java.io.Serializable;

public class TextMessage implements Serializable {

    private String text;
    private byte arrayBuffer[];

    public TextMessage() {
    }

    public TextMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getArrayBuffer() {
        return arrayBuffer;
    }

    public void setArrayBuffer(byte[] arrayBuffer) {
        this.arrayBuffer = arrayBuffer;
    }

    public TextMessage(String text, byte[] arrayBuffer) {
        this.text = text;
        this.arrayBuffer = arrayBuffer;
    }
}
