package dk.aau.cs.ds308e;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationTest {

    public static void main(String[] args) {
        Locale localeEn = new Locale("en","US");
        ResourceBundle buttonsEn = ResourceBundle.getBundle("Buttons", localeEn);
        System.out.println("English: " + buttonsEn.getString("menu_tours"));

        Locale localeDa = new Locale("da","DA");
        ResourceBundle buttonsDa = ResourceBundle.getBundle("Buttons", localeDa);
        System.out.println("Dansk: " + buttonsDa.getString("menu_tours"));

    }
}
