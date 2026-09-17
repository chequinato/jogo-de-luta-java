public class Lutador {

    // Atributos
    String nome;
    String pais;
    String estilo;
    String especial;
    int vida;
    int danoSoco;
    int danoChute;
    int danoEspecial;
    boolean defendendo; // true quando o lutador esta de guarda levantada

    // Construtor
    public Lutador(String nome, String pais, String estilo, String especial, int danoSoco, int danoChute, int danoEspecial) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.danoSoco = danoSoco;
        this.danoChute = danoChute;
        this.danoEspecial = danoEspecial;
        this.vida = 100; // todo lutador comeca a luta com 100 de vida
        this.defendendo = false;
    }

    // Metodos

    // Tira vida do inimigo, igual ao atacarOutroPersonagem da aula
    void socar(Lutador inimigo) {
        int dano = danoSoco;

        // se o inimigo levantou a guarda, o golpe tira so a metade
        if (inimigo.defendendo) {
            dano = dano / 2;
        }

        inimigo.vida = inimigo.vida - dano;

        // a vida nao pode ficar negativa
        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("   " + nome + " deu um SOCO e tirou " + dano + " de vida de " + inimigo.nome);
    }

    // Igual ao soco, mas usando o dano do chute, que e maior
    void chutar(Lutador inimigo) {
        int dano = danoChute;

        if (inimigo.defendendo) {
            dano = dano / 2;
        }

        inimigo.vida = inimigo.vida - dano;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("   " + nome + " deu um CHUTE e tirou " + dano + " de vida de " + inimigo.nome);
    }

    // O golpe mais forte. So pode ser usado quando a vida esta em 50 ou menos
    void golpeEspecial(Lutador inimigo) {
        if (vida <= 50) {
            inimigo.vida = inimigo.vida - danoEspecial;

            if (inimigo.vida < 0) {
                inimigo.vida = 0;
            }

            System.out.println("   " + nome + " usou " + especial + " e tirou " + danoEspecial + " de vida de " + inimigo.nome);
        } else {
            System.out.println("   " + nome + " ainda tem vida demais para usar o especial e perdeu a vez");
        }
    }

    // Levanta a guarda. Quem ataca olha este atributo para saber se o dano cai pela metade
    void defender() {
        defendendo = true;
        System.out.println("   " + nome + " levantou a guarda e vai sofrer metade do dano");
    }

    // Mostra os dados do lutador, igual ao pokedex() do exercicio do Pokemon
    void ficha() {
        System.out.println("   Nome: " + nome);
        System.out.println("   Pais: " + pais);
        System.out.println("   Estilo: " + estilo);
        System.out.println("   Especial: " + especial);
        System.out.println("   Soco: " + danoSoco);
        System.out.println("   Chute: " + danoChute);
        System.out.println("   Dano do especial: " + danoEspecial);
        System.out.println("   Vida: " + vida);
        System.out.println();
    }
}
