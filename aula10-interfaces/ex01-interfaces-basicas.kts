println("═══════════════════════════════════════")
println("    MUNHU - INTERFACES BÁSICAS")
println("═══════════════════════════════════════")

// ========== INTERFACE SIMPLES ==========

interface Curtivel {
    fun curtir()
    fun descurtir()
    fun totalCurtidas(): Int
}

class Post(val conteudo: String) : Curtivel {
    private var curtidas = 0
    
    override fun curtir() {
        curtidas++
        println("❤️ Post curtido! Total: $curtidas")
    }
    
    override fun descurtir() {
        if (curtidas > 0) {
            curtidas--
            println("💔 Curtida removida. Total: $curtidas")
        }
    }
    
    override fun totalCurtidas(): Int = curtidas
    
    fun exibir() {
        println("\n📝 \"$conteudo\"")
        println("   ❤️ $curtidas curtidas")
    }
}

println("\n=== INTERFACE CURTIVEL ===")

val post1 = Post("Estudando interfaces em Kotlin! 🚀")
post1.exibir()
post1.curtir()
post1.curtir()
post1.curtir()
post1.exibir()
post1.descurtir()
post1.exibir()

// ========== MÚLTIPLAS INTERFACES ==========

interface Comentavel {
    fun comentar(texto: String)
    fun totalComentarios(): Int
}

interface Compartilhavel {
    fun compartilhar()
    fun totalCompartilhamentos(): Int
}

class PostCompleto(val conteudo: String) : Curtivel, Comentavel, Compartilhavel {
    private var curtidas = 0
    private val comentarios = mutableListOf<String>()
    private var compartilhamentos = 0
    
    override fun curtir() {
        curtidas++
    }
    
    override fun descurtir() {
        if (curtidas > 0) curtidas--
    }
    
    override fun totalCurtidas() = curtidas
    
    override fun comentar(texto: String) {
        comentarios.add(texto)
        println("💬 Novo comentário: \"$texto\"")
    }
    
    override fun totalComentarios() = comentarios.size
    
    override fun compartilhar() {
        compartilhamentos++
        println("🔄 Post compartilhado!")
    }
    
    override fun totalCompartilhamentos() = compartilhamentos
    
    fun exibir() {
        println("\n┌── POST COMPLETO ──────────")
        println("│ \"$conteudo\"")
        println("│")
        println("│ ❤️ $curtidas  💬 ${comentarios.size}  🔄 $compartilhamentos")
        
        if (comentarios.isNotEmpty()) {
            println("│")
            println("│ Comentários:")
            comentarios.take(3).forEach { coment ->
                println("│   • \"$coment\"")
            }
            if (comentarios.size > 3) {
                println("│   ... e mais ${comentarios.size - 3}")
            }
        }
        
        println("└───────────────────────────")
    }
    
    fun engajamentoTotal(): Int {
        return curtidas + (comentarios.size * 2) + (compartilhamentos * 3)
    }
}

println("\n=== MÚLTIPLAS INTERFACES ===")

val post2 = PostCompleto("Munhu vai ser incrível! 🇲🇿")
post2.curtir()
post2.curtir()
post2.curtir()
post2.comentar("Mal posso esperar!")
post2.comentar("Vai ser sucesso!")
post2.compartilhar()
post2.exibir()
println("\n📊 Engajamento total: ${post2.engajamentoTotal()} pontos")

// ========== INTERFACE COM PROPRIEDADES ==========

interface Identificavel {
    val id: String
    val tipo: String
}

interface Temporal {
    val timestamp: Long
    
    fun idadeEmMinutos(): Long {
        return (System.currentTimeMillis() - timestamp) / 60000
    }
}

class Comentario(
    override val id: String,
    val autor: String,
    val texto: String,
    override val timestamp: Long = System.currentTimeMillis()
) : Identificavel, Temporal {
    
    override val tipo = "COMENTARIO"
    
    fun exibir() {
        println("\n💬 @$autor (há ${idadeEmMinutos()} min)")
        println("   \"$texto\"")
        println("   ID: $id")
    }
}

println("\n=== PROPRIEDADES EM INTERFACES ===")

val coment1 = Comentario("COM_1", "francisco_raul", "Ótimo post!")
Thread.sleep(100)  // Simula tempo
val coment2 = Comentario("COM_2", "ana_silva", "Concordo!")

coment1.exibir()
coment2.exibir()

// ========== INTERFACE COM MÉTODOS PADRÃO ==========

interface Moderavel {
    var flagsRecebidas: Int
    
    fun denunciar(motivo: String) {
        flagsRecebidas++
        println("🚩 Denúncia recebida: $motivo")
        println("   Total de flags: $flagsRecebidas")
        
        if (flagsRecebidas >= 3) {
            marcarParaRevisao()
        }
    }
    
    fun marcarParaRevisao() {
        println("⚠️ Conteúdo marcado para revisão de moderador")
    }
    
    // Método que pode ser sobrescrito
    fun remover() {
        println("🗑️ Conteúdo removido automaticamente")
    }
}

class PostModeravel(val conteudo: String) : Moderavel {
    override var flagsRecebidas = 0
    
    override fun remover() {
        println("🗑️ POST REMOVIDO: \"${conteudo.take(30)}...\"")
        println("   Autor será notificado")
    }
    
    fun exibir() {
        if (flagsRecebidas < 3) {
            println("\n📝 \"$conteudo\"")
            println("   🚩 $flagsRecebidas flag(s)")
        } else {
            println("\n⚠️ [CONTEÚDO EM REVISÃO]")
            println("   🚩 $flagsRecebidas flag(s)")
        }
    }
}

println("\n=== MÉTODOS PADRÃO ===")

val post3 = PostModeravel("Conteúdo potencialmente problemático")
post3.exibir()
post3.denunciar("Linguagem ofensiva")
post3.denunciar("Spam")
post3.exibir()
post3.denunciar("Desinformação")
post3.exibir()
post3.remover()

// ========== POLIMORFISMO COM INTERFACES ==========

println("\n=== POLIMORFISMO ===")

fun processarCurtivel(item: Curtivel) {
    println("\nProcessando item curtível...")
    item.curtir()
    item.curtir()
    println("Total de curtidas: ${item.totalCurtidas()}")
}

val post4 = Post("Teste de polimorfismo")
val post5 = PostCompleto("Outro teste")

processarCurtivel(post4)
processarCurtivel(post5)

// ========== LISTA DE INTERFACES ==========

println("\n=== LISTA POLIMÓRFICA ===")

val itensCurtiveis: List<Curtivel> = listOf(
    Post("Post 1"),
    PostCompleto("Post 2"),
    Post("Post 3")
)

println("Curtindo todos os itens:")
itensCurtiveis.forEach { item ->
    repeat(2) { item.curtir() }
}

println("\nTotal geral de curtidas:")
val totalGeral = itensCurtiveis.sumOf { it.totalCurtidas() }
println("$totalGeral curtidas no feed")
