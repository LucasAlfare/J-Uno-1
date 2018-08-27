package stm.juno.simulador.acoes;

import stm.juno.diversos.Mensageiro;
import stm.juno.simulador.Uno;
import stm.juno.simulador.cartas.Carta;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

public class ComprarCarta extends Acao {

    private int numCartas;

    public ComprarCarta(int numCartas) {
        this.numCartas = numCartas;
    }

    @Override
    public void executar(Uno jogo, boolean verboso) {
        Carta[] cartas = jogo.getPilhaCompra().comprar(numCartas, jogo);
        if (cartas.length > 0) {
            jogo.getJogadores().getJogadorAtual().getCartas().addAll(Arrays.asList(cartas));
            jogo.getPilhaDescarte().setAcaoPendente(false);
            if (verboso)
                Mensageiro.imprimir(numCartas + " cartas foram compradas");
        } else {
            try {
                throw new OperationNotSupportedException("Não há cartas no monte o suficiente para compra");
            } catch (OperationNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComprarCarta) {
            return numCartas == ((ComprarCarta) obj).numCartas;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Comprar " + numCartas + " cartas";
    }
}
