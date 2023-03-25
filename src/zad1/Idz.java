package zad1;

public class Idz extends Instrukcja {
    protected void wykonajInstrukcje(Rob rob) {
        switch (rob.dajKierunek()) {
            case GORA:
                rob.zmienPozycje(rob.wspX(), rob.gornaWspY());
                break;
            case PRAWO:
                rob.zmienPozycje(rob.prawaWspX(), rob.wspY());
                break;
            case DOL:
                rob.zmienPozycje(rob.wspX(), rob.dolnaWspY());
                break;
            case LEWO:
                rob.zmienPozycje(rob.lewaWspX(), rob.wspY());
                break;
        }
    }
}
