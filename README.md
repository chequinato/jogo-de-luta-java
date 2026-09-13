# Jogo de Luta em Java — projeto de POO

Jogo de luta estilo Street Fighter que roda no terminal. **Dois jogadores no
mesmo teclado** escolhem um lutador cada e se enfrentam em uma luta de
**melhor de 3 rounds**: cada lutador começa o round com 100 de vida e vence a
luta quem ganhar 2 rounds.

## Como rodar

Jeito mais fácil: dar dois cliques em **`jogar.bat`**. Ele compila tudo e já
abre o jogo.

Pelo terminal, dentro da pasta do projeto:

```bash
javac -d bin src/*.java
java -cp bin App
```

A tela usa só caracteres comuns do teclado (`#`, `.`, `-`, `=`, `[`, `]`),
então fica igual em qualquer terminal: `cmd`, PowerShell, VS Code ou IntelliJ.

## As classes do projeto

Cada classe é uma "coisa" do jogo, do mesmo jeito que `Pokemon` e `Pessoa`
eram nos exercícios da aula.

| Classe | O que ela é |
|---|---|
| `Lutador` | **O personagem.** Tem os atributos (nome, vida, dano dos golpes...), o construtor e os métodos (`socar`, `chutar`, `defender`, `provocar`, `especial`, `mostrarBarra`, `mostrarFicha`). |
| `Jogo` | **O jogo.** Mostra o menu, deixa escolher o personagem e controla os rounds e os turnos da luta. |
| `App` | Classe principal com o `main`. Só cria o objeto `Jogo` e manda ele abrir o menu. |

O `App` ficou igual ao das aulas: cria um objeto e chama um método dele.

```java
public class App {
    public static void main(String[] args) throws Exception {

        Jogo jogo = new Jogo();
        jogo.abrirMenu();

    }
}
```

O `throws Exception` é o mesmo do `App` da aula. Ele está aí por causa do
`Thread.sleep`, que dá as pausas entre um golpe e outro — sem ele o Java
obrigaria a escrever um `try/catch` em volta de cada pausa.

## Os métodos

O projeto tem poucos métodos de propósito: só existe método para o que tem
lógica própria (um laço, uma conta, uma decisão) e é usado em mais de um lugar.
As mensagens de tela são `System.out.println` escritos no meio do código que
faz o trabalho, igual aos `System.out.println("=========== Dados iniciais ===========")`
dos exercícios de aula.

**Na classe `Lutador`:**

| Método | O que faz |
|---|---|
| `socar`, `chutar`, `defender`, `provocar`, `especial` | As cinco ações do lutador. As três que causam dano recebem o adversário como parâmetro, igual ao `atacarOutroPersonagem` da aula. |
| `receberDano` | Faz a conta do dano (defesa, guarda levantada, guarda aberta) e devolve um `int` com quanto de vida foi perdido. Existe porque os três golpes usam a mesma conta. |
| `mostrarBarra` | Desenha a barra de vida com um `for` de 20 voltas. |
| `mostrarFicha` | Mostra todos os dados do lutador, igual ao `pokedex()` do exercício do Pokémon. |

**Na classe `Jogo`:**

| Método | O que faz |
|---|---|
| `abrirMenu` | O `while` do menu principal, com a abertura desenhada e as três opções. |
| `escolherLutador` | Mostra a lista e pede um número de 1 a 6, repetindo enquanto o número for inválido. É usado três vezes (jogador 1, jogador 2 e a tela de ficha). |
| `pegarLutador` | Cria com `new` o objeto do lutador escolhido. |
| `iniciarBatalha` | O `while` que conta os rounds até alguém vencer 2. |
| `disputarRound` | O `while` que conta os turnos até a vida de um dos dois chegar a zero. |
| `executarTurno` | Mostra as opções de golpe e executa a escolhida. |

## Regras da luta

A luta é **melhor de 3 rounds**. Cada round começa com os dois lutadores em
**100 de vida** e acaba quando a vida de um deles chega a zero. Quem vencer 2
rounds ganha a luta. Quem tem mais **velocidade** ataca primeiro.

No seu turno você escolhe uma ação:

| Ação | Efeito |
|---|---|
| **Soco** | Dano menor, mas a guarda continua fechada. |
| **Chute** | Dano maior, só que para girar o corpo o lutador **abre a guarda**. |
| **Defender** | O próximo golpe recebido causa metade do dano. |
| **Provocar** | Não causa dano nenhum e ainda abre a guarda. Serve para zoar. |
| **Especial** | O golpe mais forte, e ele **ignora a defesa** do adversário. |

Outros detalhes:

- **Nada é sorteado.** O dano de cada golpe já vem definido no construtor do
  lutador, então o mesmo golpe sempre tira o mesmo tanto de vida.
- Quem está com a **guarda aberta** leva 50% a mais no golpe seguinte.
- A **defesa** é descontada do dano dos golpes normais, mas não do especial.
- Todo golpe tira pelo menos **1** de vida, mesmo contra uma defesa alta.
- A guarda levantada e a guarda aberta valem só até o lutador agir de novo.
- No começo de cada round os dois voltam com a vida cheia, mas os rounds já
  vencidos continuam contados.

## Os lutadores

| # | Nome | País | Estilo | Soco | Chute | Especial | Defesa | Velocidade | Golpe |
|---|---|---|---|---|---|---|---|---|---|
| 1 | Ryu | Japão | Karate | 14 | 22 | 45 | 5 | 6 | Hadouken |
| 2 | Ken | EUA | Karate | 16 | 24 | 48 | 4 | 7 | Shoryuken |
| 3 | Chun-Li | China | Kung Fu | 13 | 20 | 42 | 4 | 9 | Spinning Bird Kick |
| 4 | Blanka | Brasil | Selvagem | 17 | 26 | 50 | 3 | 6 | Electric Thunder |
| 5 | Zangief | Rússia | Luta Livre | 19 | 30 | 55 | 7 | 2 | Spinning Piledriver |
| 6 | Dhalsim | Índia | Yoga | 14 | 23 | 46 | 3 | 8 | Yoga Flame |

## Como é feito o visual do terminal

Não tem biblioteca nenhuma, é tudo `System.out.println`:

- O nome **STREET FIGHTER** é desenhado com as próprias letras, uma linha de
  `println` por vez, dentro do `abrirMenu`.
- As **molduras** são `System.out.println` com `=` e `-`, escritos no lugar
  onde a mensagem aparece.
- A **barra de vida** é montada com um `for` que roda 20 vezes no método
  `mostrarBarra`. Cada volta acrescenta um `#` se aquele pedaço ainda tem vida,
  ou um `.` se não tem. Como a vida vai de 0 a 100, basta dividir por 5 para
  saber quantos `#` desenhar. O nome fica numa linha e a barra na linha de
  baixo, e é por isso que as barras dos dois lutadores sempre começam na mesma
  coluna, sem precisar acertar espaço nenhum.
- O `Thread.sleep` segura a tela por alguns instantes entre um golpe e outro,
  para dar tempo de ler o que aconteceu.
- Para limpar a tela é só um `for` que imprime 30 linhas em branco, empurrando
  a tela anterior para cima.

## Conceitos de POO usados (para explicar na apresentação)

- **Classe e objeto**: `Lutador` é a classe (a "forma"); `new Lutador("Ryu", ...)`
  cria um objeto, um lutador de verdade com os dados dele.
- **Atributos**: as características de cada objeto (`nome`, `vida`, `danoSoco`...),
  todos de tipo simples (`String`, `int`, `boolean`).
- **Construtor**: `public Lutador(...)` define como um lutador nasce. Todo
  lutador já começa com 100 de vida, isso está escrito no construtor. É lá
  também que entra o dano dos golpes de cada personagem.
- **Métodos**: as ações do objeto (`socar`, `chutar`, `defender`, `mostrarFicha`).
- **Um objeto agindo sobre outro**: `ryu.socar(ken)` — o método recebe outro
  `Lutador` como parâmetro e mexe na vida dele, igualzinho ao exercício do
  `atacarOutroPersonagem`.
- **Mexer no atributo de outro objeto**: no começo do turno o jogo faz
  `atacante.defendendo = false;`, do mesmo jeito que a aula fazia
  `inimigo.vida = inimigo.vida - 10;`.
- **`this`**: usado dentro da classe para deixar claro que estamos falando do
  atributo daquele objeto, e não de uma variável qualquer.
- **Objeto criado na hora**: o `pegarLutador` usa `new Lutador(...)` toda vez
  que alguém escolhe. É por isso que os dois jogadores podem pegar o mesmo
  personagem sem dividirem a mesma barra de vida — são dois objetos diferentes.
- **Um `while` dentro do outro**: o `iniciarBatalha` tem um `while` que conta
  os rounds (`while (lutador1.roundsVencidos < 2 && ...)`) e, dentro do
  `disputarRound`, outro `while` que conta os turnos daquele round. É o mesmo
  `while` da aula, só que um dentro do outro.
- **Método que devolve valor**: quase todos os métodos são `void`, mas
  `receberDano` devolve um `int` (quanto de vida foi perdido) e `pegarLutador`
  devolve um `Lutador`. Serve para mostrar a diferença entre os dois tipos.

## Ideias para aumentar o projeto depois

- Mais lutadores (acrescentar um `else if` no `pegarLutador` e uma linha na
  lista do `escolherLutador`).
- Limitar o golpe especial a um uso por round, com um atributo `int` contando.
- Um segundo golpe especial por personagem.
- Um cenário que mude alguma regra da luta.
