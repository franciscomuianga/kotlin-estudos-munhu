println("╔════════════════════════════════════════╗")
println("║    MUNHU - CLASSIFICADOR DE POSTS      ║")
println("╚════════════════════════════════════════╝")

print("\n📝 Conteúdo do post: ")
val conteudo = readln()

print("👤 Seguidores do autor: ")
val seguidores = readln().toInt()

print("❤️ Número de likes: ")
val likes = readln().toInt()

print("💬 Número de comentários: ")
val comentarios = readln().toInt()

print("🔄 Número de compartilhamentos: ")
val compartilhamentos = readln().toInt()

print("⏰ Horas desde publicação: ")
val horasPublicado = readln().toInt()

// ========== CÁLCULOS ==========

val totalInteracoes = likes + comentarios + compartilhamentos
val taxaEngajamento = if (seguidores > 0) {
    (totalInteracoes.toDouble() / seguidores * 100)
} else {
    0.0
}

val interacoesPorHora = if (horasPublicado > 0) {
    totalInteracoes / horasPublicado
} else {
    totalInteracoes
}

println("\n📊 MÉTRICAS:")
println("   Total de interações: $totalInteracoes")
println("   Taxa de engajamento: ${"%.2f".format(taxaEngajamento)}%")
println("   Interações/hora: $interacoesPorHora")

// ========== CLASSIFICAÇÃO DO POST ==========

println("\n🏆 CLASSIFICAÇÃO:")

val classificacao = when {
    // Viral
    totalInteracoes >= 10000 && taxaEngajamento >= 10 -> {
        "🔥 VIRAL"
    }
    
    // Trending
    totalInteracoes >= 1000 && interacoesPorHora >= 100 -> {
        "📈 TRENDING"
    }
    
    // Popular
    totalInteracoes >= 500 && taxaEngajamento >= 5 -> {
        "⭐ POPULAR"
    }
    
    // Bom engajamento
    taxaEngajamento >= 3 -> {
        "✅ BOM ENGAJAMENTO"
    }
    
    // Engajamento médio
    taxaEngajamento >= 1 -> {
        "📊 ENGAJAMENTO MÉDIO"
    }
    
    // Baixo engajamento
    else -> {
        "😴 BAIXO ENGAJAMENTO"
    }
}

println("   $classificacao")

// ========== ANÁLISE DE QUALIDADE ==========

val tamanhoint = conteudo.length
val temHashtag = conteudo.contains("#")
val temMencao = conteudo.contains("@")
val temPergunta = conteudo.contains("?")

val pontuacaoQualidade = when {
    tamanhoint in 50..280 -> 3
    tamanhoint in 20..49 -> 2
    tamanhoint in 281..500 -> 2
    else -> 1
} + (if (temHashtag) 1 else 0) +
    (if (temMencao) 1 else 0) +
    (if (temPergunta) 1 else 0)

println("\n📋 QUALIDADE DO CONTEÚDO:")
println("   Tamanho: $tamanho caracteres")
println("   Hashtags: ${if (temHashtag) "✓" else "✗"}")
println("   Menções: ${if (temMencao) "✓" else "✗"}")
println("   Interativo: ${if (temPergunta) "✓" else "✗"}")
println("   Score: $pontuacaoQualidade/6")

// ========== RECOMENDAÇÕES ==========

println("\n💡 RECOMENDAÇÕES:")

when (classificacao) {
    "🔥 VIRAL" -> {
        println("   • POST EXCEPCIONAL!")
        println("   • Continue criando conteúdo assim")
        println("   • Considere fazer série sobre o tema")
    }
    
    "📈 TRENDING" -> {
        println("   • Post com alto potencial!")
        println("   • Responda aos comentários rapidamente")
        println("   • Compartilhe em outras plataformas")
    }
    
    "⭐ POPULAR" -> {
        println("   • Bom trabalho!")
        println("   • Interaja com quem comentou")
        println("   • Poste mais neste horário")
    }
    
    "✅ BOM ENGAJAMENTO" -> {
        println("   • Performance acima da média")
        println("   • Teste adicionar mais hashtags")
        println("   • Tente fazer perguntas aos seguidores")
    }
    
    "📊 ENGAJAMENTO MÉDIO" -> {
        println("   • Post normal")
        println("   • Tente conteúdo mais visual")
        println("   • Use hashtags relevantes")
    }
    
    else -> {
        println("   • Revise o conteúdo")
        println("   • Poste em horários de pico")
        println("   • Interaja mais com a comunidade")
    }
}

// ========== PREVISÃO ==========

println("\n🔮 PREVISÃO (próximas 24h):")

val potencialViews = when {
    classificacao.contains("VIRAL") -> totalInteracoes * 10
    classificacao.contains("TRENDING") -> totalInteracoes * 5
    classificacao.contains("POPULAR") -> totalInteracoes * 2
    else -> (totalInteracoes * 1.2).toInt()
}

println("   Visualizações estimadas: ~$potencialViews")

val devePromover = totalInteracoes >= 100 && taxaEngajamento >= 5

if (devePromover) {
    println("\n💰 SUGESTÃO:")
    println("   Este post tem alto potencial!")
    println("   Considere promovê-lo para alcance maior.")
}

println("\n╚════════════════════════════════════════╝")
