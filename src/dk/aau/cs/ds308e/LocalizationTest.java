package dk.aau.cs.ds308e;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationTest {

    static ResourceBundle labels;

    public static void main(String[] args) {
        setLocale("en", "US");
        System.out.println("english: " + labels.getString("tour"));

        setLocale("da", "DA");
        System.out.println("dansk: " + labels.getString("tour"));

    }

    static void setLocale(String language, String country)
    {
        Locale locale = new Locale(language,country);
        labels = ResourceBundle.getBundle("Labels", locale);
    }
}
