package zad1;

public class Jedz extends Instrukcja {
    protected void wykonajInstrukcje(Rob rob) {
        int x = rob.wspX();
        int y = rob.wspY();

        int x_lewo = rob.lewaWspX();
        int x_prawo = rob.prawaWspX();
        int y_gora = rob.gornaWspY();
        int y_dol = rob.dolnaWspY();

        if (rob.plansza.dajPole(x_lewo, y_gora).ma_jedzenie) {
            /* Na polu po lewej górnej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.LEWO);
            rob.zmienPozycje(x_lewo, y_gora);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y_gora][x_lewo]);

        } else if (rob.plansza.dajPole(x, y_gora).ma_jedzenie) {
            /* Na polu na górze jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.GORA);
            rob.zmienPozycje(x, y_gora);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y_gora][x]);

        } else if (rob.plansza.dajPole(x_prawo, y_gora).ma_jedzenie) {
            /* Na polu po prawej górnej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.PRAWO);
            rob.zmienPozycje(x_prawo, y_gora);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y_gora][x_prawo]);

        } else if (rob.plansza.dajPole(x_lewo, y).ma_jedzenie) {
            /* Na polu po lewej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.LEWO);
            rob.zmienPozycje(x_lewo, y);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y][x_lewo]);

        } else if (rob.plansza.dajPole(x_prawo, y).ma_jedzenie) {
            /* Na polu po prawej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.PRAWO);
            rob.zmienPozycje(x_prawo, y);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y][x_prawo]);

        } else if (rob.plansza.dajPole(x_lewo, y_dol).ma_jedzenie) {
            /* Na polu po lewej dolnej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.LEWO);
            rob.zmienPozycje(x_lewo, y_dol);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y_dol][x_lewo]);

        } else if (rob.plansza.dajPole(x, y_dol).ma_jedzenie) {
            /* Na polu na dole jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.DOL);
            rob.zmienPozycje(x, y_dol);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y_dol][x]);

        } else if (rob.plansza.dajPole(x_prawo, y_dol).ma_jedzenie) {
            /* Na polu po prawej dolnej stronie jest jedzenie. */
            rob.zmienKierunek(Rob.Kierunek.PRAWO);
            rob.zmienPozycje(x_prawo, y_dol);
            rob.zjedzJedzenie((PoleZywieniowe) rob.plansza.pola[y_dol][x_prawo]);
        }
    }
}
