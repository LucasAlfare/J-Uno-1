package stm.juno.simulador.acoes;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;

public class InverterSentido extends Acao {
    @Override
    public void executar(Uno jogo, boolean verboso) {
        jogo.getJogadores().inverterSentido();
        jogo.getPilhaDescarte().setAcaoPendente(false);
        if (verboso)
            Mensageiro.imprimir("Sentido do jogo foi invertido para " + (jogo.getJogadores().sentidoHorario()
                    ? "horário" : "anti-horário"));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof InverterSentido;
    }

    @Override
    public String toString() {
        return "Inverter sentido";
    }
}
