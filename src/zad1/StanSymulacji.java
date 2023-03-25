package zad1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class StanSymulacji {
    private final int liczba_robow;
    private final ArrayList<Rob> roby;
    private final int liczba_pol;
    private final int liczba_pol_zywieniowych;
    private final int liczba_pol_z_zywnoscia;
    private final int liczba_pol_pustych;

    public StanSymulacji(Plansza plansza) {
        this.liczba_robow = plansza.dajLiczbeRobow();
        this.roby = plansza.dajRoby();
        this.liczba_pol = plansza.dajLiczbePol();
        this.liczba_pol_zywieniowych = plansza.dajLiczbePolZywieniowych();
        this.liczba_pol_z_zywnoscia = plansza.liczbaPolZZywnoscia();
        this.liczba_pol_pustych = plansza.dajLiczbePolPustych();
    }

    private int minimalnaDlugoscProgramu() {
        if (this.liczba_robow == 0) return 0;
        if (this.liczbaZywychRobow() == 0) return 0;
        int minimalna_dlugosc_programu = Integer.MAX_VALUE;
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) {
                minimalna_dlugosc_programu = Math.min(minimalna_dlugosc_programu, rob.dajDlugoscProgramu());
            }
        }
        return minimalna_dlugosc_programu;
    }

    private BigDecimal sredniaDlugoscProgramu() {
        if (this.liczba_robow == 0) return BigDecimal.ZERO;
        if (this.liczbaZywychRobow() == 0) return BigDecimal.ZERO;
        BigDecimal suma = new BigDecimal(0);
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) {
                suma = suma.add(BigDecimal.valueOf(rob.dajDlugoscProgramu()));
            }
        }
        BigDecimal srednia = suma.divide(BigDecimal.valueOf(this.liczba_robow), 2, RoundingMode.HALF_UP);
        if (isIntegerValue(srednia)) return BigDecimal.valueOf(srednia.intValue());
        return srednia;
    }

    private int maksymalnaDlugoscProgramu() {
        if (this.liczba_robow == 0) return 0;
        if (this.liczbaZywychRobow() == 0) return 0;
        int maksymalna_dlugosc_programu = 0;
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) {
                maksymalna_dlugosc_programu = Math.max(maksymalna_dlugosc_programu, rob.dajDlugoscProgramu());
            }
        }
        return maksymalna_dlugosc_programu;
    }

    private BigDecimal minimalnaEnergia() {
        if (this.liczba_robow == 0) return BigDecimal.ZERO;
        if (this.liczbaZywychRobow() == 0) return BigDecimal.ZERO;
        BigDecimal minimalna_energia = BigDecimal.valueOf(Double.MAX_VALUE);
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) {
                minimalna_energia = minimalna_energia.min(rob.dajEnergie());
            }
        }
        if (isIntegerValue(minimalna_energia)) return BigDecimal.valueOf(minimalna_energia.intValue());
        return minimalna_energia.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal sredniaEnergia() {
        if (this.liczba_robow == 0) return BigDecimal.ZERO;
        if (this.liczbaZywychRobow() == 0) return BigDecimal.ZERO;
        BigDecimal suma = new BigDecimal(0);
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) suma = suma.add(rob.dajEnergie());
        }
        BigDecimal srednia =  suma.divide(BigDecimal.valueOf(this.liczba_robow),2, RoundingMode.HALF_UP);
        if (isIntegerValue(srednia)) return BigDecimal.valueOf(srednia.intValue());
        return srednia;
    }

    private BigDecimal maksymalnaEnergia() {
        if (this.liczba_robow == 0) return BigDecimal.ZERO;
        BigDecimal maksymalna_energia = new BigDecimal(0);
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) maksymalna_energia = maksymalna_energia.max(rob.dajEnergie());
        }
        if (isIntegerValue(maksymalna_energia)) return BigDecimal.valueOf(maksymalna_energia.intValue());
        return maksymalna_energia.setScale(2, RoundingMode.HALF_UP);
    }

    private int minimalnyWiek() {
        if (this.liczba_robow == 0) return 0;
        if (this.liczbaZywychRobow() == 0) return 0;
        int minimalny_wiek = Integer.MAX_VALUE;
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) {
                minimalny_wiek = Math.min(minimalny_wiek, rob.dajWiek());
            }
        }
        return minimalny_wiek;
    }

    private BigDecimal sredniWiek() {
        if (this.liczba_robow == 0) return BigDecimal.ZERO;
        if (this.liczbaZywychRobow() == 0) return BigDecimal.ZERO;
        BigDecimal suma = new BigDecimal(0);
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) suma = suma.add(BigDecimal.valueOf(rob.dajWiek()));
        }
        BigDecimal srednia =  suma.divide(BigDecimal.valueOf(this.liczba_robow),2, RoundingMode.HALF_UP);
        if (isIntegerValue(srednia)) return BigDecimal.valueOf(srednia.intValue());
        return srednia;
    }

    private int maksymalnyWiek() {
        if (this.liczba_robow == 0) return 0;
        if (this.liczbaZywychRobow() == 0) return 0;
        int maksymalny_wiek = 0;
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) {
                maksymalny_wiek = Math.max(maksymalny_wiek, rob.dajWiek());
            }
        }
        return maksymalny_wiek;
    }

    public String podstawoweDane() {
        String s = "";
        s += "rob: " + this.liczba_robow + ", ";
        s += "żyw: " + this.liczba_pol_z_zywnoscia + ", ";
        s += "prg: " + this.minimalnaDlugoscProgramu() + "/" +
                       this.sredniaDlugoscProgramu() + "/" +
                       this.maksymalnaDlugoscProgramu() + ", ";
        s += "energ: " + this.minimalnaEnergia() + "/" +
                         this.sredniaEnergia() + "/" +
                         this.maksymalnaEnergia() + ", ";
        s += "wiek: " + this.minimalnyWiek() + "/" +
                        this.sredniWiek() + "/" +
                        this.maksymalnyWiek();
        return s;
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.stripTrailingZeros().scale() <= 0;
    }

    private int liczbaMartwychRobow() {
        int licznik_martwych_robow = 0;
        for (Rob rob : this.roby) {
            if (rob.jestMartwy()) ++licznik_martwych_robow;
        }
        return licznik_martwych_robow;
    }

    private int liczbaZywychRobow() {
        return this.liczba_robow - this.liczbaMartwychRobow();
    }

    private int liczbaNowychRobow() {
        int licznik_nowych_robow = 0;
        for (Rob rob : this.roby) {
            if (rob.dajWiek() == 0) ++licznik_nowych_robow;
        }
        return licznik_nowych_robow;
    }

    private String stanZywegoRoba(Rob rob) {
        String s = " - Rob o identyfikatorze " + rob.dajIdentyfikator() + ": ";
        s += "stan: żywy, ";
        s += "położenie: (" + rob.wspX() + ", " + rob.wspY() + "), ";
        s += "kierunek: " + rob.dajKierunek().name().toLowerCase() + ", ";
        s += "liczba jednostek energii: " +
             rob.dajEnergie().setScale(2, RoundingMode.HALF_UP) + ", ";
        s += "wiek: " + rob.dajWiek() + ", ";
        s += "program: " + String.valueOf(rob.dajProgram()) + "\n";
        return s;
    }

    private String stanMartwegoRoba(Rob rob) {
        String s = " - Rob o identyfikatorze " + rob.dajIdentyfikator() + ": ";
        s += "stan: martwy, ";
        s += "położenie: (" + rob.wspX() + ", " + rob.wspY() + "), ";
        s += "kierunek: " + rob.dajKierunek().name().toLowerCase() + ", ";
        s += "wiek: " + rob.dajWiek() + "\n";
        return s;
    }

    public String opisPrzedRozpoczeciemEwolucji() {
        String s = "\nStan symulacji przed rozpoczęciem ewolucji:\n";
        s += "Plansza posiada " + this.liczba_pol + " pól, z czego ";
        s += this.liczba_pol_zywieniowych +
             " to pola żywieniowe i na wszystkich znajduje się pożywienie. ";
        s += "Pozostałe " + this.liczba_pol_pustych + " pól to pola puste.\n";
        if (this.liczba_robow > 0) {
            s += "Na planszy znajduje się " + this.liczba_robow + " robów, z czego wszystkie działają.\n";
            s += "Roby umieszczone na planszy:\n";
            StringBuilder sb = new StringBuilder();
            for (Rob rob : this.roby) {
                sb.append(stanZywegoRoba(rob));
            }
            s += sb.toString();
            s += "Minimalna, średnia i maksymalna długość programu robów jest taka sama i wynosi: " +
                 this.minimalnaDlugoscProgramu() + ".\n";
            s += "Minimalna, średnia i maksymalna energia roba jest taka sama i wynosi: " +
                 this.minimalnaEnergia() + ".\n";
            s += "Minimalny, średni i maksymalny wiek roba jest taki sam i wynosi: " +
                 this.minimalnyWiek() + ".\n";
        } else {
            s += "Na planszy nie ma żadnych robów.\n";
        }
        return s;
    }

    private String opisStanuRobow(String s) {
        StringBuilder sb = new StringBuilder();
        for (Rob rob : this.roby) {
            if (!rob.jestMartwy()) sb.append(stanZywegoRoba(rob));
            else sb.append(stanMartwegoRoba(rob));
        }
        s += sb.toString();
        s += "Długość programu robów:\n";
        s += " * minimalna: " + this.minimalnaDlugoscProgramu() + ",\n";
        s += " * średnia: " + this.sredniaDlugoscProgramu() + ",\n";
        s += " * maksymalna: " + this.maksymalnaDlugoscProgramu() + ".\n";
        s += "Energia roba:\n";
        s += " * minimalna: " + this.minimalnaEnergia() + ",\n";
        s += " * średnia: " + this.sredniaEnergia() + ",\n";
        s += " * maksymalna: " + this.maksymalnaEnergia() + ".\n";
        s += "Wiek roba:\n";
        s += " * minimalny: " + this.minimalnyWiek() + ",\n";
        s += " * średni: " + this.sredniWiek() + ",\n";
        s += " * maksymalny: " + this.maksymalnyWiek() + ".\n";
        return s;
    }

    public String opisPoTurze(int numer_tury) {
        String s = "\nBieżący stan symulacji po turze nr " + numer_tury + ":\n";
        s += "Plansza posiada " + this.liczba_pol + " pól, z czego ";
        s += this.liczba_pol_zywieniowych + " to pola żywieniowe, obecnie pożywienie znajduje na ";
        s += this.liczba_pol_z_zywnoscia + " z nich. ";
        s += "Pozostałe " + this.liczba_pol_pustych + " pól to pola puste.\n";
        if (this.liczba_robow > 0) {
            s += "Na planszy znajduje się " + this.liczba_robow + " robów, z czego ";
            s += this.liczbaZywychRobow() + " robów działają, natomiast pozostałe ";
            s += this.liczbaMartwychRobow() +
                 " robów przestało działać podczas tej tury i od następnej tury znikną z planszy.\n";
            s += "Podczas tej tury " + this.liczbaNowychRobow() +
                 " robów się powieliło i na planszy pojawiło się ";
            s += this.liczbaNowychRobow() + " nowych robów.\n";
            s += "Roby umieszczone na planszy:\n";
            s = opisStanuRobow(s);
        } else {
            s += "Na planszy nie ma żadnych robów.\n";
        }
        return s;
    }

    public String opisPoZakonczeniuEwolucji() {
        String s = "\nStan symulacji po zakończeniu ewolucji:\n";
        s += "Plansza posiada " + this.liczba_pol + " pól, z czego ";
        s += this.liczba_pol_zywieniowych + " to pola żywieniowe, obecnie pożywienie znajduje na ";
        s += this.liczba_pol_z_zywnoscia + " z nich. ";
        s += "Pozostałe " + this.liczba_pol_pustych + " pól to pola puste.\n";
        if (this.liczba_robow > 0) {
            s += "Na planszy znajduje się " + this.liczba_robow + " robów, z czego ";
            s += this.liczbaZywychRobow() + " robów działają, natomiast pozostałe ";
            s += this.liczbaMartwychRobow() + " robów przestało działać podczas ostatniej tury.\n";
            s += "Podczas ostatniej tury " + this.liczbaNowychRobow() +
                 " robów się powieliło i na planszy pojawiło się ";
            s += this.liczbaNowychRobow() + " nowych robów.\n";
            s = opisStanuRobow(s);
        } else {
            s += "Na planszy nie ma żadnych robów.\n";
        }
        return s;
    }
}
