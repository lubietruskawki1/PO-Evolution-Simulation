package zad1;

import java.math.BigDecimal;

public class PoleZywieniowe extends Pole {
    protected BigDecimal ile_daje_jedzenie;
    private final int ile_rosnie_jedzenie;
    private int ile_tur_minelo;

    public PoleZywieniowe(int x, int y, double ile_daje_jedzenie, int ile_rosnie_jedzenie) {
        this.x = x;
        this.y = y;
        this.ile_daje_jedzenie = new BigDecimal(ile_daje_jedzenie);
        this.ile_rosnie_jedzenie = ile_rosnie_jedzenie;
        this.ma_jedzenie = true;
        this.ile_tur_minelo = 0;
    }

    public void usunJedzenie() {
        this.ma_jedzenie = false;
        this.ile_tur_minelo = 0;
    }

    protected void wykonajTure() {
        if (!this.ma_jedzenie) {
            ++this.ile_tur_minelo;
            if (this.ile_tur_minelo == ile_rosnie_jedzenie) {
                this.ma_jedzenie = true;
            }
        }
    }
}
