println("═══════════════════════════════════════")
println("    MUNHU - CLASSES E OBJETOS")
println("═══════════════════════════════════════")

// ========== CLASSE SIMPLES ==========

class Usuario {
    var nome: String = ""
    var idade: Int = 0
    var email: String = ""
    
    fun apresentar() {
        println("Olá! Sou $nome, tenho $idade anos.")
    }
    
    fun podeUsarMunhu(): Boolean {
        return idade >= 13
    }
}

println("\n=== CRIANDO OBJETOS ===")

// Criar instância (objeto)
val usuario1 = Usuario()
usuario1.nome = "Francisco Raul"
usuario1.idade = 17
usuario1.email = "francisco@munhu.co.mz"

usuario1.apresentar()
println("Pode usar Munhu? ${usuario1.podeUsarMunhu()}")

// Outro objeto da mesma classe
val usuario2 = Usuario()
usuario2.nome = "Ana Silva"
usuario2.idade = 12
usuario2.email = "ana@email.com"

usuario2.apresentar()
println("Pode usar Munhu? ${usuario2.podeUsarMunhu()}")

// ========== CLASSE COM CONSTRUTOR PRIMÁRIO ==========

class Post(
    var autorId: String,
    var conteudo: String,
    var likes: Int = 0  // Valor padrão
) {
    fun curtir() {
        likes++
        println("Post curtido! Total: $likes likes")
    }
    
    fun exibir() {
        println("\n┌────────────────────────")
        println("│ Autor: $autorId")
        println("│ $conteudo")
        println("│ ❤️ $likes likes")
        println("└────────────────────────")
    }
}

println("\n=== CONSTRUTOR PRIMÁRIO ===")

val post1 = Post("francisco_raul", "Estudando POO em Kotlin! 🚀")
post1.exibir()
post1.curtir()
post1.curtir()

val post2 = Post(
    autorId = "ana_silva",
    conteudo = "Primeiro post no Munhu!",
    likes = 5
)
post2.exibir()

// ========== CLASSE COM INIT ==========

class Notificacao(val tipo: String, val mensagem: String) {
    val timestamp: Long
    val emoji: String
    
    init {
        timestamp = System.currentTimeMillis()
        
        emoji = when (tipo) {
            "curtida" -> "❤️"
            "comentario" -> "💬"
            "seguidor" -> "👥"
            else -> "📢"
        }
        
        println("🔔 Notificação criada: $emoji $mensagem")
    }
    
    fun exibir() {
        println("$emoji $mensagem")
    }
}

println("\n=== BLOCO INIT ===")

val notif1 = Notificacao("curtida", "@ana curtiu seu post")
val notif2 = Notificacao("seguidor", "@carlos começou a te seguir")

notif1.exibir()
notif2.exibir()

// ========== MÚLTIPLOS CONSTRUTORES ==========

class Perfil {
    var username: String
    var nome: String
    var bio: String
    var verificado: Boolean
    
    // Construtor primário
    constructor(username: String, nome: String) {
        this.username = username
        this.nome = nome
        this.bio = ""
        this.verificado = false
        println("✅ Perfil básico criado: @$username")
    }
    
    // Construtor secundário (mais completo)
    constructor(username: String, nome: String, bio: String, verificado: Boolean) {
        this.username = username
        this.nome = nome
        this.bio = bio
        this.verificado = verificado
        println("✅ Perfil completo criado: @$username ${if (verificado) "✓" else ""}")
    }
    
    fun exibir() {
        println("\n@$username ${if (verificado) "✓" else ""}")
        println("$nome")
        if (bio.isNotEmpty()) {
            println("\"$bio\"")
        }
    }
}

println("\n=== MÚLTIPLOS CONSTRUTORES ===")

val perfil1 = Perfil("francisco_raul", "Francisco Raul Muianga Junior")
perfil1.exibir()

val perfil2 = Perfil(
    "munhu_oficial",
    "Munhu Oficial",
    "Rede Social Moçambicana 🇲🇿",
    true
)
perfil2.exibir()

// ========== PROPRIEDADES COMPUTADAS ==========

class Estatisticas(
    var posts: Int,
    var seguidores: Int,
    var seguindo: Int
) {
    // Propriedade computada (getter personalizado)
    val taxaEngajamento: Double
        get() = if (seguidores > 0) (posts.toDouble() / seguidores * 100) else 0.0
    
    val ratio: String
        get() = if (seguidores > seguindo) "Positivo ✅" else "Negativo ⚠️"
    
    val classificacao: String
        get() = when {
            seguidores >= 10000 -> "Influencer 👑"
            seguidores >= 1000 -> "Popular 🔥"
            seguidores >= 100 -> "Crescendo 📈"
            else -> "Iniciante 🌱"
        }
    
    fun exibir() {
        println("\n📊 ESTATÍSTICAS:")
        println("   Posts: $posts")
        println("   Seguidores: $seguidores")
        println("   Seguindo: $seguindo")
        println("   Taxa engajamento: ${"%.2f".format(taxaEngajamento)}%")
        println("   Ratio: $ratio")
        println("   Classificação: $classificacao")
    }
}

println("\n=== PROPRIEDADES COMPUTADAS ===")

val stats1 = Estatisticas(45, 1200, 340)
stats1.exibir()

val stats2 = Estatisticas(150, 15000, 500)
stats2.exibir()
