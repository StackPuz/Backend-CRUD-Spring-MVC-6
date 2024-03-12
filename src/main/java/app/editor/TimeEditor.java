package app.editor;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeEditor extends PropertyEditorSupport {
    
    private DateFormat format = new SimpleDateFormat("HH:mm:ss");
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.isEmpty()) {
            setValue(null);
        }
        else {
            try {
                setValue(new Time(format.parse(text).getTime()));
            }
            catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}