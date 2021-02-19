package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto,pankki,viite);

    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void tuoteMeneeKoriinVarastostaJaOstoSuoriutuu() {
        when(viite.uusi()).thenReturn(666);
        when(varasto.saldo(13)).thenReturn(1);
        when(varasto.haeTuote(13)).thenReturn(new Tuote(13, "tuote", 4));
        k.aloitaAsiointi();
        k.lisaaKoriin(13);
        k.tilimaksu("Aleksi", "54321");

        verify(pankki).tilisiirto("Aleksi", 666, "54321", "33333-44455", 4);
    }

    @Test
    public void kaksiTuotettaJoitaVarastossaOnMeneeKoriinJaTilisiirtoMeneeOikein() {
        when(viite.uusi()).thenReturn(12345);
        when(varasto.saldo(6)).thenReturn(7);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 1));
        when(varasto.saldo(7)).thenReturn(3);
        when(varasto.haeTuote(7)).thenReturn(new Tuote(7, "Tuote2", 2));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.lisaaKoriin(7);
        k.tilimaksu("Simo", "98765");

        verify(pankki).tilisiirto("Simo", 12345, "98765", "33333-44455", 3);
    }

    @Test
    public void kaksiSamaaaTuotettaJoitaVarastossaOnMeneeKoriinJaTilisiirtoMeneeOikein() {
        when(viite.uusi()).thenReturn(12345);
        when(varasto.saldo(6)).thenReturn(7);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 1));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.lisaaKoriin(6);
        k.tilimaksu("Simo", "98765");

        verify(pankki).tilisiirto("Simo", 12345, "98765", "33333-44455", 2);
    }

    @Test
    public void kaksiTuotettaJoistaVarastossaToistaOnMeneeToinenKoriinJaTilisiirtoMeneeOikein() {
        when(viite.uusi()).thenReturn(12345);
        when(varasto.saldo(6)).thenReturn(7);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 1));
        when(varasto.saldo(7)).thenReturn(0);
        when(varasto.haeTuote(7)).thenReturn(new Tuote(7, "Tuote2", 2));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.lisaaKoriin(7);
        k.tilimaksu("Simo", "98765");

        verify(pankki).tilisiirto("Simo", 12345, "98765", "33333-44455", 1);
    }

    @Test
    public void aloitaAsiointiNollaaOstoksetKunnolla() {
        when(viite.uusi()).thenReturn(12345);
        when(varasto.saldo(6)).thenReturn(7);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 100));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.aloitaAsiointi();
        when(varasto.saldo(8)).thenReturn(7);
        when(varasto.haeTuote(8)).thenReturn(new Tuote(8, "Tuote1", 1));
        k.lisaaKoriin(8);
        k.tilimaksu("Simo", "98765");

        verify(pankki).tilisiirto("Simo", 12345, "98765", "33333-44455", 1);
    }

    @Test
    public void kauppaPyytaaUudenViitteenJokaMaksulle() {
        when(varasto.saldo(6)).thenReturn(7);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 100));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.tilimaksu("Simo", "98765");
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 100));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.tilimaksu("Simo", "98765");

        verify(viite, times(2)).uusi();

        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 100));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.tilimaksu("Simo", "98765");

        verify(viite, times(3)).uusi();
    }

    @Test
    public void poistaKoristaPoistaaTuoteenKorista() {
        when(varasto.saldo(6)).thenReturn(7);
        when(varasto.haeTuote(6)).thenReturn(new Tuote(6, "Tuote1", 1));
        when(varasto.saldo(7)).thenReturn(0);
        when(varasto.haeTuote(7)).thenReturn(new Tuote(7, "Tuote2", 2));
        k.aloitaAsiointi();
        k.lisaaKoriin(6);
        k.lisaaKoriin(7);
        k.poistaKorista(7);

        k.tilimaksu("Simo", "98765");
        verify(pankki).tilisiirto("Simo", 0, "98765", "33333-44455", 1);

    }
}