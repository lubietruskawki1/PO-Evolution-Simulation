package zad1;

public class Prawo extends Instrukcja {
    protected void wykonajInstrukcje(Rob rob) {
        switch (rob.dajKierunek()) {
            case GORA:
                rob.zmienKierunek(Rob.Kierunek.PRAWO);
                break;
            case PRAWO:
                rob.zmienKierunek(Rob.Kierunek.DOL);
                break;
            case DOL:
                rob.zmienKierunek(Rob.Kierunek.LEWO);
                break;
            case LEWO:
                rob.zmienKierunek(Rob.Kierunek.GORA);
                break;
        }
    }
}
