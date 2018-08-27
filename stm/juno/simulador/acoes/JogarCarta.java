package stm.juno.simulador.acoes;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;
import stm.juno.simulador.cartas.Carta;

public class JogarCarta extends Acao {

    private boolean aindaNaoComprada;
    private int indiceCarta;
    private Carta carta;

    public JogarCarta(int indiceCarta, Carta carta) {
        this.indiceCarta = Math.abs(indiceCarta);
        this.carta = carta;
        aindaNaoComprada = indiceCarta < 0;
    }

    @Override
    public void executar(Uno jogo, boolean verboso) {
        Carta jogada = jogo.getJogadores().getJogadorAtual().getCartas().remove(indiceCarta);
        jogo.getPilhaDescarte().descartar(jogada);
        if (verboso)
            Mensageiro.imprimir("Jogador " + jogo.getJogadores().getIndiceJogadorAtual() + " jogou " + carta);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JogarCarta) {
            JogarCarta jc = (JogarCarta) obj;
            return carta.equals(jc.carta) && aindaNaoComprada == jc.aindaNaoComprada;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (!aindaNaoComprada) {
            return "Jogar " + carta + " que está no índice " + indiceCarta;
        } else {
            return "Jogar " + carta + " que acabamos de comprar";
        }
    }
}
