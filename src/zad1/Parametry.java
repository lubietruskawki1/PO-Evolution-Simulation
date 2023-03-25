package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Parametry {
    private int ile_tur = -1;
    private int rozmiar_planszy_x = 0;
    private int rozmiar_planszy_y = 0;
    private int pocz_ile_robow = -1;
    private String pocz_progr = "";
    private String spis_instr = "";
    private double pocz_energia = -1.0;
    private double ile_daje_jedzenie = -1.0;
    private int ile_rosnie_jedzenie = -1;
    private double koszt_tury = -1.0;
    private double pr_powielenia = -1.0;
    private double ulamek_energii_rodzica = -1.0;
    private double limit_powielania = -1.0;
    private double pr_usuniecia_instr = -1.0;
    private double pr_dodania_instr = -1.0;
    private double pr_zmiany_instr = -1.0;
    private int co_ile_wypisz = -1;

    /* Zliczamy ile parametrów jest podanych w pliku z parametrami - musi
     *  wystąpić 15 (rozmiar_planszy_x oraz rozmiar_planszy_y nie muszą być
     *  podane, więc nie wliczamy ich - gdy je wczytamy, nie powiększamy
     *  licznika). */
    int licznik_danych = 0;
    private final int poprawna_ilosc_parametrow = 15;

    public static class NiepoprawneParametry extends Exception {
        public NiepoprawneParametry(String wiadomosc) {
            super(wiadomosc);
        }
    }

    public Parametry(String sciezka_do_pliku) throws NiepoprawneParametry {
        Scanner parametry_txt;
        try {
            parametry_txt = new Scanner(new File(sciezka_do_pliku));
        } catch (FileNotFoundException e) {
            throw new NiepoprawneParametry("Niepoprawna ścieżka do pliku z parametrami");
        }

        /* Wczytujemy parametry. */
        while (parametry_txt.hasNextLine()) {
            Scanner wiersz = new Scanner(parametry_txt.nextLine()).useLocale(Locale.US);
            sprawdzParametr(wiersz);
            wiersz.close();
        }

        parametry_txt.close();

        if (licznik_danych != poprawna_ilosc_parametrow) {
            throw new NiepoprawneParametry("Niepoprawna ilość parametrów");
        }

        sprawdzSpisInstr(spis_instr);
    }


    private void sprawdzParametr(Scanner wiersz) throws NiepoprawneParametry {
        /* Jeśli którykolwiek wczytany parametr nie ma wartości -1 lub 0 (w przypadku
         *  rozmiar_planszy_x oraz rozmiar_planszy_y), to oznacza, że dany parametr
         *  występuje w pliku więcej niż raz, czyli dane są niepoprawne. */
        String nazwa = wiersz.next();
        switch (nazwa) {
            case "ile_tur":
                if (ile_tur != -1) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                ile_tur = wiersz.nextInt();
                ++licznik_danych;
                break;
            case "rozmiar_planszy_x":
                if (rozmiar_planszy_x != 0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                rozmiar_planszy_x = wiersz.nextInt();
                if (rozmiar_planszy_x <= 0) {
                    throw new NiepoprawneParametry("Niepoprawna wartość parametru");
                }
                break;
            case "rozmiar_planszy_y":
                if (rozmiar_planszy_y != 0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                rozmiar_planszy_y = wiersz.nextInt();
                if (rozmiar_planszy_y <= 0) {
                    throw new NiepoprawneParametry("Niepoprawna wartość parametru");
                }
                break;
            case "pocz_ile_robów":
                if (pocz_ile_robow != -1) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                pocz_ile_robow = wiersz.nextInt();
                ++licznik_danych;
                break;
            case "pocz_progr":
                if (!pocz_progr.equals("")) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                if (wiersz.hasNext()) {
                    /* pocz_progr może być pusty */
                    pocz_progr = wiersz.next();
                }
                ++licznik_danych;
                break;
            case "pocz_energia":
                if (pocz_energia != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                pocz_energia = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "ile_daje_jedzenie":
                if (ile_daje_jedzenie != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                ile_daje_jedzenie = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "ile_rośnie_jedzenie":
                if (ile_rosnie_jedzenie != -1) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                ile_rosnie_jedzenie = wiersz.nextInt();
                ++licznik_danych;
                break;
            case "koszt_tury":
                if (koszt_tury != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                koszt_tury = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "pr_powielenia":
                if (pr_powielenia != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                pr_powielenia = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "ułamek_energii_rodzica":
                if (ulamek_energii_rodzica != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                ulamek_energii_rodzica = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "limit_powielania":
                if (limit_powielania != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                limit_powielania = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "pr_usunięcia_instr":
                if (pr_usuniecia_instr != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                pr_usuniecia_instr = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "pr_dodania_instr":
                if (pr_dodania_instr != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                pr_dodania_instr = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "pr_zmiany_instr":
                if (pr_zmiany_instr != -1.0) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                pr_zmiany_instr = wiersz.nextDouble();
                ++licznik_danych;
                break;
            case "spis_instr":
                if (!spis_instr.equals("")) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                if (wiersz.hasNext()) {
                    /* spis_instr może być pusty */
                    spis_instr = wiersz.next();
                }
                ++licznik_danych;
                break;
            case "co_ile_wypisz":
                if (co_ile_wypisz != -1) {
                    throw new NiepoprawneParametry("Zduplikowany parametr");
                }
                co_ile_wypisz = wiersz.nextInt();
                ++licznik_danych;
                break;
            default:
                throw new NiepoprawneParametry("Niepoprawna nazwa parametru");
        }
    }

    private void sprawdzSpisInstr(String spis_instr) throws NiepoprawneParametry {
        /* Sprawdzamy, czy w spis_instr nie powtarzają się znaki */
        for (int i = 0; i < spis_instr.length(); ++i) {
            for (int j = i + 1; j < spis_instr.length(); ++j) {
                if (spis_instr.charAt(i) == spis_instr.charAt(j)) {
                    throw new NiepoprawneParametry("Niepoprawna wartość parametru");
                }
            }
        }
    }

    public int dajIleTur() { return this.ile_tur; }
    public int dajRozmiarPlanszyX() { return this.rozmiar_planszy_x; }
    public int dajRozmiarPlanszyY() { return this.rozmiar_planszy_y; }
    public int dajPoczIleRobow() { return this.pocz_ile_robow; }
    public String dajPoczProgr() { return this.pocz_progr; }
    public String dajSpisInstr() { return this.spis_instr; }
    public double dajPoczEnergia() { return this.pocz_energia; }
    public double dajIleDajeJedzenie() { return this.ile_daje_jedzenie; }
    public int dajIleRosnieJedzenie() { return this.ile_rosnie_jedzenie; }
    public double dajKosztTury() { return this.koszt_tury; }
    public double dajPrPowielenia() { return this.pr_powielenia; }
    public double dajUlamekEnergiiRodzica() { return this.ulamek_energii_rodzica; }
    public double dajLimitPowielania() { return this.limit_powielania; }
    public double dajPrUsunieciaInstr() { return this.pr_usuniecia_instr; }
    public double dajPrDodaniaInstr() { return this.pr_dodania_instr; }
    public double dajPrZmianyInstr() { return this.pr_zmiany_instr; }
    public int dajCoIleWypisz() { return this.co_ile_wypisz; }
}
