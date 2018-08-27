package stm.juno.simulador.cartas;

public enum Carta {

    VERMELHA_0(TipoCarta.NUMERO, CorCarta.VERMELHA, 0),
    VERMELHA_1(TipoCarta.NUMERO, CorCarta.VERMELHA, 1),
    VERMELHA_2(TipoCarta.NUMERO, CorCarta.VERMELHA, 2),
    VERMELHA_3(TipoCarta.NUMERO, CorCarta.VERMELHA, 3),
    VERMELHA_4(TipoCarta.NUMERO, CorCarta.VERMELHA, 4),
    VERMELHA_5(TipoCarta.NUMERO, CorCarta.VERMELHA, 5),
    VERMELHA_6(TipoCarta.NUMERO, CorCarta.VERMELHA, 6),
    VERMELHA_7(TipoCarta.NUMERO, CorCarta.VERMELHA, 7),
    VERMELHA_8(TipoCarta.NUMERO, CorCarta.VERMELHA, 8),
    VERMELHA_9(TipoCarta.NUMERO, CorCarta.VERMELHA, 9),

    VERDE_0(TipoCarta.NUMERO, CorCarta.VERDE, 0),
    VERDE_1(TipoCarta.NUMERO, CorCarta.VERDE, 1),
    VERDE_2(TipoCarta.NUMERO, CorCarta.VERDE, 2),
    VERDE_3(TipoCarta.NUMERO, CorCarta.VERDE, 3),
    VERDE_4(TipoCarta.NUMERO, CorCarta.VERDE, 4),
    VERDE_5(TipoCarta.NUMERO, CorCarta.VERDE, 5),
    VERDE_6(TipoCarta.NUMERO, CorCarta.VERDE, 6),
    VERDE_7(TipoCarta.NUMERO, CorCarta.VERDE, 7),
    VERDE_8(TipoCarta.NUMERO, CorCarta.VERDE, 8),
    VERDE_9(TipoCarta.NUMERO, CorCarta.VERDE, 9),

    AZUL_0(TipoCarta.NUMERO, CorCarta.AZUL, 0),
    AZUL_1(TipoCarta.NUMERO, CorCarta.AZUL, 1),
    AZUL_2(TipoCarta.NUMERO, CorCarta.AZUL, 2),
    AZUL_3(TipoCarta.NUMERO, CorCarta.AZUL, 3),
    AZUL_4(TipoCarta.NUMERO, CorCarta.AZUL, 4),
    AZUL_5(TipoCarta.NUMERO, CorCarta.AZUL, 5),
    AZUL_6(TipoCarta.NUMERO, CorCarta.AZUL, 6),
    AZUL_7(TipoCarta.NUMERO, CorCarta.AZUL, 7),
    AZUL_8(TipoCarta.NUMERO, CorCarta.AZUL, 8),
    AZUL_9(TipoCarta.NUMERO, CorCarta.AZUL, 9),

    AMARELA_0(TipoCarta.NUMERO, CorCarta.AMARELA, 0),
    AMARELA_1(TipoCarta.NUMERO, CorCarta.AMARELA, 1),
    AMARELA_2(TipoCarta.NUMERO, CorCarta.AMARELA, 2),
    AMARELA_3(TipoCarta.NUMERO, CorCarta.AMARELA, 3),
    AMARELA_4(TipoCarta.NUMERO, CorCarta.AMARELA, 4),
    AMARELA_5(TipoCarta.NUMERO, CorCarta.AMARELA, 5),
    AMARELA_6(TipoCarta.NUMERO, CorCarta.AMARELA, 6),
    AMARELA_7(TipoCarta.NUMERO, CorCarta.AMARELA, 7),
    AMARELA_8(TipoCarta.NUMERO, CorCarta.AMARELA, 8),
    AMARELA_9(TipoCarta.NUMERO, CorCarta.AMARELA, 9),

    INVERTER_VERMELHA(TipoCarta.INVERTER, CorCarta.VERMELHA, 20),
    INVERTER_VERDE(TipoCarta.INVERTER, CorCarta.VERDE, 20),
    INVERTER_AZUL(TipoCarta.INVERTER, CorCarta.AZUL, 20),
    INVERTER_AMARELA(TipoCarta.INVERTER, CorCarta.AMARELA, 20),

    PULAR_VERMELHA(TipoCarta.PULAR, CorCarta.VERMELHA, 20),
    PULAR_VERDE(TipoCarta.PULAR, CorCarta.VERDE, 20),
    PULAR_AZUL(TipoCarta.PULAR, CorCarta.AZUL, 20),
    PULAR_AMARELA(TipoCarta.PULAR, CorCarta.AMARELA, 20),

    COMRPAR_DUAS_VERMELHA(TipoCarta.COMPRAR_DUAS, CorCarta.VERMELHA, 20),
    COMRPAR_DUAS_VERDE(TipoCarta.COMPRAR_DUAS, CorCarta.VERDE, 20),
    COMRPAR_DUAS_AZUL(TipoCarta.COMPRAR_DUAS, CorCarta.AZUL, 20),
    COMRPAR_DUAS_AMARELA(TipoCarta.COMPRAR_DUAS, CorCarta.AMARELA, 20),

    CURINGA(TipoCarta.CURINGA, null, 50),
    CURINGA_COMPRAR_QUATRO(TipoCarta.CURINGA_COMPRAR_QUATRO, null, 50);

    private TipoCarta tipo;
    private CorCarta cor;
    private int valor;

    Carta(TipoCarta tipo, CorCarta cor, int valor) {
        this.tipo = tipo;
        this.cor = cor;
        this.valor = valor;
    }

    public TipoCarta getTipo() {
        return tipo;
    }

    public CorCarta getCor() {
        return cor;
    }

    public int getValor() {
        return valor;
    }

    public static Carta[] getBaralho() {
        Carta[] baralho = new Carta[108];
        int indiceAtual = 0;
        for (Carta c : new Carta[]{VERMELHA_0, AZUL_0, AMARELA_0, VERDE_0, CURINGA, CURINGA, CURINGA_COMPRAR_QUATRO,
                CURINGA_COMPRAR_QUATRO}) {
            baralho[indiceAtual] = c;
            indiceAtual++;
        }

        for (int i = 0; i < 2; i++) {
            for (Carta c : values()) {
                if (c.getValor() > 0) {
                    baralho[indiceAtual] = c;
                    indiceAtual++;
                }
            }
        }

        return baralho;
    }
}
