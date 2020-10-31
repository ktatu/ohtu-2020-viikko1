package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenLisaysEiVaikutaSaldoon() {
        double saldoEnnen = varasto.getSaldo();
        
        varasto.lisaaVarastoon(-10);
        
        assertEquals(saldoEnnen, varasto.getSaldo(), vertailuTarkkuus);
    }

    // Eli jos saldo + lisays > kapasiteetti, niin saldoksi tulee kapasiteetti ja ylim��r�inen menee hukkaan
    @Test
    public void ylimenevaLisaysMeneeHukkaan() {
        varasto.lisaaVarastoon(varasto.getTilavuus() + 10);
        
        assertEquals(varasto.getTilavuus(), varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenSaldostaOttoEiVaikutaSaldoon() {
        double saldoEnnen = varasto.getSaldo();
        
        varasto.otaVarastosta(-10);
        
        assertEquals(saldoEnnen, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void kaikkiSaldoAnnetaanJosOttoOnEnemmanKuinSaldo() {
        varasto.lisaaVarastoon(5);
        
        double otto = varasto.otaVarastosta(10);
        
        assertEquals(5, otto, vertailuTarkkuus);
    }
    
    @Test
    public void varastoTyhjentyyJosOttoOnEnemmanKuinSaldo() {
        varasto.lisaaVarastoon(5);
        
        varasto.otaVarastosta(10);
        
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toStringTulostaaPaljonkoSaldoOn() {
        varasto.lisaaVarastoon(5);
        
        String toStringTest = varasto.toString();
        assertTrue(toStringTest.contains("saldo = 5"));
    }
    
    @Test
    public void toStringTulostaaJaljellaOlevanTilan() {
        varasto.lisaaVarastoon(6);
        
        String toStringTest = varasto.toString();
        assertTrue(toStringTest.contains("tilaa 4.0"));
    }
    
    // Konstruktoritestit
    
    @Test
    public void nollaTilavuudenVarastonInitialisointi() {
        Varasto tilTesti = new Varasto(0);
        assertEquals(0, tilTesti.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void varastonInitialisointiTilavuudellaJaAlkusaldolla() {
        Varasto var = new Varasto(10, 5);
        
        assertEquals(10, var.getTilavuus(), vertailuTarkkuus);
        assertEquals(5, var.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenAlkusaldoMuuttuuNollaksi() {
        Varasto var = new Varasto(10, -10);
        
        assertEquals(0, var.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void tilavuudenYlimenevaAlkusaldoTayttaaVaraston() {
        // Ja ylimenev� osa menee hukkaan
        Varasto var = new Varasto(10, 20);
        
        assertEquals(10, var.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenTilavuusTuottaaNollaTilaisenVaraston() {
        Varasto var = new Varasto(-10, 10);
        Varasto var2 = new Varasto(-10);
        
        assertEquals(0, var.getTilavuus(), vertailuTarkkuus);
        assertEquals(0, var2.getTilavuus(), vertailuTarkkuus);
    }
}