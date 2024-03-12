package app.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateEditor extends PropertyEditorSupport {
    
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private DateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.isEmpty()) {
            setValue(null);
        }
        else {
            try {
                setValue(text.length() == 10 ? dateFormat.parse(text) : dateTimeFormat.parse(text));
            }
            catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}