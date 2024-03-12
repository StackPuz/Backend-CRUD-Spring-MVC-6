package app.config;

import app.editor.*;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class Controller {

    @InitBinder
    public void initializeBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new BytesEditor());
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(Time.class, new TimeEditor());
        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
    }
}