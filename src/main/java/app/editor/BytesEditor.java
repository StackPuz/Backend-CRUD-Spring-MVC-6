package app.editor;

import java.beans.PropertyEditorSupport;
import java.nio.charset.StandardCharsets;

public class BytesEditor extends PropertyEditorSupport {
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text.getBytes(StandardCharsets.UTF_8));
    }
    
}