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
    boolean defendendo;

    // Construtor
    public Lutador(String nome, String pais, String estilo, String especial, int danoSoco, int danoChute, int danoEspecial) {

        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.vida = 100;
        this.danoSoco = danoSoco;
        this.danoChute = danoChute;
        this.danoEspecial = danoEspecial;
        this.defendendo = false;
    }

    // Métodos

    void socar(Lutador inimigo) {

        if (inimigo.defendendo) {
            inimigo.vida = inimigo.vida - danoSoco/2;
        } else {
            inimigo.vida = inimigo.vida - danoSoco;
        }

        System.out.println(nome + " deu um SOCO em " + inimigo.nome);
    }

    void chutar(Lutador inimigo) {

        if (inimigo.defendendo) {
            inimigo.vida = inimigo.vida - danoChute/2;
        } else {
            inimigo.vida = inimigo.vida - danoChute;
        }

        System.out.println(nome + " deu um CHUTE em " + inimigo.nome);
    }

    void golpeEspecial(Lutador inimigo) {

        if (inimigo.defendendo) {
            inimigo.vida = inimigo.vida - danoEspecial/2;
        } else {
            inimigo.vida = inimigo.vida - danoEspecial;
        }

        System.out.println(nome + " usou " + especial + "!");
    }

    void defender() {

        defendendo = true;

        System.out.println(nome + " está defendendo!");
    }

    void info() {

        System.out.println("------------------------------");
        System.out.println("Nome: " + nome);
        System.out.println("País: " + pais);
        System.out.println("Estilo: " + estilo);
        System.out.println("Especial: " + especial);
        System.out.println("Vida: " + vida);
        System.out.println("Dano do soco: " + danoSoco);
        System.out.println("Dano do chute: " + danoChute);
        System.out.println("Dano especial: " + danoEspecial);
        System.out.println("------------------------------");
    }
}