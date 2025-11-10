println("═══════════════════════════════════════")
println("    MUNHU - ITERANDO COLEÇÕES")
println("═══════════════════════════════════════")

// ========== FOR EM LISTA ==========

println("\n=== 👥 LISTA DE USUÁRIOS ===")

val usuarios = listOf("francisco", "ana", "carlos", "beatriz", "daniel")

for (usuario in usuarios) {
    println("@$usuario")
}

// ========== FOR COM ÍNDICE ==========

println("\n=== 🏆 RANKING DE USUÁRIOS ===")

for (index in usuarios.indices) {
    val posicao = index + 1
    val usuario = usuarios[index]
    
    val emoji = when (posicao) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "  "
    }
    
    println("$emoji #$posicao - @$usuario")
}

// ========== FOR COM withIndex() ==========

println("\n=== 📊 ESTATÍSTICAS DE POSTS ===")

val posts = listOf(
    "Bom dia Moçambique! 🇲🇿",
    "Desenvolvendo o Munhu...",
    "Kotlin é incrível!",
    "Quem mais está estudando?",
    "210 dias de código!"
)

for ((indice, conteudo) in posts.withIndex()) {
    val numeroPost = indice + 1
    val tamanho = conteudo.length
    println("Post #$numeroPost ($tamanho chars): $conteudo")
}

// ========== FOR EM STRING (caractere por caractere) ==========

println("\n=== 🔍 ANALISANDO USERNAME ===")

val username = "francisco"

println("Username: @$username")
println("Caracteres:")

for (char in username) {
    println("  - '$char' (ASCII: ${char.code})")
}

// ========== CONTANDO VOGAIS ==========

println("\n=== 🎵 CONTADOR DE VOGAIS ===")

val texto = "Munhu - Rede Social Moçambicana"
val vogais = "aeiouAEIOU"
var contadorVogais = 0

println("Texto: $texto")

for (char in texto) {
    if (char in vogais) {
        contadorVogais++
    }
}

println("Total de vogais: $contadorVogais")

// ========== APLICAÇÃO: MODERAÇÃO EM LOTE ==========

println("\n=== 🛡️ MODERAÇÃO AUTOMÁTICA ===")

val postsPendentes = listOf(
    "Conteúdo normal",
    "SPAM SPAM SPAM",
    "Post interessante",
    "Golpe! Clique aqui!",
    "Ótima discussão"
)

val palavrasProibidas = listOf("spam", "golpe")

for ((index, post) in postsPendentes.withIndex()) {
    val postLower = post.lowercase()
    var aprovado = true
    
    for (palavraProibida in palavrasProibidas) {
        if (postLower.contains(palavraProibida)) {
            aprovado = false
            break
        }
    }
    
    val status = if (aprovado) "✅ APROVADO" else "❌ BLOQUEADO"
    println("Post ${index + 1}: $status - \"$post\"")
}

// ========== APLICAÇÃO: FEED DE NOTÍCIAS ==========

println("\n=== 📰 FEED DO MUNHU ===")

val feed = listOf(
    Pair("francisco", "Bom dia! 🌅"),
    Pair("ana", "Alguém sabe Kotlin?"),
    Pair("carlos", "Moçambique 🇲🇿❤️"),
    Pair("beatriz", "Novo projeto em andamento..."),
    Pair("daniel", "Pizza ou hambúrguer?")
)

for ((index, postData) in feed.withIndex()) {
    val (autor, conteudo) = postData
    println("\n┌─────────────────────────")
    println("│ @$autor")
    println("│ $conteudo")
    println("│ ❤️ ${index * 12} 💬 ${index * 3} 🔄 ${index * 2}")
    println("└─────────────────────────")
}
