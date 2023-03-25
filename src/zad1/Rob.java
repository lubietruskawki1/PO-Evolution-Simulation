package zad1;

import java.math.BigDecimal;
import java.util.Arrays;

public class Rob {
    private BigDecimal energia;
    private final char[] program;
    private Kierunek kierunek;
    protected Plansza plansza;
    private int x;
    private int y;
    private final BigDecimal koszt_tury;
    private boolean martwy;
    private int wiek;
    private final double pr_powielenia;
    private final BigDecimal ulamek_energii_rodzica;
    private final BigDecimal limit_powielania;
    private final Powielanie powielanie;
    private final int identyfikator; // Identyfikator roba
                                     // (w celu rozróżnienia ich przy wypisywaniu stanów robów).

    public Rob(double energia, char[] program, Kierunek kierunek, Plansza plansza, int x, int y,
               double koszt_tury, double pr_powielenia, double ulamek_energii_rodzica,
               double limit_powielania, Powielanie powielanie, int identyfikator) {
        this.energia = BigDecimal.valueOf(energia);
        this.program = new char[program.length];
        System.arraycopy(program, 0, this.program, 0, program.length);
        this.kierunek = kierunek;
        this.plansza = plansza;
        this.x = x;
        this.y = y;
        this.koszt_tury = BigDecimal.valueOf(koszt_tury);
        this.martwy = false;
        this.wiek = 0;
        this.pr_powielenia = pr_powielenia;
        this.ulamek_energii_rodzica = BigDecimal.valueOf(ulamek_energii_rodzica);
        this.limit_powielania = BigDecimal.valueOf(limit_powielania);
        this.powielanie = powielanie;
        this.identyfikator = identyfikator;
    }

    public enum Kierunek {
        GORA, PRAWO, DOL, LEWO
    }

    protected void zmienPozycje(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void zmienKierunek(Kierunek kierunek) {
        this.kierunek = kierunek;
    }

    protected int lewaWspX() {
        int x_lewo = this.x - 1;
        if (x_lewo == -1) x_lewo = this.plansza.dajRozmiarPlanszyX() - 1;
        return x_lewo;
    }

    protected int prawaWspX() {
        int x_prawo = this.x + 1;
        if (x_prawo == this.plansza.dajRozmiarPlanszyX()) x_prawo = 0;
        return x_prawo;
    }

    protected int gornaWspY() {
        int y_gora = this.y - 1;
        if (y_gora == -1) y_gora = this.plansza.dajRozmiarPlanszyY() - 1;
        return y_gora;
    }

    protected int dolnaWspY() {
        int y_dol = this.y + 1;
        if (y_dol == this.plansza.dajRozmiarPlanszyY()) y_dol = 0;
        return y_dol;
    }

    private void wykonajInstrukcje(char znak) {
        Instrukcja instrukcja;
        switch (znak) {
            case 'l':
                instrukcja = new Lewo();
                instrukcja.wykonajInstrukcje(this);
                break;
            case 'p':
                instrukcja = new Prawo();
                instrukcja.wykonajInstrukcje(this);
                break;
            case 'i':
                instrukcja = new Idz();
                instrukcja.wykonajInstrukcje(this);
                break;
            case 'w':
                instrukcja = new Wachaj();
                instrukcja.wykonajInstrukcje(this);
                break;
            case 'j':
                instrukcja = new Jedz();
                instrukcja.wykonajInstrukcje(this);
                break;
        }
    }

    protected void wykonajProgram() {
        int i = 0;
        while (this.energia.doubleValue() > 0 && i < this.program.length) {
            wykonajInstrukcje(this.program[i]);
            ++i;
            this.energia = this.energia.subtract(new BigDecimal(1));
        }
        /* Jeśli po wykonaniu programu roba nie stać na przeżycie tury, to go zabijamy. */
        if (this.energia.compareTo(this.koszt_tury) < 0) {
            zabijRoba();
            return;
        }
        this.energia = this.energia.subtract(this.koszt_tury);
        ++wiek;
    }

    private void zabijRoba() {
        this.martwy = true;
    }

    protected void zjedzJedzenie(PoleZywieniowe pole) {
        this.energia = this.energia.add(pole.ile_daje_jedzenie);
        pole.usunJedzenie();
    }

    protected void powielSie() {
        /* Trzeba sprawdzić, czy nie jest martwy, ponieważ mógł umrzeć podczas
        *  wykonywania programu (nie mieć wystarczająco energii, żeby przeżyć turę). */
        if (!this.martwy) {
            if (Math.random() <= this.pr_powielenia && this.energia.compareTo(this.limit_powielania) >= 0) {
                this.energia = this.energia.subtract(this.ulamek_energii_rodzica);
                plansza.dodajRoba(this.powielanie.dziecko(this));
            }
        }
    }

    protected boolean jestMartwy() { return this.martwy; }
    protected int dajDlugoscProgramu() { return this.program.length; }
    protected BigDecimal dajEnergie() { return this.energia; }
    protected int dajWiek() { return this.wiek; }
    protected int wspX() { return this.x; }
    protected int wspY() { return this.y; }
    protected char[] dajProgram() { return Arrays.copyOf(this.program, this.program.length); }
    protected Kierunek dajKierunek() { return this.kierunek; }
    protected BigDecimal dajKosztTury() { return this.koszt_tury; }
    protected double dajPrPowielenia() { return this.pr_powielenia; }
    protected BigDecimal dajUlamekEnergiiRodzica() { return this.ulamek_energii_rodzica; }
    protected BigDecimal dajLimitPowielania() { return this.limit_powielania; }
    protected Powielanie dajPowielanie() {
        double pr_usuniecia_instr = this.powielanie.dajPrUsunieciaInstr();
        double pr_dodania_instr = this.powielanie.dajPrDodaniaInstr();
        double pr_zmiany_instr = this.powielanie.dajPrZmianyinstr();
        char[] spis_instr = this.powielanie.dajSpisInstr();
        return new Powielanie(pr_usuniecia_instr, pr_dodania_instr, pr_zmiany_instr, spis_instr);
    }
    protected int dajIdentyfikator() { return this.identyfikator; }
}
