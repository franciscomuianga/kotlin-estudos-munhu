println("═══════════════════════════════════════")
println("    MUNHU - SISTEMA DE MODERAÇÃO")
println("═══════════════════════════════════════")

print("\n✍️ Digite o conteúdo do post: ")
val post = readln().lowercase()

print("👤 Tipo de usuário (novo/regular/verificado): ")
val tipoUsuario = readln().lowercase()

// ========== PALAVRAS PROIBIDAS ==========

val palavrasProibidas = listOf("spam", "golpe", "fraude", "hack")

var contemPalavraProibida = false
for (palavra in palavrasProibidas) {
    if (post.contains(palavra)) {
        contemPalavraProibida = true
        break
    }
}

// ========== ANÁLISE DO POST ==========

val comprimento = post.length
val temLink = post.contains("http") || post.contains("www.")
val temMuitasMaiusculas = post.count { it.isUpperCase() } > post.length * 0.5
val somenteEmojis = post.all { !it.isLetterOrDigit() }

println("\n🔍 ANÁLISE DO POST:")
println("   Tamanho: $comprimento caracteres")
println("   Contém link: ${if (temLink) "SIM" else "NÃO"}")
println("   Muitas maiúsculas: ${if (temMuitasMaiusculas) "SIM" else "NÃO"}")
println("   Palavra proibida: ${if (contemPalavraProibida) "SIM" else "NÃO"}")
println("   Tipo usuário: $tipoUsuario")

// ========== DECISÃO DE MODERAÇÃO ==========

println("\n⚖️ DECISÃO:")

val decisao = when {
    // Bloqueios automáticos
    contemPalavraProibida -> "BLOQUEADO - Conteúdo proibido"
    somenteEmojis -> "BLOQUEADO - Spam de emojis"
    comprimento < 3 -> "BLOQUEADO - Post muito curto"
    comprimento > 500 -> "BLOQUEADO - Post muito longo"
    
    // Revisão manual
    temLink && tipoUsuario == "novo" -> "REVISÃO - Novo usuário com link"
    temMuitasMaiusculas -> "REVISÃO - Possível spam (maiúsculas)"
    
    // Aprovações automáticas
    tipoUsuario == "verificado" -> "APROVADO ✓ - Usuário verificado"
    tipoUsuario == "regular" && !temLink -> "APROVADO ✓"
    tipoUsuario == "novo" && !temLink && comprimento in 10..280 -> "APROVADO ✓"
    
    else -> "REVISÃO - Análise manual necessária"
}

println(decisao)

// ========== AÇÕES BASEADAS NA DECISÃO ==========

if (decisao.startsWith("APROVADO")) {
    println("\n✅ POST PUBLICADO!")
    println("🔔 Notificando seus seguidores...")
} else if (decisao.startsWith("REVISÃO")) {
    println("\n⏳ POST EM REVISÃO")
    println("📋 Será analisado por moderador em até 2h")
} else {
    println("\n❌ POST BLOQUEADO!")
    println("⚠️ Motivo: ${decisao.substringAfter(" - ")}")
    
    // Ação disciplinar
    when (tipoUsuario) {
        "novo" -> println("📝 Aviso registrado na conta")
        "regular" -> println("⚠️ Primeira advertência")
        "verificado" -> println("🔍 Equipe de suporte notificada")
    }
}

// ========== SCORE DE CONFIANÇA ==========

println("\n📊 SCORE DE CONFIANÇA:")

val scoreUsuario = when (tipoUsuario) {
    "novo" -> 30
    "regular" -> 70
    "verificado" -> 95
    else -> 0
}

val penalidades = when {
    contemPalavraProibida -> -50
    temMuitasMaiusculas -> -20
    temLink && tipoUsuario == "novo" -> -30
    else -> 0
}

val scoreFinal = (scoreUsuario + penalidades).coerceIn(0, 100)

println("   Score base: $scoreUsuario")
println("   Penalidades: $penalidades")
println("   Score final: $scoreFinal/100")

when {
    scoreFinal >= 80 -> println("   Status: ✅ Confiável")
    scoreFinal >= 50 -> println("   Status: ⚠️ Atenção")
    else -> println("   Status: ❌ Suspeito")
}
