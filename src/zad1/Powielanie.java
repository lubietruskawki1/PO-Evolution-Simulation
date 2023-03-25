package zad1;

import java.util.Arrays;
import java.util.Random;

public class Powielanie {
    private final double pr_usuniecia_instr;
    private final double pr_dodania_instr;
    private final double pr_zmiany_instr;
    private final char[] spis_instr;

    public Powielanie(double pr_usuniecia_instr, double pr_dodania_instr,
                      double pr_zmiany_instr, char[] spis_instr) {
        this.pr_usuniecia_instr = pr_usuniecia_instr;
        this.pr_dodania_instr = pr_dodania_instr;
        this.pr_zmiany_instr = pr_zmiany_instr;
        this.spis_instr = new char[spis_instr.length];
        System.arraycopy(spis_instr, 0, this.spis_instr, 0, spis_instr.length);
    }

    public Rob dziecko(Rob rob) {
        double energia = rob.dajUlamekEnergiiRodzica().doubleValue();
        char[] program = zmutowanyProgram(rob.dajProgram());
        Rob.Kierunek kierunek;
        switch (rob.dajKierunek()) {
            case DOL:
                kierunek = Rob.Kierunek.GORA;
                break;
            case GORA:
                kierunek = Rob.Kierunek.DOL;
                break;
            case LEWO:
                kierunek = Rob.Kierunek.PRAWO;
                break;
            case PRAWO:
                kierunek = Rob.Kierunek.LEWO;
                break;
            default:
                Random r = new Random();
                kierunek = Rob.Kierunek.values()[r.nextInt(4)];
                break;
        }
        Plansza plansza = rob.plansza;
        int x = rob.wspX();
        int y = rob.wspY();
        double koszt_tury = rob.dajKosztTury().doubleValue();
        double pr_powielenia = rob.dajPrPowielenia();
        double ulamek_energii_rodzica = rob.dajUlamekEnergiiRodzica().doubleValue();
        double limit_powielania = rob.dajLimitPowielania().doubleValue();
        int identyfikator = rob.plansza.nastepnyIdentyfikator();
        Powielanie powielanie = rob.dajPowielanie();
        return new Rob(energia, program, kierunek, plansza, x, y, koszt_tury, pr_powielenia,
                       ulamek_energii_rodzica, limit_powielania, powielanie, identyfikator);
    }

    private char[] zmutowanyProgram(char[] program) {
        boolean usuniecie_instr = false;
        boolean dodanie_instr = false;
        boolean zmiana_instr = false;

        int rozmiar = program.length;

        if (Math.random() <= this.pr_usuniecia_instr) {
            usuniecie_instr = true;
            --rozmiar;
        }
        if (Math.random() <= this.pr_dodania_instr) {
            dodanie_instr = true;
            ++rozmiar;
        }
        if (Math.random() <= this.pr_zmiany_instr) {
            zmiana_instr = true;
        }

        if (rozmiar <= 0) return new char[0];

        char[] zmutowany_program = new char[rozmiar];

        if (usuniecie_instr && dodanie_instr) {
            System.arraycopy(program, 0, zmutowany_program, 0, program.length - 1);
            Random r = new Random();
            int indeks = r.nextInt(this.spis_instr.length);
            zmutowany_program[zmutowany_program.length - 1] = this.spis_instr[indeks];
        } else if (usuniecie_instr) {
            System.arraycopy(program, 0, zmutowany_program, 0, program.length - 1);
        } else if (dodanie_instr) {
            System.arraycopy(program, 0, zmutowany_program, 0, program.length);
            Random r = new Random();
            int indeks = r.nextInt(this.spis_instr.length);
            zmutowany_program[zmutowany_program.length - 1] = this.spis_instr[indeks];
        } else {
            System.arraycopy(program, 0, zmutowany_program, 0, program.length);
        }

        if (zmiana_instr) {
            Random r = new Random();
            int indeks_spis_instr = r.nextInt(this.spis_instr.length);
            int indeks_zmutowany_program = r.nextInt(zmutowany_program.length);
            zmutowany_program[indeks_zmutowany_program] = this.spis_instr[indeks_spis_instr];
        }

        return zmutowany_program;
    }

    protected double dajPrUsunieciaInstr() { return this.pr_usuniecia_instr; }
    protected double dajPrDodaniaInstr() { return this.pr_dodania_instr; }
    protected double dajPrZmianyinstr() { return this.pr_zmiany_instr; }
    protected char[] dajSpisInstr() { return Arrays.copyOf(this.spis_instr, this.spis_instr.length); }
}
