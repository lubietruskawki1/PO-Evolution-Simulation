package zad1;

import java.util.*;

/* Założenia:
* - W danej turze najpierw w miarę potrzeby na polach żywieniowych pojawia się jedzenie,
*   a potem działają roby (jeden po drugim).
* - W każdej turze roby najpierw wykonują swój program, potem zużywają energię na przeżycie
*   tury (koszt_tury), a następnie powielają się.
* - Roby wykonują swój program przed zużyciem energii na przeżycie tury, ponieważ podczas
*   wykonywania programu może nastąpić zwiększenie ich liczby jednostek energii (podczas
*   jedzenia).
* - Martwe roby są usuwane z planszy dopiero na początku nowej tury, jednak nie są wliczane
*   do statystyk z tury, w której umarły (są uwzględnione w liczbie robów na planszy, ale
*   wartości ich długości programu, energii i wieku nie są wliczane do średnich oraz nie mogą
*   być wartością minimalną bądź maksymalną). */

public class Symulacja {
    private final Parametry parametry;
    private final Plansza plansza;
    Powielanie powielanie;

    public Symulacja(String sciezka_do_pliku_z_plansza, String sciezka_do_pliku_z_parametrami)
            throws Parametry.NiepoprawneParametry, Plansza.NiepoprawnaPlansza {
        this.parametry = new Parametry(sciezka_do_pliku_z_parametrami);
        this.plansza = new Plansza(sciezka_do_pliku_z_plansza,
                                   parametry.dajRozmiarPlanszyX(), parametry.dajRozmiarPlanszyY(),
                                   parametry.dajIleDajeJedzenie(), parametry.dajIleRosnieJedzenie());
        this.powielanie = new Powielanie(parametry.dajPrUsunieciaInstr(),
                                         parametry.dajPrDodaniaInstr(),
                                         parametry.dajPrZmianyInstr(),
                                         parametry.dajSpisInstr().toCharArray());

    }

    public void generujRoby(int pocz_ile_robow, int rozmiar_planszy_x, int rozmiar_planszy_y,
                            double pocz_energia, String pocz_progr, double koszt_tury,
                            double pr_powielenia, double ulamek_energii_rodzica,
                            double limit_powielania) {
        /* Dodajemy na planszę pocz_ile_robow robów w losowych miejscach, skierowanych w
         *  losowych kierunkach. */
        for (int i = 1; i <= pocz_ile_robow; ++i) {
            Random r = new Random();
            /* Losujemy jego współrzędną x (kolumnę), y (wiersz) oraz kierunek. */
            int x = r.nextInt(rozmiar_planszy_x);
            int y = r.nextInt(rozmiar_planszy_y);
            int kierunek = r.nextInt(4);
            this.plansza.dodajRoba(new Rob(pocz_energia, pocz_progr.toCharArray(),
                                           Rob.Kierunek.values()[kierunek], this.plansza, x, y,
                                           koszt_tury, pr_powielenia, ulamek_energii_rodzica,
                                           limit_powielania, this.powielanie, i));
        }
        plansza.zaktualizujLiczbeRobow();
    }

    public void symulujEwolucje() {
        generujRoby(this.parametry.dajPoczIleRobow(), this.plansza.dajRozmiarPlanszyX(),
                    this.plansza.dajRozmiarPlanszyY(), this.parametry.dajPoczEnergia(),
                    this.parametry.dajPoczProgr(), this.parametry.dajKosztTury(),
                    this.parametry.dajPrPowielenia(), this.parametry.dajUlamekEnergiiRodzica(),
                    this.parametry.dajLimitPowielania());

        int ile_tur = this.parametry.dajIleTur();
        int co_ile_wypisz = this.parametry.dajCoIleWypisz();

        System.out.println(new StanSymulacji(this.plansza).opisPrzedRozpoczeciemEwolucji());
        int ile_tur_minelo = 0;
        for (int i = 1; i <= ile_tur; ++i) {
            this.plansza.wykonajTure();
            if (ile_tur_minelo == co_ile_wypisz) {
                System.out.println(new StanSymulacji(this.plansza).opisPoTurze(i));
                ile_tur_minelo = 0;
            }
            ++ile_tur_minelo;
            System.out.println(i + ", " + new StanSymulacji(this.plansza).podstawoweDane());
        }
        System.out.println(new StanSymulacji(this.plansza).opisPoZakonczeniuEwolucji());
    }

    public static void main(String[] args)
            throws Parametry.NiepoprawneParametry, Plansza.NiepoprawnaPlansza {
        Symulacja symulacja = new Symulacja(args[0], args[1]);
        symulacja.symulujEwolucje();
    }
}