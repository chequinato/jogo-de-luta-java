# Jogo de Luta em Java — projeto de POO

Jogo de luta estilo Street Fighter que roda no terminal. **Dois jogadores no
mesmo teclado** escolhem um lutador cada e se enfrentam em uma luta de
**melhor de 3 rounds**: cada lutador começa o round com 100 de vida e vence a
luta quem ganhar 2 rounds.

O projeto usa só o que foi visto em aula: **atributos, construtor, métodos,
`this`, `if`, `while`, `for` e um objeto recebendo outro objeto como
parâmetro**. Não tem nada além disso — sem biblioteca, sem sorteio, sem
tratamento de erro e sem truque de terminal.

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
| `Lutador` | **O personagem.** Tem os atributos (nome, vida, dano dos golpes...), o construtor e os métodos (`socar`, `chutar`, `golpeEspecial`, `defender`, `mostrarBarra`, `mostrarFicha`). |
| `Jogo` | **O jogo.** Mostra o menu, deixa escolher o personagem e controla os rounds e as vezes de cada jogador. |
| `App` | Classe principal com o `main`. Só cria o objeto `Jogo` e manda ele abrir o menu. |

O `App` ficou igual ao das aulas: cria um objeto e chama um método dele.

```java
public class App {
    public static void main(String[] args) {

        Jogo jogo = new Jogo();
        jogo.abrirMenu();

    }
}
```

## Os métodos

O projeto tem poucos métodos de propósito: só existe método para o que tem
lógica própria (um laço, uma conta, uma decisão) e é usado em mais de um lugar.
As mensagens de tela são `System.out.println` escritos no meio do código que
faz o trabalho, igual aos `System.out.println("=========== Dados iniciais ===========")`
dos exercícios de aula.

**Na classe `Lutador`:**

| Método | O que faz |
|---|---|
| `socar`, `chutar`, `golpeEspecial` | Os três golpes. Cada um recebe o adversário como parâmetro e tira a vida dele com `inimigo.vida = inimigo.vida - dano`, igual ao `atacarOutroPersonagem` da aula. |
| `defender` | Levanta a guarda: deixa o atributo `defendendo` como `true`. Quem atacar olha esse atributo e tira só metade do dano. |
| `mostrarBarra` | Desenha a barra de vida com um `for` de 20 voltas. |
| `mostrarFicha` | Mostra todos os dados do lutador, igual ao `pokedex()` do exercício do Pokémon. |

**Na classe `Jogo`:**

| Método | O que faz |
|---|---|
| `abrirMenu` | O `while` do menu principal, com a abertura desenhada e as três opções. |
| `escolherLutador` | Mostra a lista, pede um número de 1 a 6 repetindo enquanto for inválido, e devolve o `Lutador` criado com `new`. É usado três vezes (jogador 1, jogador 2 e a tela de ficha). |
| `iniciarBatalha` | A luta inteira: apresenta os dois lutadores, e um `while` chama um round atrás do outro até alguém vencer 2. No fim mostra quem ganhou. |
| `jogarRound` | Um round só: devolve os dois para 100 de vida e roda o `while` dos turnos até a vida de um deles chegar a zero. No fim conta o round para quem sobrou de pé. |
| `jogarVez` | A vez de um lutador: mostra as quatro ações, pede uma (repetindo enquanto for inválida) e executa a escolhida. |

## Regras da luta

A luta é **melhor de 3 rounds**. Cada round começa com os dois lutadores em
**100 de vida** e acaba quando a vida de um deles chega a zero. Quem vencer 2
rounds ganha a luta. O jogador 1 sempre joga primeiro no turno.

No seu turno você escolhe uma ação:

| Ação | Efeito |
|---|---|
| **Soco** | Dano menor. |
| **Chute** | Dano maior que o soco. |
| **Defender** | Não causa dano: o lutador levanta a guarda e o próximo golpe que ele receber tira metade. |
| **Especial** | O golpe mais forte, e ele **passa por cima da guarda**. |

Outros detalhes:

- **Nada é sorteado.** O dano de cada golpe já vem definido no construtor do
  lutador, então o mesmo golpe sempre tira o mesmo tanto de vida.
- A guarda levantada vale só até aquele lutador jogar de novo: a primeira linha
  do `jogarVez` é `atacante.defendendo = false`.
- Se você digitar um número que não existe, o jogo pede de novo em vez de
  passar a vez.
- A vida nunca fica negativa: quando passa de zero, ela é acertada para zero.
- No começo de cada round os dois voltam com a vida cheia e a guarda baixada,
  mas os rounds já vencidos continuam contados.

## Os lutadores

| # | Nome | País | Estilo | Soco | Chute | Especial | Golpe |
|---|---|---|---|---|---|---|---|
| 1 | Ryu | Japão | Karate | 14 | 22 | 45 | Hadouken |
| 2 | Ken | EUA | Karate | 16 | 24 | 48 | Shoryuken |
| 3 | Chun-Li | China | Kung Fu | 13 | 20 | 42 | Spinning Bird Kick |
| 4 | Blanka | Brasil | Selvagem | 17 | 26 | 50 | Electric Thunder |
| 5 | Zangief | Rússia | Luta Livre | 19 | 30 | 55 | Spinning Piledriver |
| 6 | Dhalsim | Índia | Yoga | 14 | 23 | 46 | Yoga Flame |

## Como é feito o visual do terminal

Não tem biblioteca nenhuma, é tudo `System.out.println`:

- O nome **STREET FIGHTER** é desenhado com as próprias letras, uma linha de
  `println` por vez, no começo do `abrirMenu` — antes do `while`, para aparecer
  uma vez só quando o jogo abre.
- As **molduras** são `System.out.println` com `=` e `-`, escritos no lugar
  onde a mensagem aparece.
- A **barra de vida** é desenhada com um `for` que roda 20 vezes no método
  `mostrarBarra`. Cada volta escreve um `#` se aquele pedaço ainda tem vida,
  ou um `.` se não tem. Como a vida vai de 0 a 100, basta dividir por 5 para
  saber quantos `#` desenhar. O nome fica numa linha e a barra na linha de
  baixo, e é por isso que as barras dos dois lutadores sempre começam na mesma
  coluna, sem precisar acertar espaço nenhum.

## Conceitos de POO usados (para explicar na apresentação)

- **Classe e objeto**: `Lutador` é a classe (a "forma"); `new Lutador("Ryu", ...)`
  cria um objeto, um lutador de verdade com os dados dele.
- **Atributos**: as características de cada objeto (`nome`, `vida`, `danoSoco`...),
  todos de tipo simples (`String`, `int`, `boolean`).
- **Construtor**: `public Lutador(...)` define como um lutador nasce. Todo
  lutador já começa com 100 de vida e com zero round vencido, isso está escrito
  no construtor. É lá também que entra o dano dos golpes de cada personagem.
- **Métodos**: as ações do objeto (`socar`, `chutar`, `defender`, `mostrarFicha`).
- **Um objeto agindo sobre outro**: `ryu.socar(ken)` — o método recebe outro
  `Lutador` como parâmetro e mexe na vida dele, igualzinho ao exercício do
  `atacarOutroPersonagem`.
- **Mexer no atributo de outro objeto**: dentro do `socar` o jogo faz
  `inimigo.vida = inimigo.vida - dano;`, do mesmo jeito que a aula fazia
  `inimigo.vida = inimigo.vida - 10;`.
- **`this`**: usado dentro da classe para deixar claro que estamos falando do
  atributo daquele objeto, e não de uma variável qualquer.
- **Objeto criado na hora**: o `escolherLutador` usa `new Lutador(...)` toda vez
  que alguém escolhe. É por isso que os dois jogadores podem pegar o mesmo
  personagem sem dividirem a mesma barra de vida — são dois objetos diferentes.
- **Um método chamando outro**: o `iniciarBatalha` tem um `while` que conta os
  rounds (`while (lutador1.roundsVencidos < 2 && ...)`) e, a cada volta, chama
  o `jogarRound`. Dentro do `jogarRound` tem outro `while`, que conta os turnos,
  e ele chama o `jogarVez` uma vez para cada jogador. Assim cada `while` fica
  num método só e dá para ler um de cada vez, em vez de três laços empilhados
  no mesmo lugar.
- **Método que devolve valor**: quase todos os métodos são `void`, mas o
  `escolherLutador` devolve um `Lutador`. Serve para mostrar a diferença entre
  um método que só faz uma coisa e um que entrega um valor de volta.

## Ideias para aumentar o projeto depois

- Mais lutadores (acrescentar um `else if` no `escolherLutador` e uma linha na
  lista).
- Limitar o golpe especial a um uso por round, com um atributo `int` contando
  quantas vezes já foi usado.
- Um atributo `velocidade` para decidir quem ataca primeiro no turno.
- Um atributo `defesa` descontado do dano dos golpes normais.
