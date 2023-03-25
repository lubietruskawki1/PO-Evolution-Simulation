package zad1;

public class Wachaj extends Instrukcja {
    protected void wykonajInstrukcje(Rob rob) {
        int x = rob.wspX();
        int y = rob.wspY();

        int x_lewo = rob.lewaWspX();
        int x_prawo = rob.prawaWspX();
        int y_gora = rob.gornaWspY();
        int y_dol = rob.dolnaWspY();

        if (rob.plansza.dajPole(x_lewo, y).ma_jedzenie) {
            /* Na polu po lewej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.LEWO);

        } else if (rob.plansza.dajPole(x_prawo, y).ma_jedzenie) {
            /* Na polu po prawej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.PRAWO);

        } else if (rob.plansza.dajPole(x, y_gora).ma_jedzenie) {
            /* Na polu na górze jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.GORA);

        } else if (rob.plansza.dajPole(x, y_dol).ma_jedzenie) {
            /* Na polu na dole jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.DOL);
        }
    }
}
