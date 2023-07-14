package thevelopers.devsoftware.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

    /**
     * Método que verifica se uma data é válida
     * Ele verifica o formato e se a data é posterior a data atual
     * @param dateText a data a ser verificada em formato de texto
     * @return Se a data é válida ou não
     * @throws ParseException se a data não estiver no formato correto
     */
    public static boolean isDateValid(String dateText) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setLenient(false);

        try {
            Date date = format.parse(dateText);
            Date currentDate = new Date();

            return !date.before(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }
}
