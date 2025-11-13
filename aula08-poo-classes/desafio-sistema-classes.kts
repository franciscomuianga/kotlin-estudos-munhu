println("╔════════════════════════════════════════╗")
println("║    MUNHU - SISTEMA POO COMPLETO        ║")
println("╚════════════════════════════════════════╝")

// ========== CLASSES DO SISTEMA ==========

// Configurações globais
object Config {
    const val VERSAO = "2.0.0"
    const val MAX_POST_LENGTH = 280
    var totalUsuarios = 0
        private set
    
    fun incrementarUsuarios() {
        totalUsuarios++
    }
}

// Classe base Usuario
class Usuario(
    val id: String,
    username: String,
    var nome: String,
    var bio: String = ""
) {
    var username: String = username
        set(value) {
            if (value.length >= 3) field = value.lowercase()
        }
    
    var seguidores: Int = 0
        private set
    
    var seguindo: Int = 0
        private set
    
    var verificado: Boolean = false
    
    val posts = mutableListOf<Post>()
    
    companion object {
        private var proximoId = 1
        
        fun criar(username: String, nome: String): Usuario {
            val id = "USER_${proximoId++}"
            Config.incrementarUsuarios()
            return Usuario(id, username, nome)
        }
    }
    
    fun seguir(outroUsuario: Usuario) {
        seguindo++
        outroUsuario.seguidores++
        println("✅ @$username agora segue @${outroUsuario.username}")
    }
    
    fun verificar() {
        if (seguidores >= 1000) {
            verificado = true
            println("✅ @$username agora é verificado!")
        }
    }
    
    fun criarPost(conteudo: String): Post? {
        if (conteudo.length > Config.MAX_POST_LENGTH) {
            println("❌ Post muito longo!")
            return null
        }
        
        val post = Post(this, conteudo)
        posts.add(post)
        return post
    }
    
    fun exibirPerfil() {
        println("\n┌─── PERFIL ─────────────────")
        println("│ @$username ${if (verificado) "✓" else ""}")
        println("│ $nome")
        if (bio.isNotEmpty()) println("│ \"$bio\"")
        println("│")
        println("│ 👥 $seguidores seguidores · $seguindo seguindo")
        println("│ 📝 ${posts.size} posts")
        println("└────────────────────────────")
    }
}

// Classe Post
class Post(
    val autor: Usuario,
    var conteudo: String
) {
    val id: String = "POST_${System.currentTimeMillis()}"
    var likes: Int = 0
        private set
    
    val comentarios = mutableListOf<Comentario>()
    var compartilhamentos: Int = 0
        private set
    
    val timestamp = System.currentTimeMillis()
    
    val engajamento: Int
        get() = likes + comentarios.size + compartilhamentos
    
    fun curtir() {
        likes++
    }
    
    fun comentar(autor: Usuario, texto: String) {
        val comentario = Comentario(autor, texto)
        comentarios.add(comentario)
    }
    
    fun compartilhar() {
        compartilhamentos++
    }
    
    fun exibir() {
        println("\n┌────────────────────────────────")
        println("│ @${autor.username} ${if (autor.verificado) "✓" else ""}")
        println("│")
        println("│ $conteudo")
        println("│")
        println("│ ❤️ $likes  💬 ${comentarios.size}  🔄 $compartilhamentos")
        
        if (comentarios.isNotEmpty()) {
            println("│")
            comentarios.take(2).forEach { comentario ->
                println("│   @${comentario.autor.username}: ${comentario.texto}")
            }
            if (comentarios.size > 2) {
                println("│   ... e mais ${comentarios.size - 2} comentários")
            }
        }
        
        println("└────────────────────────────────")
    }
}

// Classe Comentario
class Comentario(
    val autor: Usuario,
    val texto: String
) {
    val timestamp = System.currentTimeMillis()
}

// Classe Feed (gerencia posts)
object Feed {
    private val todosPosts = mutableListOf<Post>()
    
    fun adicionar(post: Post) {
        todosPosts.add(0, post)  // Adiciona no início
    }
    
    fun exibir(limite: Int = 10) {
        println("\n╔════════════════════════════════════════╗")
        println("║              FEED MUNHU                 ║")
        println("╚════════════════════════════════════════╝")
        
        if (todosPosts.isEmpty()) {
            println("\n📭 Nenhum post ainda!")
            return
        }
        
        todosPosts.take(limite).forEach { it.exibir() }
        
        if (todosPosts.size > limite) {
            println("\n... e mais ${todosPosts.size - limite} posts")
        }
    }
    
    fun topPosts(quantidade: Int = 5): List<Post> {
        return todosPosts.sortedByDescending { it.engajamento }.take(quantidade)
    }
}

// ========== SIMULAÇÃO DO SISTEMA ==========

println("\n🚀 Iniciando Munhu v${Config.VERSAO}...\n")

// Criar usuários
val francisco = Usuario.criar("francisco_raul", "Francisco Raul Muianga Junior")
francisco.bio = "Desenvolvedor | THE FRA LABS | Moçambique 🇲🇿"

val ana = Usuario.criar("ana_silva", "Ana Silva")
ana.bio = "Designer | Maputo"

val carlos = Usuario.criar("carlos_dev", "Carlos Mendes")
carlos.bio = "Dev Full Stack"

val beatriz = Usuario.criar("beatriz_santos", "Beatriz Santos")

println("✅ ${Config.totalUsuarios} usuários criados\n")

// Simular seguidores
francisco.seguindo++
francisco.seguidores = 1200

ana.seguindo++
ana.seguidores = 850

carlos.seguindo++
carlos.seguidores = 2500

beatriz.seguindo++
beatriz.seguidores = 450

// Verificar usuários elegíveis
francisco.verificar()
carlos.verificar()

// Criar relacionamentos
println("\n👥 CONEXÕES:")
ana.seguir(francisco)
carlos.seguir(francisco)
beatriz.seguir(francisco)
francisco.seguir(ana)
francisco.seguir(carlos)

// Exibir perfis
francisco.exibirPerfil()
carlos.exibirPerfil()

// Criar posts
println("\n📝 CRIANDO POSTS:")

val post1 = francisco.criarPost("Desenvolvendo o Munhu com POO em Kotlin! 🇲🇿 #tech #mozambique")
if (post1 != null) Feed.adicionar(post1)

val post2 = ana.criarPost("Adorando o design do Munhu! 🎨")
if (post2 != null) Feed.adicionar(post2)

val post3 = carlos.criarPost("POO deixa o código muito mais organizado!")
if (post3 != null) Feed.adicionar(post3)

val post4 = beatriz.criarPost("Primeira vez no Munhu! Alguém me explica como funciona? 😅")
if (post4 != null) Feed.adicionar(post4)

val post5 = francisco.criarPost("Aula 8 completa! Classes e Objetos dominados! 💪")
if (post5 != null) Feed.adicionar(post5)

// Interações
println("\n💬 INTERAÇÕES:")

post1?.curtir()
post1?.curtir()
post1?.curtir()
post1?.comentar(ana, "Parabéns! Projeto incrível! 👏")
post1?.comentar(carlos, "Sucesso, mano! 🔥")
post1?.comentar(beatriz, "Mal posso esperar pra usar!")
post1?.compartilhar()

post2?.curtir()
post2?.curtir()
post2?.comentar(francisco, "Obrigado! Teu feedback é importante!")

post5?.curtir()
post5?.curtir()
post5?.curtir()
post5?.curtir()

// Exibir feed
Feed.exibir()

// Top posts
println("\n🏆 TOP 3 POSTS MAIS ENGAJADOS:")
Feed.topPosts(3).forEachIndexed { index, post ->
    val emoji = when (index) {
        0 -> "🥇"
        1 -> "🥈"
        2 -> "🥉"
        else -> ""
    }
    println("$emoji @${post.autor.username}: ${post.engajamento} pontos de engajamento")
}

// Estatísticas finais
println("\n╔════════════════════════════════════════╗")
println("║         ESTATÍSTICAS FINAIS             ║")
println("╠════════════════════════════════════════╣")
println("║ Total de usuários: ${Config.totalUsuarios}")
println("║ Posts publicados: ${francisco.posts.size + ana.posts.size + carlos.posts.size + beatriz.posts.size}")
println("║ Usuários verificados: 2")
println("║ Versão do sistema: ${Config.VERSAO}")
println("╚════════════════════════════════════════╝")

println("\n✅ SISTEMA FUNCIONANDO PERFEITAMENTE!")
