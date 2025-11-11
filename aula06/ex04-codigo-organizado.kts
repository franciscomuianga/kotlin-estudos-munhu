println("╔════════════════════════════════════════╗")
println("║   MUNHU - CÓDIGO PROFISSIONAL          ║")
println("╚════════════════════════════════════════╝")

// ========== MÓDULO: VALIDAÇÕES ==========

fun validarEmail(email: String): Boolean {
    return email.contains("@") && 
           email.contains(".") && 
           email.length >= 5
}

fun validarUsername(username: String): Boolean {
    return username.length in 3..15 &&
           username.all { it.isLetterOrDigit() || it == '_' } &&
           username.first().isLetter()
}

fun validarSenha(senha: String): Boolean {
    return senha.length >= 8 &&
           senha.any { it.isDigit() } &&
           senha.any { it.isLetter() }
}

fun validarIdade(idade: Int): Boolean {
    return idade in 13..120
}

// ========== MÓDULO: FORMATAÇÃO ==========

fun formatarUsername(nome: String): String {
    return nome
        .lowercase()
        .replace(" ", "_")
        .filter { it.isLetterOrDigit() || it == '_' }
}

fun formatarNumero(numero: Int): String {
    return when {
        numero >= 1_000_000 -> "${numero / 1_000_000}M"
        numero >= 1_000 -> "${numero / 1_000}K"
        else -> numero.toString()
    }
}

fun formatarTempo(minutos: Int): String {
    return when {
        minutos < 60 -> "${minutos}min"
        minutos < 1440 -> "${minutos / 60}h"
        else -> "${minutos / 1440}d"
    }
}

// ========== MÓDULO: CÁLCULOS ==========

fun calcularEngajamento(likes: Int, comentarios: Int, compartilhamentos: Int): Int {
    return likes + (comentarios * 2) + (compartilhamentos * 3)
}

fun calcularTaxaEngajamento(interacoes: Int, seguidores: Int): Double {
    if (seguidores == 0) return 0.0
    return (interacoes.toDouble() / seguidores * 100)
}

fun calcularAlcance(seguidores: Int, taxaEngajamento: Double): Int {
    return (seguidores * (taxaEngajamento / 100)).toInt()
}

// ========== MÓDULO: CLASSIFICAÇÕES ==========

fun classificarUsuario(seguidores: Int): String {
    return when {
        seguidores >= 100_000 -> "👑 Mega Influencer"
        seguidores >= 10_000 -> "⭐ Influencer"
        seguidores >= 1_000 -> "🔥 Popular"
        seguidores >= 100 -> "📈 Crescendo"
        else -> "🌱 Iniciante"
    }
}

fun classificarPost(likes: Int): String {
    return when {
        likes >= 10_000 -> "🔥 VIRAL"
        likes >= 1_000 -> "⭐ TRENDING"
        likes >= 500 -> "💫 POPULAR"
        likes >= 100 -> "✅ BOM"
        else -> "📊 NORMAL"
    }
}

// ========== MÓDULO: GERAÇÃO ==========

fun gerarId(prefixo: String = "MUNHU"): String {
    val timestamp = System.currentTimeMillis()
    val random = (1000..9999).random()
    return "${prefixo}_${timestamp}_$random"
}

fun gerarBio(nome: String, cidade: String, profissao: String): String {
    return "$profissao de $cidade 🇲🇿 | $nome"
}

// ========== TESTANDO TUDO ==========

println("\n═══════════════════════════════════════")
println("  TESTANDO SISTEMA MUNHU")
println("═══════════════════════════════════════")

// Dados de teste
val nomeCompleto = "Francisco Silva"
val email = "francisco@munhu.co.mz"
val senha = "munhu2025"
val idade = 17
val seguidores = 1200
val likes = 350
val comentarios = 45
val compartilhamentos = 20

println("\n📝 CADASTRO:")
println("Nome: $nomeCompleto")
println("Email: $email ${if (validarEmail(email)) "✅" else "❌"}")

val username = formatarUsername(nomeCompleto)
println("Username: @$username ${if (validarUsername(username)) "✅" else "❌"}")

println("Senha: ${"*".repeat(senha.length)} ${if (validarSenha(senha)) "✅" else "❌"}")
println("Idade: $idade ${if (validarIdade(idade)) "✅" else "❌"}")

println("\n👤 PERFIL:")
println("@$username")
println("Seguidores: ${formatarNumero(seguidores)}")
println("Classificação: ${classificarUsuario(seguidores)}")
println("ID: ${gerarId()}")

val bio = gerarBio(nomeCompleto, "Maputo", "Desenvolvedor")
println("Bio: $bio")

println("\n📊 ESTATÍSTICAS DE POST:")
val engajamento = calcularEngajamento(likes, comentarios, compartilhamentos)
val taxaEng = calcularTaxaEngajamento(engajamento, seguidores)
val alcance = calcularAlcance(seguidores, taxaEng)

println("Likes: ${formatarNumero(likes)}")
println("Comentários: ${formatarNumero(comentarios)}")
println("Compartilhamentos: ${formatarNumero(compartilhamentos)}")
println("Engajamento total: ${formatarNumero(engajamento)}")
println("Taxa de engajamento: ${"%.2f".format(taxaEng)}%")
println("Alcance estimado: ${formatarNumero(alcance)} pessoas")
println("Classificação: ${classificarPost(likes)}")

println("\n⏰ ATIVIDADE RECENTE:")
println("Último post: ${formatarTempo(45)} atrás")
println("Último login: ${formatarTempo(120)} atrás")
println("Membro desde: ${formatarTempo(10080)} atrás")

println("\n═══════════════════════════════════════")
println("✅ Todos os módulos funcionando!")
println("═══════════════════════════════════════")
