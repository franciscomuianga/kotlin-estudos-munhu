println("═══════════════════════════════════════")
println("    MUNHU - LAMBDA & SINGLE-EXPRESSION")
println("═══════════════════════════════════════")

// ========== SINGLE-EXPRESSION FUNCTIONS (uma linha) ==========

// Forma normal
fun somarNormal(a: Int, b: Int): Int {
    return a + b
}

// Forma compacta (sem chaves, sem return)
fun somarCompacta(a: Int, b: Int): Int = a + b

// Pode omitir tipo de retorno (inferido)
fun somarMinima(a: Int, b: Int) = a + b

println("\n=== SINGLE-EXPRESSION FUNCTIONS ===")
println("Normal: ${somarNormal(10, 5)}")
println("Compacta: ${somarCompacta(10, 5)}")
println("Mínima: ${somarMinima(10, 5)}")

// ========== FUNÇÕES ÚTEIS COMPACTAS ==========

fun dobrar(numero: Int) = numero * 2
fun isAdulto(idade: Int) = idade >= 18
fun primeiraLetra(texto: String) = texto.firstOrNull() ?: ' '
fun tamanhoValido(texto: String) = texto.length in 3..50

println("\n=== FUNÇÕES COMPACTAS ÚTEIS ===")
println("Dobro de 15: ${dobrar(15)}")
println("17 é adulto? ${isAdulto(17)}")
println("Primeira letra de 'Munhu': ${primeiraLetra("Munhu")}")
println("'Francisco' tem tamanho válido? ${tamanhoValido("Francisco")}")

// ========== LAMBDA EXPRESSIONS ==========

println("\n=== LAMBDA EXPRESSIONS ===")

// Lambda armazenada em variável
val saudacao = { nome: String -> "Olá, $nome!" }
println(saudacao("Francisco"))

// Lambda com múltiplos parâmetros
val multiplicar = { a: Int, b: Int -> a * b }
println("5 * 3 = ${multiplicar(5, 3)}")

// Lambda sem parâmetros
val mensagemPadrao = { "Bem-vindo ao Munhu! 🇲🇿" }
println(mensagemPadrao())

// Lambda com corpo maior
val validarSenha = { senha: String ->
    val temTamanho = senha.length >= 8
    val temNumero = senha.any { it.isDigit() }
    val temLetra = senha.any { it.isLetter() }
    
    temTamanho && temNumero && temLetra
}

println("\n=== VALIDADOR DE SENHA (LAMBDA) ===")
println("'abc123': ${validarSenha("abc123")}")  // false (curta)
println("'senha12345': ${validarSenha("senha12345")}")  // true

// ========== HIGHER-ORDER FUNCTIONS ==========

println("\n=== HIGHER-ORDER FUNCTIONS ===")

// Função que recebe outra função como parâmetro
fun executarOperacao(a: Int, b: Int, operacao: (Int, Int) -> Int): Int {
    return operacao(a, b)
}

// Passando lambdas diferentes
val resultadoSoma = executarOperacao(10, 5) { x, y -> x + y }
val resultadoMult = executarOperacao(10, 5) { x, y -> x * y }
val resultadoMax = executarOperacao(10, 5) { x, y -> if (x > y) x else y }

println("10 + 5 = $resultadoSoma")
println("10 * 5 = $resultadoMult")
println("max(10, 5) = $resultadoMax")

// ========== FUNÇÕES COM LAMBDAS (APLICAÇÕES REAIS) ==========

println("\n=== FILTRAR POSTS ===")

val posts = listOf(
    "Post normal",
    "SPAM! Clique aqui!",
    "Conteúdo interessante",
    "Outro SPAM",
    "Discussão de qualidade"
)

// filter é uma higher-order function
val postsLimpos = posts.filter { post ->
    !post.uppercase().contains("SPAM")
}

println("Posts originais: ${posts.size}")
println("Posts limpos: ${postsLimpos.size}")
postsLimpos.forEach { println("  ✅ $it") }

// ========== MAP (TRANSFORMAR LISTA) ==========

println("\n=== TRANSFORMAR USERNAMES ===")

val nomes = listOf("Francisco", "Ana Silva", "Carlos")

// Transforma cada elemento
val usernames = nomes.map { nome ->
    nome.lowercase().replace(" ", "_")
}

nomes.forEachIndexed { index, nome ->
    println("$nome → @${usernames[index]}")
}

// ========== FOREACH COM LAMBDA ==========

println("\n=== NOTIFICAR USUÁRIOS ===")

val usuarios = listOf("francisco", "ana", "carlos")

usuarios.forEach { usuario ->
    println("📧 Enviando email para @$usuario")
}

// ========== SORTEDBY (ORDENAR) ==========

println("\n=== RANKING POR SEGUIDORES ===")

data class Usuario(val nome: String, val seguidores: Int)

val todosUsuarios = listOf(
    Usuario("francisco", 1200),
    Usuario("ana", 5000),
    Usuario("carlos", 800),
    Usuario("beatriz", 3500)
)

// Ordena por seguidores (decrescente)
val ranking = todosUsuarios.sortedByDescending { it.seguidores }

ranking.forEachIndexed { index, usuario ->
    val emoji = when (index) {
        0 -> "🥇"
        1 -> "🥈"
        2 -> "🥉"
        else -> "  "
    }
    println("$emoji #${index + 1} - ${usuario.nome}: ${usuario.seguidores} seguidores")
}

// ========== IT (PARÂMETRO IMPLÍCITO) ==========

println("\n=== USANDO 'IT' (SHORTHAND) ===")

val numeros = listOf(1, 2, 3, 4, 5)

// Com parâmetro nomeado
val dobrados1 = numeros.map { numero -> numero * 2 }

// Com 'it' (quando há só 1 parâmetro)
val dobrados2 = numeros.map { it * 2 }

println("Original: $numeros")
println("Dobrados: $dobrados2")

// ========== FUNÇÃO QUE RETORNA LAMBDA ==========

fun criarMultiplicador(fator: Int): (Int) -> Int {
    return { numero -> numero * fator }
}

println("\n=== FÁBRICA DE FUNÇÕES ===")

val multiplicarPor10 = criarMultiplicador(10)
val multiplicarPor100 = criarMultiplicador(100)

println("5 * 10 = ${multiplicarPor10(5)}")
println("5 * 100 = ${multiplicarPor100(5)}")
