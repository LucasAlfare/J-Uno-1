package stm.juno.simulador.acoes;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;

public class PularJogador extends Acao {
    @Override
    public void executar(Uno jogo, boolean verboso) {
        jogo.getPilhaDescarte().setAcaoPendente(false);
        if (verboso)
            Mensageiro.imprimir("Jogador " + jogo.getJogadores().getIndiceJogadorAtual() + " foi pulado");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PularJogador;
    }

    @Override
    public String toString() {
        return "Pular jogador";
    }
}
