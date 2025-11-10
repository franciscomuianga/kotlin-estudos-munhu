println("╔════════════════════════════════════════╗")
println("║      MUNHU - GERADOR DE TIMELINE       ║")
println("╚════════════════════════════════════════╝")

// ========== DADOS SIMULADOS ==========

val usuarios = listOf(
    "francisco", "ana", "carlos", "beatriz", "daniel",
    "eduarda", "felipe", "gabriela", "henrique", "isabela"
)

val tiposPosts = listOf(
    "compartilhou uma foto",
    "publicou um pensamento",
    "fez check-in",
    "atualizou status",
    "compartilhou um link"
)

val reacoes = listOf("❤️", "👍", "😂", "😮", "😢")

// ========== CONFIGURAÇÕES ==========

print("\n⚙️ Quantos posts gerar? (máx 50): ")
val numPostsInput = readln().toIntOrNull() ?: 10
val numPosts = if (numPostsInput > 50) 50 else numPostsInput

print("🎯 Filtrar por usuário? (deixe vazio para todos): ")
val filtroUsuario = readln().lowercase().trim()

print("🔢 Mínimo de likes para exibir? (0 para todos): ")
val minimoLikes = readln().toIntOrNull() ?: 0

// ========== GERAÇÃO DA TIMELINE ==========

println("\n" + "═".repeat(45))
println("📱 TIMELINE DO MUNHU")
println("═".repeat(45))

var postsExibidos = 0
var totalLikes = 0
var totalComentarios = 0

for (postId in 1..numPosts) {
    // Gera dados aleatórios
    val autor = usuarios.random()
    
    // Se há filtro e autor não corresponde, pula
    if (filtroUsuario.isNotEmpty() && autor != filtroUsuario) {
        continue
    }
    
    val tipoPost = tiposPosts.random()
    val likes = (0..500).random()
    
    // Se não atingiu mínimo de likes, pula
    if (likes < minimoLikes) {
        continue
    }
    
    val comentarios = (0..50).random()
    val compartilhamentos = (0..20).random()
    val horasAtras = (1..48).random()
    
    // Atualiza contadores
    postsExibidos++
    totalLikes += likes
    totalComentarios += comentarios
    
    // Exibe o post
    println("\n┌─────────────────────────────────────────")
    println("│ @$autor · ${horasAtras}h atrás")
    println("│ $tipoPost")
    println("│")
    
    // Gera conteúdo baseado no tipo
    when (tipoPost) {
        "compartilhou uma foto" -> {
            println("│ 🖼️ [FOTO: Paisagem de Moçambique 🇲🇿]")
        }
        "publicou um pensamento" -> {
            val pensamentos = listOf(
                "A vida é uma jornada incrível!",
                "Desenvolvendo o Munhu com muito amor ❤️",
                "Moçambique tem tanto potencial!",
                "Tecnologia transforma vidas 💻",
                "Cada dia é uma nova oportunidade"
            )
            println("│ \"${pensamentos.random()}\"")
        }
        "fez check-in" -> {
            val locais = listOf("Maputo", "Beira", "Nampula", "Pemba", "Quelimane")
            println("│ 📍 ${locais.random()}, Moçambique")
        }
        "atualizou status" -> {
            println("│ 💭 Está se sentindo motivado")
        }
        "compartilhou um link" -> {
            println("│ 🔗 munhu.co.mz/artigo-${postId}")
        }
    }
    
    println("│")
    
    // Reações
    val numReacoes = (2..5).random()
    val reacoesPost = mutableListOf<String>()
    repeat(numReacoes) {
        reacoesPost.add(reacoes.random())
    }
    
    println("│ ${reacoesPost.joinToString(" ")} $likes")
    println("│ 💬 $comentarios   🔄 $compartilhamentos")
    
    // Alguns comentários aleatórios
    if (comentarios > 0) {
        val numComentariosExibir = minOf(2, comentarios)
        println("│")
        
        for (i in 1..numComentariosExibir) {
            val comentador = usuarios.filter { it != autor }.random()
            val comentariosExemplo = listOf(
                "Adorei! 👏",
                "Muito bom!",
                "Parabéns!",
                "Concordo totalmente",
                "Top demais! 🔥"
            )
            println("│   @$comentador: ${comentariosExemplo.random()}")
        }
        
        if (comentarios > 2) {
            println("│   Ver mais ${comentarios - 2} comentários...")
        }
    }
    
    println("└─────────────────────────────────────────")
    
    // A cada 5 posts, mostra um anúncio
    if (postsExibidos % 5 == 0) {
        println("\n┌─────────────────────────────────────────")
        println("│ 📢 PATROCINADO")
        println("│")
        println("│ 🎯 Impulsione seu perfil no Munhu")
        println("│ Alcance milhares de pessoas!")
        println("│")
        println("│ [Saiba Mais]")
        println("└─────────────────────────────────────────")
    }
    
    // Limite de exibição
    if (postsExibidos >= 20) {
        println("\n⏸️ Primeiros 20 posts carregados.")
        println("🔄 Role para baixo para carregar mais...")
        break
    }
}

// ========== ESTATÍSTICAS FINAIS ==========

println("\n" + "═".repeat(45))
println("📊 ESTATÍSTICAS DA TIMELINE")
println("═".repeat(45))

if (postsExibidos > 0) {
    val mediaLikes = totalLikes / postsExibidos
    val mediaComentarios = totalComentarios / postsExibidos
    
    println("Posts exibidos: $postsExibidos")
    println("Total de likes: $totalLikes")
    println("Total de comentários: $totalComentarios")
    println("Média de likes/post: $mediaLikes")
    println("Média de comentários/post: $mediaComentarios")
    
    if (filtroUsuario.isNotEmpty()) {
        println("Filtrado por: @$filtroUsuario")
    }
    
    if (minimoLikes > 0) {
        println("Mínimo de likes: $minimoLikes")
    }
} else {
    println("❌ Nenhum post corresponde aos filtros!")
}

println("═".repeat(45))

// ========== RECOMENDAÇÕES ==========

println("\n💡 USUÁRIOS SUGERIDOS PARA SEGUIR:")

var sugestoesExibidas = 0

for (usuario in usuarios) {
    // Não sugere o usuário filtrado
    if (usuario == filtroUsuario) {
        continue
    }
    
    sugestoesExibidas++
    val seguidores = (100..10000).random()
    
    println("   @$usuario ($seguidores seguidores) [Seguir]")
    
    // Limita sugestões
    if (sugestoesExibidas >= 3) {
        break
    }
}

println("\n👋 Fim da timeline!")
