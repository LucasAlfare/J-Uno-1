package stm.juno.simulador.pilhas;

import stm.juno.simulador.Uno;
import stm.juno.simulador.cartas.Carta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PilhaCompra extends ArrayList<Carta> {

    public PilhaCompra(Carta[] baralho) {
        addAll(Arrays.asList(baralho));
        Collections.shuffle(this);
    }

    public PilhaCompra(PilhaCompra cobaia) {
        addAll(cobaia);
    }

    public Carta[] comprar(int numCartas, Uno jogo) {
        Carta[] cartas = new Carta[numCartas];

        if (size() < numCartas && jogo.getPilhaDescarte().size() > 1) {
            Carta topo = proximaNaPilha();

            if (topo != null)
                remove(size() - 1);

            int totalPilhaDescarte = jogo.getPilhaDescarte().size();
            for (int i = 0; i < totalPilhaDescarte- 1; i++)
                add(jogo.getPilhaDescarte().remove(0));

            Collections.shuffle(this);

            if (topo != null)
                add(topo);

            //Mensageiro.imprimir("Pilha de compra foi reabastecida com a pilha de descarte");
        }

        for (int i = 0; i < numCartas; i++)
            cartas[i] = remove(size() - 1);

        return cartas;
    }

    public Carta proximaNaPilha() {
        return !isEmpty() ? get(size() - 1) : null;
    }
}
