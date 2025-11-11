println("╔════════════════════════════════════════╗")
println("║      MUNHU API - SISTEMA COMPLETO      ║")
println("╚════════════════════════════════════════╝")

// ========== DATA CLASSES (estruturas de dados) ==========

data class Usuario(
    val id: String,
    val username: String,
    val nome: String,
    val email: String,
    var seguidores: Int = 0,
    var seguindo: Int = 0,
    var verificado: Boolean = false
)

data class Post(
    val id: String,
    val autorId: String,
    val conteudo: String,
    var likes: Int = 0,
    var comentarios: Int = 0,
    var compartilhamentos: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

// ========== API: USUÁRIOS ==========

fun criarUsuario(nome: String, email: String): Usuario? {
    // Validações
    if (!email.contains("@")) {
        println("❌ Email inválido!")
        return null
    }
    
    val username = nome.lowercase().replace(" ", "_")
    val id = "USER_${System.currentTimeMillis()}"
    
    return Usuario(id, username, nome, email)
}

fun seguirUsuario(usuario: Usuario, alvo: Usuario) {
    usuario.seguindo++
    alvo.seguidores++
    println("✅ @${usuario.username} agora segue @${alvo.username}")
}

fun verificarUsuario(usuario: Usuario) {
    if (usuario.seguidores >= 1000) {
        usuario.verificado = true
        println("✅ @${usuario.username} agora é verificado!")
    } else {
        println("❌ Mínimo de 1000 seguidores necessário")
    }
}

fun exibirPerfil(usuario: Usuario) {
    println("\n┌─── PERFIL ───────────────────")
    println("│ @${usuario.username} ${if (usuario.verificado) "✓" else ""}")
    println("│ ${usuario.nome}")
    println("│")
    println("│ 👥 ${usuario.seguidores} seguidores")
    println("│ 👤 ${usuario.seguindo} seguindo")
    println("│")
    println("│ ID: ${usuario.id}")
    println("└──────────────────────────────")
}

// ========== API: POSTS ==========

fun criarPost(usuario: Usuario, conteudo: String): Post? {
    if (conteudo.length > 280) {
        println("❌ Post muito longo! (máx 280 caracteres)")
        return null
    }
    
    if (conteudo.isBlank()) {
        println("❌ Post não pode estar vazio!")
        return null
    }
    
    val postId = "POST_${System.currentTimeMillis()}"
    return Post(postId, usuario.id, conteudo)
}

fun curtirPost(post: Post, usuario: Usuario) {
    post.likes++
    println("❤️ @${usuario.username} curtiu o post")
}

fun comentarPost(post: Post, usuario: Usuario, comentario: String) {
    post.comentarios++
    println("💬 @${usuario.username}: $comentario")
}

fun compartilharPost(post: Post, usuario: Usuario) {
    post.compartilhamentos++
    println("🔄 @${usuario.username} compartilhou o post")
}

fun exibirPost(post: Post, autor: Usuario) {
    val tempoDecorrido = (System.currentTimeMillis() - post.timestamp) / 60000
    
    println("\n┌────────────────────────────────")
    println("│ @${autor.username} ${if (autor.verificado) "✓" else ""} · ${tempoDecorrido}min atrás")
    println("│")
    println("│ ${post.conteudo}")
    println("│")
    println("│ ❤️ ${post.likes}  💬 ${post.comentarios}  🔄 ${post.compartilhamentos}")
    println("└────────────────────────────────")
}

// ========== API: FEED ==========

fun gerarFeed(posts: List<Post>, usuarios: Map<String, Usuario>) {
    println("\n╔════════════════════════════════════════╗")
    println("║              FEED MUNHU                 ║")
    println("╚════════════════════════════════════════╝")
    
    for (post in posts) {
        val autor = usuarios[post.autorId]
        if (autor != null) {
            exibirPost(post, autor)
        }
    }
}

// ========== API: BUSCA ==========

fun buscarUsuarios(usuarios: List, query: String): List {
return usuarios.filter { usuario ->
usuario.username.contains(query, ignoreCase = true) ||
usuario.nome.contains(query, ignoreCase = true)
}
}
fun buscarPosts(posts: List, query: String): List {
return posts.filter { post ->
post.conteudo.contains(query, ignoreCase = true)
}
}
// ========== API: ESTATÍSTICAS ==========
fun calcularEngajamentoPost(post: Post): Int {
return post.likes + (post.comentarios * 2) + (post.compartilhamentos * 3)
}
fun obterTopPosts(posts: List, limite: Int = 5): List {
return posts.sortedByDescending { calcularEngajamentoPost(it) }.take(limite)
}
fun obterTopUsuarios(usuarios: List, limite: Int = 5): List {
return usuarios.sortedByDescending { it.seguidores }.take(limite)
}
fun gerarRelatorio(usuario: Usuario, posts: List) {
val meusPosts = posts.filter { it.autorId == usuario.id }
val totalLikes = meusPosts.sumOf { it.likes }
val totalComentarios = meusPosts.sumOf { it.comentarios }
val mediaLikes = if (meusPosts.isNotEmpty()) totalLikes / meusPosts.size else 0
println("\n╔════════════════════════════════════════╗")
println("║         RELATÓRIO DE @${usuario.username}         ")
println("╠════════════════════════════════════════╣")
println("║ Posts publicados: ${meusPosts.size}")
println("║ Total de likes: $totalLikes")
println("║ Total de comentários: $totalComentarios")
println("║ Média de likes/post: $mediaLikes")
println("║ Seguidores: ${usuario.seguidores}")
println("║ Taxa de engajamento: ${if (usuario.seguidores > 0) "%.2f".format(totalLikes.toDouble() / usuario.seguidores * 100) else "0"}%")
println("╚════════════════════════════════════════╝")
}
// ========== SIMULAÇÃO COMPLETA ==========
println("\n🚀 INICIANDO SISTEMA MUNHU...\n")
// Criar usuários
val usuarios = mutableMapOf<String, Usuario>()
val francisco = criarUsuario("Francisco Silva", "francisco@munhu.co.mz")!!
usuarios[francisco.id] = francisco
val ana = criarUsuario("Ana Costa", "ana@munhu.co.mz")!!
usuarios[ana.id] = ana
val carlos = criarUsuario("Carlos Mendes", "carlos@munhu.co.mz")!!
usuarios[carlos.id] = carlos
val beatriz = criarUsuario("Beatriz Santos", "beatriz@munhu.co.mz")!!
usuarios[beatriz.id] = beatriz
println("✅ ${usuarios.size} usuários criados\n")
// Simular seguidores
francisco.seguidores = 1200
ana.seguidores = 850
carlos.seguidores = 2500
beatriz.seguidores = 450
// Verificar usuários elegíveis
println("🔐 VERIFICANDO USUÁRIOS:")
verificarUsuario(francisco)
verificarUsuario(carlos)
println()
// Criar relacionamentos
println("👥 CRIANDO CONEXÕES:")
seguirUsuario(francisco, ana)
seguirUsuario(francisco, carlos)
seguirUsuario(ana, francisco)
seguirUsuario(beatriz, francisco)
println()
// Exibir perfis
exibirPerfil(francisco)
exibirPerfil(carlos)
// Criar posts
println("\n📝 CRIANDO POSTS:")
val posts = mutableListOf()
val post1 = criarPost(francisco, "Desenvolvendo o Munhu! 🇲🇿 #tech #mozambique")!!
posts.add(post1)
val post2 = criarPost(ana, "Bom dia! Alguém sabe Kotlin? 💻")!!
posts.add(post2)
val post3 = criarPost(carlos, "Moçambique é lindo! 🌅")!!
posts.add(post3)
val post4 = criarPost(beatriz, "Primeira vez no Munhu! 🎉")!!
posts.add(post4)
val post5 = criarPost(francisco, "210 dias de código! Rumo ao Munhu V1 🚀")!!
posts.add(post5)
println("✅ ${posts.size} posts criados\n")
// Simular interações
println("💬 SIMULANDO INTERAÇÕES:")
curtirPost(post1, ana)
curtirPost(post1, carlos)
curtirPost(post1, beatriz)
comentarPost(post1, ana, "Incrível! Parabéns! 👏")
comentarPost(post1, carlos, "Sucesso, mano! 🔥")
compartilharPost(post1, ana)
println()
curtirPost(post2, francisco)
comentarPost(post2, francisco, "Sim! Estou estudando há 6 dias 💪")
println()
curtirPost(post5, ana)
curtirPost(post5, carlos)
curtirPost(post5, beatriz)
println()
// Atualizar likes/comentários manualmente (simulação)
post1.likes += 147
post1.comentarios += 28
post2.likes += 52
post3.likes += 234
post3.comentarios += 45
post4.likes += 18
post5.likes += 89
post5.comentarios += 15
// Gerar feed
gerarFeed(posts, usuarios)
// Buscar usuários
println("\n🔍 BUSCA: 'silva'")
val resultadosBusca = buscarUsuarios(usuarios.values.toList(), "silva")
resultadosBusca.forEach { println("   • @{it.username} ({it.nome})") }
// Buscar posts
println("\n🔍 BUSCA EM POSTS: 'munhu'")
val postsBuscados = buscarPosts(posts, "munhu")
postsBuscados.forEach { println("   • "{it.conteudo}" ({it.likes} likes)") }
// Top posts
println("\n🏆 TOP 3 POSTS:")
val topPosts = obterTopPosts(posts, 3)
topPosts.forEachIndexed { index, post ->
val autor = usuarios[post.autorId]!!
val engajamento = calcularEngajamentoPost(post)
val emoji = when (index) {
0 -> "🥇"
1 -> "🥈"
2 -> "🥉"
else -> ""
}
println("emoji #{index + 1} - @${autor.username}: {engajamento} pontos")println("     "{post.conteudo.take(50)}..."")
}
// Top usuários
println("\n👑 TOP USUÁRIOS:")
val topUsuarios = obterTopUsuarios(usuarios.values.toList(), 3)
topUsuarios.forEachIndexed { index, usuario ->
val emoji = when (index) {
0 -> "🥇"
1 -> "🥈"
2 -> "🥉"
else -> ""
}
println("emoji #{index + 1} - @{usuario.username} ({usuario.seguidores} seguidores) ${if (usuario.verificado) "✓" else ""}")
}
// Relatório de Francisco
gerarRelatorio(francisco, posts)
// Estatísticas gerais
println("\n📊 ESTATÍSTICAS GERAIS:")
println("   Total de usuários: ${usuarios.size}")
println("   Total de posts: ${posts.size}")
println("   Total de likes: ${posts.sumOf { it.likes }}")
println("   Total de comentários: ${posts.sumOf { it.comentarios }}")
println("   Total de compartilhamentos: ${posts.sumOf { it.compartilhamentos }}")
println("   Usuários verificados: ${usuarios.values.count { it.verificado }}")
val totalEngajamento = posts.sumOf { calcularEngajamentoPost(it) }
println("   Engajamento total: $totalEngajamento pontos")
println("\n╔════════════════════════════════════════╗")
println("║   ✅ SISTEMA MUNHU FUNCIONANDO!        ║")
println("╚════════════════════════════════════════╝")
