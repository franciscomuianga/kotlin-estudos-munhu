println("╔════════════════════════════════════════╗")
println("║    MUNHU - SISTEMA DE ANÁLISE FEED     ║")
println("╚════════════════════════════════════════╝")

// ═══════════════════════════════════════════════════════
//  MODELS
// ═══════════════════════════════════════════════════════

data class Usuario(
    val id: String,
    val username: String,
    val seguidores: Int,
    val verificado: Boolean = false,
    val ativo: Boolean = true
)

data class Post(
    val id: String,
    val autorId: String,
    val conteudo: String,
    val hashtags: List<String>,
    val likes: Int,
    val comentarios: Int,
    val compartilhamentos: Int,
    val visualizacoes: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    val engajamento: Int
        get() = likes + (comentarios * 2) + (compartilhamentos * 3)
    
    val taxaEngajamento: Double
        get() = if (visualizacoes > 0) (engajamento.toDouble() / visualizacoes) * 100 else 0.0
}

// ═══════════════════════════════════════════════════════
//  GERADOR DE DADOS
// ═══════════════════════════════════════════════════════

fun gerarUsuarios(quantidade: Int): List<Usuario> {
    return (1..quantidade).map { i ->
        Usuario(
            id = "U$i",
            username = "usuario_$i",
            seguidores = (10..10000).random(),
            verificado = i % 10 == 0,  // 10% verificados
            ativo = i % 20 != 0  // 95% ativos
        )
    }
}

fun gerarPosts(usuarios: List<Usuario>, quantidade: Int): List<Post> {
    val hashtagsPool = listOf(
        "kotlin", "android", "mozambique", "tech", "dev",
        "programming", "munhu", "code", "app", "mobile"
    )
    
    return (1..quantidade).map { i ->
        val autor = usuarios.random()
        val numHashtags = (0..3).random()
        val hashtags = hashtagsPool.shuffled().take(numHashtags)
        val visualizacoes = (100..10000).random()
        val likes = (visualizacoes * (0.01..0.15).random()).toInt()
        val comentarios = (likes * (0.05..0.2).random()).toInt()
        val compartilhamentos = (likes * (0.02..0.1).random()).toInt()
        
        Post(
            id = "P$i",
            autorId = autor.id,
            conteudo = "Post de exemplo $i",
            hashtags = hashtags,
            likes = likes,
            comentarios = comentarios,
            compartilhamentos = compartilhamentos,
            visualizacoes = visualizacoes,
            timestamp = System.currentTimeMillis() - (i * 60000)  // 1 min entre posts
        )
    }
}

// ═══════════════════════════════════════════════════════
//  SISTEMA DE ANÁLISE
// ═══════════════════════════════════════════════════════

class AnalisadorFeed(
    private val usuarios: List<Usuario>,
    private val posts: List<Post>
) {
    
    // Estatísticas gerais
    fun estatisticasGerais() {
        println("\n╔════════════════════════════════════════╗")
        println("║       ESTATÍSTICAS GERAIS              ║")
        println("╠════════════════════════════════════════╣")
        println("║ Total usuários: ${usuarios.size}")
        println("║ • Verificados: ${usuarios.count { it.verificado }}")
        println("║ • Ativos: ${usuarios.count { it.ativo }}")
        println("║")
        println("║ Total posts: ${posts.size}")
        println("║ Total likes: ${posts.sumOf { it.likes }}")
        println("║ Total comentários: ${posts.sumOf { it.comentarios }}")
        println("║ Total compartilhamentos: ${posts.sumOf { it.compartilhamentos }}")
        println("║ Total visualizações: ${posts.sumOf { it.visualizacoes }}")
        println("╚════════════════════════════════════════╝")
    }
    
    // Top posts por engajamento
    fun topPosts(limite: Int = 10) {
        println("\n🏆 TOP $limite POSTS (ENGAJAMENTO):")
        
        posts.asSequence()
            .sortedByDescending { it.engajamento }
            .take(limite)
            .forEachIndexed { index, post ->
                val autor = usuarios.find { it.id == post.autorId }
                val emoji = when (index) {
                    0 -> "🥇"
                    1 -> "🥈"
                    2 -> "🥉"
                    else -> "  "
                }
                println("$emoji #${index + 1} - ${post.id} by @${autor?.username}")
                println("     ❤️ ${post.likes}  💬 ${post.comentarios}  🔄 ${post.compartilhamentos}")
                println("     📊 Engajamento: ${post.engajamento}")
            }
    }
    
    // Top usuários mais ativos
    fun topUsuariosAtivos(limite: Int = 10) {
        println("\n👥 TOP $limite USUÁRIOS MAIS ATIVOS:")
        
        val postsPorUsuario = posts.groupBy { it.autorId }
            .mapValues { (_, posts) -> posts.size }
            .toList()
            .sortedByDescending { it.second }
            .take(limite)
        
        postsPorUsuario.forEachIndexed { index, (usuarioId, qtdPosts) ->
            val usuario = usuarios.find { it.id == usuarioId }
            println("  ${index + 1}. @${usuario?.username} ${if (usuario?.verificado == true) "✓" else ""}")
            println("     Posts: $qtdPosts  Seguidores: ${usuario?.seguidores}")
        }
    }
    
    // Análise de hashtags
    fun analiseHashtags(limite: Int = 10) {
        println("\n#️⃣ TOP $limite HASHTAGS:")
        
        val hashtagStats = posts.asSequence()
            .flatMap { it.hashtags }
            .groupBy { it }
            .mapValues { (_, ocorrencias) -> ocorrencias.size }
            .toList()
            .sortedByDescending { it.second }
            .take(limite)
        
        hashtagStats.forEachIndexed { index, (tag, count) ->
            println("  ${index + 1}. #$tag - $count posts")
        }
        
        // Posts por hashtag popular
        val hashtagMaisPopular = hashtagStats.firstOrNull()?.first
        if (hashtagMaisPopular != null) {
            val postsComTag = posts.count { hashtagMaisPopular in it.hashtags }
            val likesTotal = posts.filter { hashtagMaisPopular in it.hashtags }
                .sumOf { it.likes }
            
            println("\n  Análise de #$hashtagMaisPopular:")
            println("    Posts: $postsComTag")
            println("    Likes totais: $likesTotal")
            println("    Média de likes: ${likesTotal / postsComTag}")
        }
    }
    
    // Taxa de engajamento média
    fun taxasEngajamento() {
        println("\n📊 TAXAS DE ENGAJAMENTO:")
        
        val taxaMedia = posts.map { it.taxaEngajamento }.average()
        println("  Média geral: ${"%.2f".format(taxaMedia)}%")
        
        // Por faixa de seguidores do autor
        val postsPorFaixa = posts.groupBy { post ->
            val autor = usuarios.find { it.id == post.autorId }
            when (autor?.seguidores ?: 0) {
                in 0..100 -> "0-100"
                in 101..1000 -> "101-1K"
                in 1001..10000 -> "1K-10K"
                else -> "10K+"
            }
        }
        
        println("\n  Por faixa de seguidores do autor:")
        postsPorFaixa.forEach { (faixa, postsNaFaixa) ->
            val taxaFaixa = postsNaFaixa.map { it.taxaEngajamento }.average()
            println("    $faixa seguidores: ${"%.2f".format(taxaFaixa)}%")
        }
    }
    
    // Usuários com melhor performance
    fun usuariosComMelhorPerformance(limite: Int = 10) {
        println("\n⭐ TOP $limite USUÁRIOS (MÉDIA DE ENGAJAMENTO):")
        
        val performancePorUsuario = posts.asSequence()
            .groupBy { it.autorId }
            .filter { (_, posts) -> posts.size >= 3 }  // Mínimo 3 posts
            .map { (usuarioId, postsDoUsuario) ->
                val usuario = usuarios.find { it.id == usuarioId }!!
                val mediaEngajamento = postsDoUsuario.map { it.engajamento }.average()
                Triple(usuario, postsDoUsuario.size, mediaEngajamento)
            }
            .sortedByDescending { it.third }
            .take(limite)
            .toList()
        
        performancePorUsuario.forEachIndexed { index, (usuario, qtdPosts, media) ->
            println("  ${index + 1}. @${usuario.username} ${if (usuario.verificado) "✓" else ""}")
            println("     Posts: $qtdPosts  Média engajamento: ${"%.1f".format(media)}")
        }
    }
    
    // Análise temporal
    fun analiseTemporal() {
        println("\n⏰ ANÁLISE TEMPORAL:")
        
        // Agrupar por hora
        val postsPorHora = posts.groupBy { post ->
            val minutos = ((System.currentTimeMillis() - post.timestamp) / 60000).toInt()
            minutos / 60  // Horas atrás
        }
        
        println("\n  Posts nas últimas horas:")
        postsPorHora.toList()
            .sortedBy { it.first }
            .take(5)
            .forEach { (horasAtras, postsNaHora) ->
                println("    Há ${horasAtras}h: ${postsNaHora.size} posts")
            }
    }
    
    // Correlação verificado x engajamento
    fun analiseVerificados() {
        println("\n✓ ANÁLISE: VERIFICADOS vs NÃO VERIFICADOS:")
        
        val postsVerificados = posts.filter { post ->
            usuarios.find { it.id == post.autorId }?.verificado == true
        }
        
        val postsNaoVerificados = posts.filter { post ->
            usuarios.find { it.id == post.autorId }?.verificado == false
        }
        
        val mediaVerificados = postsVerificados.map { it.engajamento }.average()
        val mediaNaoVerificados = postsNaoVerificados.map { it.engajamento }.average()
        
        println("  Verificados:")
        println("    Posts: ${postsVerificados.size}")
        println("    Média engajamento: ${"%.1f".format(mediaVerificados)}")
        
        println("\n  Não verificados:")
        println("    Posts: ${postsNaoVerificados.size}")
        println("    Média engajamento: ${"%.1f".format(mediaNaoVerificados)}")
        
        val diferenca = ((mediaVerificados / mediaNaoVerificados - 1) * 100)
        println("\n  💡 Verificados têm ${"%.1f".format(diferenca)}% mais engajamento")
    }
    
    // Posts que precisam de boost
    fun postsPrecisandoBoost(limite: Int = 10) {
        println("\n📢 POSTS PRECISANDO DE BOOST:")
        
        posts.asSequence()
            .filter { it.visualizacoes < 500 }  // Poucas views
            .filter { it.taxaEngajamento > 5.0 }  // Mas boa taxa
            .sortedByDescending { it.taxaEngajamento }
            .take(limite)
            .forEachIndexed { index, post ->
                val autor = usuarios.find { it.id == post.autorId }
                println("  ${index + 1}. ${post.id} by @${autor?.username}")
                println("     Views: ${post.visualizacoes}  Taxa: ${"%.2f".format(post.taxaEngajamento)}%")
                println("     💡 Bom conteúdo com pouco alcance!")
            }
    }
    
    // Relatório completo
    fun relatorioCompleto() {
        estatisticasGerais()
        topPosts(5)
        topUsuariosAtivos(5)
        analiseHashtags(5)
        taxasEngajamento()
        usuariosComMelhorPerformance(5)
        analiseTemporal()
        analiseVerificados()
        postsPrecisandoBoost(5)
    }
}

// ═══════════════════════════════════════════════════════
//  EXECUÇÃO
// ═══════════════════════════════════════════════════════

println("\n🚀 Gerando dados do sistema...\n")

val usuarios = gerarUsuarios(100)
val posts = gerarPosts(usuarios, 1000)

println("✅ ${usuarios.size} usuários gerados")
println("✅ ${posts.size} posts gerados")

val analisador = AnalisadorFeed(usuarios, posts)

println("\n" + "═".repeat(45))
println("INICIANDO ANÁLISE COMPLETA")
println("═".repeat(45))

analisador.relatorioCompleto()

// ═══════════════════════════════════════════════════════
//  FILTROS PERSONALIZADOS
// ═══════════════════════════════════════════════════════

println("\n" + "═".repeat(45))
println("FILTROS PERSONALIZADOS")
println("═".repeat(45))

// Feed personalizado
fun feedPersonalizado(
    todosOsPosts: List<Post>,
    todosOsUsuarios: List<Usuario>,
    usuarioAtual: Usuario
): List<Post> {
    return todosOsPosts.asSequence()
        .filter { post ->
            // Não mostrar posts de usuários inativos
            val autor = todosOsUsuarios.find { it.id == post.autorId }
            autor?.ativo == true
        }
        .filter { it.visualizacoes >= 50 }  // Mínimo de qualidade
        .sortedWith(
            compareByDescending<Post> { it.engajamento }
                .thenByDescending { it.timestamp }
        )
        .take(20)
        .toList()
}

val usuarioTeste = usuarios.first()
val feedPersonalizado = feedPersonalizado(posts, usuarios, usuarioTeste)

println("\n📱 FEED PERSONALIZADO (top 5):")
feedPersonalizado.take(5).forEach { post ->
    val autor = usuarios.find { it.id == post.autorId }
    println("  ${post.id} by @${autor?.username}")
    println("    ${post.engajamento} engajamento  ${post.hashtags.take(2).joinToString(" ") { "#$it" }}")
}

println("\n✅ ANÁLISE COMPLETA FINALIZADA!")
