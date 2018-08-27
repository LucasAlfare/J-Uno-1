package stm.juno.simulador.pilhas;

import stm.juno.simulador.Uno;
import stm.juno.simulador.cartas.Carta;
import stm.juno.simulador.cartas.CorCarta;
import stm.juno.simulador.cartas.TipoCarta;

import java.util.ArrayList;

public class PilhaDescarte extends ArrayList<Carta> {

    private CorCarta ultimaCor;
    private boolean acaoPendente;

    public PilhaDescarte(Uno jogo) {
        Carta primeira;

        while (true) {
            primeira = jogo.getPilhaCompra().comprar(1, jogo)[0];
            if (primeira.getTipo().equals(TipoCarta.CURINGA_COMPRAR_QUATRO)) {
                jogo.getPilhaCompra().add(0, primeira);
            } else {
                break;
            }
        }

        add(primeira);
        ultimaCor = primeira.getCor();
        acaoPendente = !primeira.getTipo().equals(TipoCarta.NUMERO);
    }

    public PilhaDescarte(PilhaDescarte cobaia) {
        ultimaCor = cobaia.ultimaCor;
        acaoPendente = cobaia.acaoPendente;
        addAll(cobaia);
    }

    public boolean temAcaoPendente() {
        return acaoPendente;
    }

    public void setAcaoPendente(boolean acaoPendente) {
        this.acaoPendente = acaoPendente;
    }

    public void setUltimaCor(CorCarta ultimaCor) {
        this.ultimaCor = ultimaCor;
    }

    public void descartar(Carta carta) {
        add(carta);
        ultimaCor = carta.getCor();
        acaoPendente = !carta.getTipo().equals(TipoCarta.NUMERO) && !carta.getTipo().equals(TipoCarta.CURINGA);
    }

    public CorCarta getUltimaCor() {
        return ultimaCor;
    }

    public Carta getUltimaJogada() {
        return get(size() - 1);
    }
}
