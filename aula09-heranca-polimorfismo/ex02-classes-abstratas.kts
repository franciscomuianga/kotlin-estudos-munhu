println("═══════════════════════════════════════")
println("    MUNHU - CLASSES ABSTRATAS")
println("═══════════════════════════════════════")

// ========== CLASSE ABSTRATA ==========

abstract class Conteudo(
    val id: String,
    val autorId: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    var likes: Int = 0
    var visualizacoes: Int = 0
    
    // Método abstrato (DEVE ser implementado pelas classes filhas)
    abstract fun exibir()
    
    // Método abstrato com retorno
    abstract fun calcularEngajamento(): Int
    
    // Método concreto (pode ser usado por todas as filhas)
    fun curtir() {
        likes++
        println("❤️ Curtido! Total: $likes")
    }
    
    fun visualizar() {
        visualizacoes++
    }
    
    // Método concreto que pode ser sobrescrito
    open fun compartilhar() {
        println("🔄 Conteúdo compartilhado!")
    }
}

// ========== CLASSE FILHA: POST ==========

class Post(
    id: String,
    autorId: String,
    var conteudo: String
) : Conteudo(id, autorId) {
    
    var comentarios: Int = 0
    
    override fun exibir() {
        println("\n┌── POST ────────────────")
        println("│ Autor: $autorId")
        println("│ $conteudo")
        println("│ ❤️ $likes  💬 $comentarios  👁️ $visualizacoes")
        println("└────────────────────────")
    }
    
    override fun calcularEngajamento(): Int {
        return likes + (comentarios * 2)
    }
    
    fun comentar() {
        comentarios++
    }
}

// ========== CLASSE FILHA: FOTO ==========

class Foto(
    id: String,
    autorId: String,
    var url: String,
    var legenda: String = ""
) : Conteudo(id, autorId) {
    
    var comentarios: Int = 0
    var compartilhamentos: Int = 0
    
    override fun exibir() {
        println("\n┌── FOTO ────────────────")
        println("│ Autor: $autorId")
        println("│ 🖼️ [$url]")
        if (legenda.isNotEmpty()) {
            println("│ \"$legenda\"")
        }
        println("│ ❤️ $likes  💬 $comentarios  🔄 $compartilhamentos")
        println("│ 👁️ $visualizacoes visualizações")
        println("└────────────────────────")
    }
    
    override fun calcularEngajamento(): Int {
        return likes + comentarios + (compartilhamentos * 3)
    }
    
    override fun compartilhar() {
        compartilhamentos++
        super.compartilhar()
    }
}

// ========== CLASSE FILHA: VIDEO ==========

class Video(
    id: String,
    autorId: String,
    var url: String,
    var titulo: String,
    var duracao: Int  // em segundos
) : Conteudo(id, autorId) {
    
    var comentarios: Int = 0
    var compartilhamentos: Int = 0
    
    override fun exibir() {
        val minutos = duracao / 60
        val segundos = duracao % 60
        
        println("\n┌── VÍDEO ───────────────")
        println("│ Autor: $autorId")
        println("│ 🎥 $titulo")
        println("│ ⏱️ ${minutos}:${segundos.toString().padStart(2, '0')}")
        println("│ 🔗 [$url]")
        println("│ ❤️ $likes  💬 $comentarios  🔄 $compartilhamentos")
        println("│ 👁️ $visualizacoes views")
        println("└────────────────────────")
    }
    
    override fun calcularEngajamento(): Int {
        // Vídeos têm engajamento maior
        return (likes * 2) + (comentarios * 3) + (compartilhamentos * 5) + (visualizacoes / 10)
    }
    
    override fun compartilhar() {
        compartilhamentos++
        println("🔄 Vídeo compartilhado! Alcance aumentado!")
    }
}

// ========== CLASSE FILHA: ENQUETE ==========

class Enquete(
    id: String,
    autorId: String,
    var pergunta: String,
    var opcoes: List<String>
) : Conteudo(id, autorId) {
    
    val votos = mutableMapOf<String, Int>()
    
    init {
        // Inicializa votos
        opcoes.forEach { votos[it] = 0 }
    }
    
    override fun exibir() {
        println("\n┌── ENQUETE ─────────────")
        println("│ Autor: $autorId")
        println("│ ❓ $pergunta")
        println("│")
        
        val totalVotos = votos.values.sum()
        
        opcoes.forEachIndexed { index, opcao ->
            val votosOpcao = votos[opcao] ?: 0
            val porcentagem = if (totalVotos > 0) (votosOpcao * 100 / totalVotos) else 0
            println("│ ${index + 1}. $opcao")
            println("│    [${"█".repeat(porcentagem / 5)}${"░".repeat(20 - porcentagem / 5)}] $porcentagem% ($votosOpcao votos)")
        }
        
        println("│")
        println("│ 👥 $totalVotos votos totais")
        println("└────────────────────────")
    }
    
    override fun calcularEngajamento(): Int {
        return votos.values.sum() * 2  // Cada voto vale 2 pontos
    }
    
    fun votar(opcao: String) {
        if (opcao in opcoes) {
            votos[opcao] = votos[opcao]!! + 1
            println("✅ Voto registrado: $opcao")
        } else {
            println("❌ Opção inválida!")
        }
    }
}

// ========== TESTANDO TUDO ==========

println("\n=== CRIANDO DIFERENTES TIPOS DE CONTEÚDO ===")

val post1 = Post("POST_1", "francisco_raul", "Estudando classes abstratas em Kotlin! 🚀")
val foto1 = Foto("FOTO_1", "ana_silva", "foto_maputo.jpg", "Sunset em Maputo 🌅")
val video1 = Video("VIDEO_1", "carlos_dev", "tutorial_kotlin.mp4", "POO em Kotlin - Tutorial Completo", 1800)
val enquete1 = Enquete(
    "POLL_1",
    "beatriz_santos",
    "Qual feature devemos adicionar no Munhu?",
    listOf("Stories", "Vídeos curtos", "Marketplace", "Grupos")
)

println("\n=== EXIBINDO CONTEÚDOS ===")

post1.exibir()
foto1.exibir()
video1.exibir()
enquete1.exibir()

println("\n=== INTERAÇÕES ===")

post1.curtir()
post1.curtir()
post1.comentar()

foto1.curtir()
foto1.curtir()
foto1.curtir()
foto1.compartilhar()

video1.curtir()
video1.curtir()
video1.curtir()
video1.curtir()
video1.compartilhar()

repeat(5) { video1.visualizar() }

enquete1.votar("Stories")
enquete1.votar("Stories")
enquete1.votar("Vídeos curtos")
enquete1.votar("Stories")
enquete1.votar("Marketplace")

println("\n=== POLIMORFISMO - LISTA DE CONTEÚDOS ===")

val feed: List<Conteudo> = listOf(post1, foto1, video1, enquete1)

println("\nExibindo feed completo:")
for (conteudo in feed) {
    conteudo.exibir()
}

println("\n=== RANKING POR ENGAJAMENTO ===")

val ranking = feed.sortedByDescending { it.calcularEngajamento() }

ranking.forEachIndexed { index, conteudo ->
    val tipo = when (conteudo) {
        is Video -> "🎥 Vídeo"
        is Foto -> "🖼️ Foto"
        is Post -> "📝 Post"
        is Enquete -> "❓ Enquete"
        else -> "❔ Desconhecido"
    }
    
    println("${index + 1}. $tipo (ID: ${conteudo.id})")
    println("   Engajamento: ${conteudo.calcularEngajamento()} pontos")
}

// ========== PROCESSAMENTO ESPECÍFICO POR TIPO ==========

println("\n=== PROCESSAMENTO POR TIPO ===")

fun processarConteudo(conteudo: Conteudo) {
    println("\nProcessando ${conteudo.id}:")
    
    when (conteudo) {
        is Video -> {
            println("   Tipo: Vídeo")
            println("   Duração: ${conteudo.duracao}s")
            println("   Views: ${conteudo.visualizacoes}")
            if (conteudo.visualizacoes > 100) {
                println("   🔥 TRENDING!")
            }
        }
        is Foto -> {
            println("   Tipo: Foto")
            println("   URL: ${conteudo.url}")
            if (conteudo.compartilhamentos > 5) {
                println("   ⭐ POPULAR!")
            }
        }
        is Post -> {
            println("   Tipo: Post")
            println("   Comentários: ${conteudo.comentarios}")
        }
        is Enquete -> {
            println("   Tipo: Enquete")
            val totalVotos = conteudo.votos.values.sum()
            println("   Total votos: $totalVotos")
            
            // Vencedor
            val vencedor = conteudo.votos.maxByOrNull { it.value }
            if (vencedor != null && totalVotos > 0) {
                println("   🏆 Liderando: ${vencedor.key}")
            }
        }
    }
}

feed.forEach { processarConteudo(it) }
