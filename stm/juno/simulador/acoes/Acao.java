package stm.juno.simulador.acoes;

import stm.juno.simulador.Uno;

public abstract class Acao {

    public abstract void executar(Uno jogo, boolean verboso);
}
