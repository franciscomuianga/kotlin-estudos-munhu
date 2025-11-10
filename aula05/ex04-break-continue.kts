println("═══════════════════════════════════════")
println("    MUNHU - BREAK & CONTINUE")
println("═══════════════════════════════════════")

// ========== BREAK (para o loop) ==========

println("\n=== 🔍 BUSCA DE USUÁRIO ===")

val usuarios = listOf(
    "ana", "beatriz", "carlos", "daniel", 
    "francisco", "gabriela", "henrique"
)

val usuarioProcurado = "francisco"
var encontrado = false

for ((index, usuario) in usuarios.withIndex()) {
    println("Verificando: @$usuario")
    
    if (usuario == usuarioProcurado) {
        encontrado = true
        println("✅ ENCONTRADO na posição ${index + 1}!")
        break  // Para o loop aqui
    }
}

if (!encontrado) {
    println("❌ Usuário não encontrado")
}

// ========== CONTINUE (pula iteração) ==========

println("\n=== 📝 FILTRANDO POSTS ===")

val posts = listOf(
    "Post normal",
    "SPAM! Clique aqui!",
    "Conteúdo interessante",
    "Golpe! Ganhe dinheiro!",
    "Discussão sobre Kotlin",
    "SPAM SPAM SPAM",
    "Moçambique é lindo 🇲🇿"
)

println("Posts aprovados:\n")

for ((index, post) in posts.withIndex()) {
    val postLower = post.lowercase()
    
    // Se contém spam ou golpe, pula
    if (postLower.contains("spam") || postLower.contains("golpe")) {
        println("${index + 1}. [BLOQUEADO] $post")
        continue  // Pula pro próximo
    }
    
    // Só chega aqui se não teve continue
    println("${index + 1}. ✅ $post")
}

// ========== BREAK COM LABEL (loops aninhados) ==========

println("\n=== 🎯 BUSCA EM MATRIZ ===")

val comentariosPorPost = listOf(
    listOf("Legal!", "Ótimo post", "👍"),
    listOf("Interessante", "SPAM AQUI", "Concordo"),
    listOf("Parabéns", "Top demais", "🔥")
)

var spamEncontrado = false

loop@ for ((postIndex, comentarios) in comentariosPorPost.withIndex()) {
    println("\nPost ${postIndex + 1}:")
    
    for ((comentarioIndex, comentario) in comentarios.withIndex()) {
        println("  Comentário ${comentarioIndex + 1}: $comentario")
        
        if (comentario.uppercase().contains("SPAM")) {
            println("\n⚠️ SPAM DETECTADO!")
            println("Post ${postIndex + 1}, Comentário ${comentarioIndex + 1}")
            spamEncontrado = true
            break@loop  // Para TODOS os loops
        }
    }
}

if (spamEncontrado) {
    println("\n🛡️ Moderação acionada!")
}

// ========== APLICAÇÃO: VALIDAÇÃO COM BREAK ==========

println("\n=== 🔐 VALIDADOR DE USERNAME ===")

print("Digite um username: ")
val username = readln()

val caracteresProibidos = listOf(' ', '@', '#', '$', '%')
var valido = true
var caracterInvalido = ' '

for (char in username) {
    if (char in caracteresProibidos) {
        valido = false
        caracterInvalido = char
        break  // Para na primeira invalidação
    }
}

if (valido) {
    println("✅ Username válido: @$username")
} else {
    println("❌ Username inválido!")
    println("   Caractere proibido encontrado: '$caracterInvalido'")
}

// ========== APLICAÇÃO: CARREGAR ATÉ LIMITE ==========

println("\n=== 📥 CARREGAMENTO INTELIGENTE ===")

val postsFeed = listOf(
    "Post 1" to 50,   // (conteúdo, tamanho em KB)
    "Post 2" to 100,
    "Post 3" to 200,
    "Post 4" to 150,
    "Post 5" to 300,
    "Post 6" to 80,
    "Post 7" to 120
)

val limiteConexao = 500  // KB
var totalCarregado = 0
var postsCarregados = 0

println("Limite de dados: ${limiteConexao}KB\n")

for ((post, tamanho) in postsFeed) {
    // Verifica se ultrapassaria o limite
    if (totalCarregado + tamanho > limiteConexao) {
        println("\n⚠️ Limite de dados atingido!")
        println("   Não é possível carregar: $post (${tamanho}KB)")
        break
    }
    
    totalCarregado += tamanho
    postsCarregados++
    println("✅ $post carregado (${tamanho}KB) - Total: ${totalCarregado}KB")
}

println("\n📊 Resumo:")
println("   Posts carregados: $postsCarregados de ${postsFeed.size}")
println("   Dados usados: ${totalCarregado}KB de ${limiteConexao}KB")

// ========== APLICAÇÃO: PULAR POSTS JÁ VISTOS ==========

println("\n=== 👁️ FEED PERSONALIZADO ===")

val todosOsPosts = listOf(
    "Post A", "Post B", "Post C", "Post D", "Post E"
)

val postsJaVistos = setOf("Post B", "Post D")

println("Mostrando apenas posts novos:\n")

for (post in todosOsPosts) {
    if (post in postsJaVistos) {
        continue  // Pula posts já vistos
    }
    
    println("🆕 $post")
}
