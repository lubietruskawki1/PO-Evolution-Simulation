package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Plansza {
    protected Pole[][] pola;
    private int rozmiar_planszy_x;
    private int rozmiar_planszy_y;
    private final ArrayList<Rob> roby;
    private int liczba_robow;
    private final int liczba_pol; // Przydatna do wypisania stanu symulacji.
    private int liczba_pol_zywieniowych; // Przydatna do wypisania stanu symulacji.
    private final int liczba_pol_pustych; // Przydatna do wypisania stanu symulacji.

    public static class NiepoprawnaPlansza extends Exception {
        public NiepoprawnaPlansza(String wiadomosc) {
            super(wiadomosc);
        }
    }

    public Plansza(String sciezka_do_pliku, int rozmiar_planszy_x, int rozmiar_planszy_y,
                   double ile_daje_jedzenie, int ile_rosnie_jedzenie) throws NiepoprawnaPlansza {
        Scanner plansza_txt;
        try {
            plansza_txt = new Scanner(new File(sciezka_do_pliku));
        } catch (FileNotFoundException e) {
            throw new NiepoprawnaPlansza("Niepoprawna ścieżka do pliku z parametrami");
        }

        this.rozmiar_planszy_x = rozmiar_planszy_x;
        this.rozmiar_planszy_y = rozmiar_planszy_y;

        boolean znamy_rozmiar_planszy_x = true;
        boolean znamy_rozmiar_planszy_y = true;
        if (rozmiar_planszy_x == 0) znamy_rozmiar_planszy_x = false;
        if (rozmiar_planszy_y == 0) znamy_rozmiar_planszy_y = false;

        /* Indeks wiersza w planszy, a zarazem numer wiersza w pliku z planszą. */
        int i = 0;

        /* ArrayList z polami planszy. */
        List<List<Pole>> pola_planszy = new ArrayList<>();

        this.liczba_pol_zywieniowych = 0;

        /* Wczytujemy planszę. */
        while (plansza_txt.hasNextLine()) {
            String wiersz = plansza_txt.nextLine();

            if (!znamy_rozmiar_planszy_x) {
                this.rozmiar_planszy_x = wiersz.length();
                znamy_rozmiar_planszy_x = true;
            }
            else {
                /* Sprawdzamy, czy każdy wiersz w pliku z planszą ma taką samą długość. */
                if (this.rozmiar_planszy_x != wiersz.length()) {
                    throw new NiepoprawnaPlansza("Niepoprawne dane w pliku z planszą");
                }
            }

            /* ArrayList z polami w wierszu o numerze i. */
            List<Pole> wiersz_planszy = new ArrayList<>();

            for (int j = 0; j < this.rozmiar_planszy_x; ++j) {
                if (wiersz.charAt(j) == ' ') {
                    wiersz_planszy.add(new PolePuste(j, i));
                } else if (wiersz.charAt(j) == 'x') {
                    wiersz_planszy.add(new PoleZywieniowe(j, i, ile_daje_jedzenie, ile_rosnie_jedzenie));
                    ++(this.liczba_pol_zywieniowych);
                } else {
                    /* Jeśli w pliku występuje znak inny niż 'x' lub ' ', to plik
                     *  zawiera niepoprawne dane. */
                    throw new NiepoprawnaPlansza("Niepoprawne dane w pliku z planszą");
                }
            }
            pola_planszy.add(wiersz_planszy);

            ++i;
        }
        plansza_txt.close();

        if (!znamy_rozmiar_planszy_y) {
            this.rozmiar_planszy_y = i;
        }

        if (this.rozmiar_planszy_x == 0 || this.rozmiar_planszy_y == 0) {
            /* Plik z planszą jest pusty. */
            throw new NiepoprawnaPlansza("Plik z planszą jest pusty");
        }

        this.pola = new Pole[this.rozmiar_planszy_y][this.rozmiar_planszy_x];
        for (int j = 0; j < this.rozmiar_planszy_y; ++j) {
            System.arraycopy(pola_planszy.get(j).toArray(new Pole[0]),
                      0, this.pola[j], 0, this.rozmiar_planszy_x);
        }
        this.roby = new ArrayList<>();
        this.liczba_robow = 0;
        this.liczba_pol = this.rozmiar_planszy_x * this.rozmiar_planszy_y;
        this.liczba_pol_pustych = this.liczba_pol - this.liczba_pol_zywieniowych;
    }

    protected Pole dajPole(int x, int y) {
        return this.pola[y][x];
    }

    protected void dodajRoba(Rob rob) {
        this.roby.add(rob);
    }

    protected void zaktualizujLiczbeRobow() {
        this.liczba_robow = this.roby.size();
    }

    public void wykonajTure() {
        /* Najpierw w razie potrzeby odradza się jedzenie na polach żywieniowych. */
        for (int i = 0; i < this.rozmiar_planszy_y; ++i) {
            for (int j = 0; j < this.rozmiar_planszy_x; ++j) {
                this.pola[i][j].wykonajTure();
            }
        }
        /* Następnie usuwamy z planszy martwe roby. */
        Iterator<Rob> iter = this.roby.iterator();
        while (iter.hasNext()) {
            Rob rob = iter.next();
            if (rob.jestMartwy()) {
                iter.remove();
                --this.liczba_robow;
            }
        }
        this.roby.trimToSize();
        /* Teraz roby będą wykonywać swoje programy i powielać się.
        *  UWAGA: Podczas powielania do tablicy robów tej planszy mogą zostać
        *  dodane nowe roby, których programy mają być wykonywane dopiero od
        *  następnej tury, więc liczbę robów aktualizujemy dopiero, gdy
        *  wszystkie roby położone na planszy nie w tej turze skończą swoje
        *  działanie. */
        for (int i = 0; i < this.liczba_robow; ++i) {
            this.roby.get(i).wykonajProgram();
            this.roby.get(i).powielSie();
        }
        this.zaktualizujLiczbeRobow();
    }

    protected int nastepnyIdentyfikator() {
        /* Identyfikator następnego roba, który zostanie dodany na planszę. */
        return this.roby.get(this.roby.size() - 1).dajIdentyfikator() + 1;
    }

    public int liczbaPolZZywnoscia() {
        int suma = 0;
        for (int i = 0; i < this.rozmiar_planszy_y; ++i) {
            for (int j = 0; j < this.rozmiar_planszy_x; ++j) {
                if (this.pola[i][j].ma_jedzenie) ++suma;
            }
        }
        return suma;
    }

    protected int dajRozmiarPlanszyX() { return this.rozmiar_planszy_x; }
    protected int dajRozmiarPlanszyY() { return this.rozmiar_planszy_y; }
    public int dajLiczbePol() { return this.liczba_pol; }
    public int dajLiczbePolZywieniowych() { return this.liczba_pol_zywieniowych; }
    public int dajLiczbePolPustych() { return this.liczba_pol_pustych; }
    public int dajLiczbeRobow() { return this.liczba_robow; }
    public ArrayList<Rob> dajRoby() { return new ArrayList<>(roby); }
}
