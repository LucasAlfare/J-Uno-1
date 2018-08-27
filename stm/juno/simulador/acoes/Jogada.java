package stm.juno.simulador.acoes;

import stm.juno.simulador.Uno;

import java.util.Arrays;

public class Jogada {

    private Acao[] acoes;

    public Jogada(Acao... acoes) {
        this.acoes = acoes;
    }

    public void executar(Uno jogo, boolean verboso) {
        for (Acao acao : acoes)
            acao.executar(jogo, verboso);
    }

    @Override
    public String toString() {
        return Arrays.toString(acoes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Jogada) {
            Jogada jogada = (Jogada) obj;
            if (jogada.acoes.length == acoes.length) {
                for (int i = 0; i < jogada.acoes.length; i++) {
                    if (!jogada.acoes[i].equals(acoes[i])) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }
}
