println("═══════════════════════════════════════")
println("    MUNHU - FUNÇÕES BÁSICAS")
println("═══════════════════════════════════════")

// ========== FUNÇÃO SEM PARÂMETROS E SEM RETORNO ==========

fun saudacao() {
    println("Bem-vindo ao Munhu! 🇲🇿")
}

println("\n=== FUNÇÃO SIMPLES ===")
saudacao()
saudacao()  // Pode chamar quantas vezes quiser!

// ========== FUNÇÃO COM PARÂMETROS ==========

fun saudacaoPersonalizada(nome: String) {
    println("Olá, $nome! Bem-vindo ao Munhu! 👋")
}

println("\n=== FUNÇÃO COM PARÂMETROS ===")
saudacaoPersonalizada("Francisco")
saudacaoPersonalizada("Ana")
saudacaoPersonalizada("Carlos")

// ========== FUNÇÃO COM MÚLTIPLOS PARÂMETROS ==========

fun criarPost(autor: String, conteudo: String, likes: Int) {
    println("\n┌─────────────────────────")
    println("│ @$autor")
    println("│ $conteudo")
    println("│ ❤️ $likes likes")
    println("└─────────────────────────")
}

println("\n=== FUNÇÃO COM MÚLTIPLOS PARÂMETROS ===")
criarPost("francisco", "Desenvolvendo o Munhu! 🚀", 150)
criarPost("ana", "Moçambique é lindo! 🇲🇿", 230)

// ========== FUNÇÃO QUE RETORNA VALOR ==========

fun somar(a: Int, b: Int): Int {
    return a + b
}

println("\n=== FUNÇÃO QUE RETORNA VALOR ===")
val resultado = somar(10, 5)
println("10 + 5 = $resultado")

val total = somar(100, 250)
println("100 + 250 = $total")

// ========== FUNÇÃO COM CÁLCULO DE ENGAJAMENTO ==========

fun calcularEngajamento(likes: Int, comentarios: Int, compartilhamentos: Int): Double {
    val total = likes + comentarios + compartilhamentos
    return total.toDouble()
}

println("\n=== CALCULADORA DE ENGAJAMENTO ===")
val engajamento1 = calcularEngajamento(150, 30, 20)
println("Post 1: $engajamento1 interações")

val engajamento2 = calcularEngajamento(500, 80, 45)
println("Post 2: $engajamento2 interações")

// ========== FUNÇÃO QUE RETORNA BOOLEAN ==========

fun isInfluencer(seguidores: Int): Boolean {
    return seguidores >= 10000
}

println("\n=== VERIFICADOR DE INFLUENCER ===")
val usuario1 = 5000
val usuario2 = 15000

println("$usuario1 seguidores: ${if (isInfluencer(usuario1)) "✅ Influencer" else "❌ Não é influencer"}")
println("$usuario2 seguidores: ${if (isInfluencer(usuario2)) "✅ Influencer" else "❌ Não é influencer"}")

// ========== FUNÇÃO QUE RETORNA STRING ==========

fun gerarUsername(nome: String, numero: Int): String {
    return "${nome.lowercase()}_$numero"
}

println("\n=== GERADOR DE USERNAME ===")
val username1 = gerarUsername("Francisco", 2025)
val username2 = gerarUsername("Ana Silva", 123)

println("Username 1: @$username1")
println("Username 2: @$username2")

// ========== FUNÇÃO COM LÓGICA COMPLEXA ==========

fun classificarPost(likes: Int): String {
    return when {
        likes >= 1000 -> "🔥 VIRAL"
        likes >= 500 -> "⭐ POPULAR"
        likes >= 100 -> "✅ BOM"
        else -> "📊 NORMAL"
    }
}

println("\n=== CLASSIFICADOR DE POSTS ===")
val posts = listOf(50, 150, 600, 1500)

for (likes in posts) {
    val classificacao = classificarPost(likes)
    println("$likes likes → $classificacao")
}

// ========== FUNÇÃO CHAMANDO OUTRA FUNÇÃO ==========

fun validarEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".")
}

fun cadastrarUsuario(nome: String, email: String): String {
    if (!validarEmail(email)) {
        return "❌ Email inválido!"
    }
    
    return "✅ Usuário $nome cadastrado com sucesso!"
}

println("\n=== SISTEMA DE CADASTRO ===")
println(cadastrarUsuario("Francisco", "francisco@munhu.co.mz"))
println(cadastrarUsuario("Ana", "email-invalido"))
