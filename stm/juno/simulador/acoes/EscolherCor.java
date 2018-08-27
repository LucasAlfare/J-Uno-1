package stm.juno.simulador.acoes;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;
import stm.juno.simulador.cartas.CorCarta;

public class EscolherCor extends Acao {

    private CorCarta cor;

    public EscolherCor(CorCarta cor) {
        this.cor = cor;
    }

    @Override
    public void executar(Uno jogo, boolean verboso) {
        jogo.getPilhaDescarte().setUltimaCor(cor);
        if (verboso)
            Mensageiro.imprimir("Jogador " + jogo.getJogadores().getIndiceJogadorAtual() + " escolheu a cor " + cor);
    }

    @Override
    public String toString() {
        return "Escolher cor " + cor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EscolherCor) {
            return cor.equals(((EscolherCor)obj).cor);
        } else {
            return false;
        }
    }
}
