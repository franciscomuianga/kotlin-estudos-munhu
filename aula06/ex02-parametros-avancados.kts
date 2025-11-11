println("═══════════════════════════════════════")
println("    MUNHU - PARÂMETROS AVANÇADOS")
println("═══════════════════════════════════════")

// ========== PARÂMETROS COM VALOR PADRÃO ==========

fun criarNotificacao(
    mensagem: String,
    tipo: String = "info",  // Valor padrão
    vibrar: Boolean = false
) {
    val emoji = when (tipo) {
        "info" -> "ℹ️"
        "sucesso" -> "✅"
        "aviso" -> "⚠️"
        "erro" -> "❌"
        else -> "📢"
    }
    
    println("$emoji $mensagem ${if (vibrar) "📳" else ""}")
}

println("\n=== PARÂMETROS COM VALOR PADRÃO ===")
criarNotificacao("Novo seguidor!")  // Usa padrões
criarNotificacao("Post publicado!", "sucesso")
criarNotificacao("Conexão perdida!", "erro", true)

// ========== PARÂMETROS NOMEADOS ==========

fun criarPerfil(
    username: String,
    nome: String,
    bio: String = "",
    verificado: Boolean = false,
    privado: Boolean = false
) {
    println("\n┌─── PERFIL ───")
    println("│ @$username ${if (verificado) "✓" else ""}")
    println("│ $nome")
    if (bio.isNotEmpty()) {
        println("│ Bio: $bio")
    }
    println("│ ${if (privado) "🔒 Privado" else "🌍 Público"}")
    println("└──────────────")
}

println("\n=== PARÂMETROS NOMEADOS ===")

// Ordem normal
criarPerfil("francisco", "Francisco")

// Usando nomes (pode mudar ordem!)
criarPerfil(
    username = "ana_silva",
    nome = "Ana Silva",
    bio = "Desenvolvedora | Moçambique 🇲🇿",
    verificado = true
)

// Pulando parâmetros opcionais
criarPerfil(
    nome = "Carlos",
    username = "carlos_dev",
    privado = true
)

// ========== FUNÇÃO COM VARARG (número variável de argumentos) ==========

fun calcularTotalLikes(vararg likes: Int): Int {
    var total = 0
    for (like in likes) {
        total += like
    }
    return total
}

println("\n=== VARARG - MÚLTIPLOS ARGUMENTOS ===")
println("Total de 3 posts: ${calcularTotalLikes(50, 100, 75)}")
println("Total de 5 posts: ${calcularTotalLikes(200, 150, 300, 90, 120)}")
println("Total de 1 post: ${calcularTotalLikes(500)}")

// ========== FUNÇÃO COM LISTA COMO PARÂMETRO ==========

fun exibirFeed(posts: List<String>) {
    println("\n=== FEED ===")
    for ((index, post) in posts.withIndex()) {
        println("${index + 1}. $post")
    }
}

val meusPosts = listOf(
    "Bom dia! ☀️",
    "Estudando Kotlin...",
    "Munhu chegando! 🚀"
)

exibirFeed(meusPosts)

// ========== FUNÇÃO QUE MODIFICA LISTA ==========

fun adicionarHashtags(post: String, vararg hashtags: String): String {
    var postCompleto = post
    for (tag in hashtags) {
        postCompleto += " #$tag"
    }
    return postCompleto
}

println("\n=== ADICIONAR HASHTAGS ===")
val post1 = adicionarHashtags("Desenvolvendo apps", "kotlin", "android", "mozambique")
val post2 = adicionarHashtags("Boa tarde!", "munhu", "tech")

println(post1)
println(post2)

// ========== FUNÇÃO COM PARÂMETRO DE FUNÇÃO ==========

fun processarUsuarios(usuarios: List<String>, acao: (String) -> Unit) {
    for (usuario in usuarios) {
        acao(usuario)
    }
}

println("\n=== PROCESSAMENTO EM LOTE ===")

val usuarios = listOf("francisco", "ana", "carlos")

println("Enviando notificações:")
processarUsuarios(usuarios) { usuario ->
    println("  📧 Notificação enviada para @$usuario")
}

// ========== FUNÇÃO COM VALOR PADRÃO COMPUTADO ==========

fun gerarPostId(prefixo: String = "POST", timestamp: Long = System.currentTimeMillis()): String {
    return "${prefixo}_$timestamp"
}

println("\n=== GERADOR DE IDs ===")
println(gerarPostId())
println(gerarPostId("MUNHU"))
println(gerarPostId(timestamp = 1234567890))
