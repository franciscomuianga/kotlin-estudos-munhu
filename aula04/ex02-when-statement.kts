println("═══════════════════════════════════════")
println("    MUNHU - SISTEMA DE CLASSIFICAÇÃO")
println("═══════════════════════════════════════")

print("\n📊 Digite seu número de seguidores: ")
val seguidores = readln().toInt()

// ========== WHEN BÁSICO (como switch) ==========

println("\n=== CLASSIFICAÇÃO EXATA ===")

when (seguidores) {
    0 -> println("🆕 Conta nova - comece a seguir pessoas!")
    10 -> println("📈 Primeira meta alcançada!")
    100 -> println("💯 Centenário!")
    1000 -> println("🎉 Mil seguidores - você é um influencer!")
    else -> println("📊 $seguidores seguidores")
}

// ========== WHEN COM RANGES ==========

println("\n=== CLASSIFICAÇÃO POR FAIXA ===")

val categoria = when (seguidores) {
    0 -> "🥚 Iniciante"
    in 1..99 -> "🌱 Crescendo"
    in 100..999 -> "📈 Em ascensão"
    in 1000..9999 -> "🔥 Influencer"
    in 10000..99999 -> "⭐ Celebridade Local"
    in 100000..999999 -> "🌟 Celebridade Nacional"
    else -> "👑 Mega Influencer"
}

println("Categoria: $categoria")

// ========== WHEN COM MÚLTIPLAS CONDIÇÕES ==========

println("\n=== BENEFÍCIOS ===")

when (seguidores) {
    in 0..99 -> {
        println("🎁 Benefícios:")
        println("   • Perfil básico")
    }
    in 100..999 -> {
        println("🎁 Benefícios:")
        println("   • Perfil básico")
        println("   • Badge 'Crescendo'")
    }
    in 1000..9999 -> {
        println("🎁 Benefícios:")
        println("   • Perfil verificado ✓")
        println("   • Badge 'Influencer'")
        println("   • Analytics avançado")
    }
    else -> {
        println("🎁 Benefícios VIP:")
        println("   • Tudo acima +")
        println("   • Suporte prioritário")
        println("   • Monetização")
        println("   • Eventos exclusivos")
    }
}

// ========== WHEN SEM ARGUMENTO (mais flexível) ==========

println("\n=== RECOMENDAÇÕES PERSONALIZADAS ===")

val postsPublicados = 45
val taxaEngajamento = 8.5

when {
    seguidores < 100 && postsPublicados < 10 -> {
        println("💡 Dica: Poste mais conteúdo para crescer!")
    }
    seguidores < 100 && postsPublicados >= 10 -> {
        println("💡 Dica: Interaja com outros usuários!")
    }
    seguidores >= 100 && taxaEngajamento < 5 -> {
        println("💡 Dica: Melhore a qualidade dos posts!")
    }
    seguidores >= 1000 && taxaEngajamento >= 5 -> {
        println("🎉 Excelente! Continue assim!")
    }
    else -> {
        println("📊 Perfil estável")
    }
}

// ========== WHEN COMO EXPRESSÃO ==========

val badge = when (seguidores) {
    in 0..99 -> "🆕"
    in 100..999 -> "🌱"
    in 1000..9999 -> "🔥"
    in 10000..99999 -> "⭐"
    else -> "👑"
}

println("\n🏅 Seu badge: $badge")

// ========== VERIFICAÇÃO DE TIPO COM WHEN ==========

println("\n=== TESTE COM MÚLTIPLOS VALORES ===")

val valores = listOf(100, 500, 1500, 25000, 500000)

for (valor in valores) {
    val nivel = when {
        valor < 100 -> "Bronze"
        valor < 1000 -> "Prata"
        valor < 10000 -> "Ouro"
        valor < 100000 -> "Platina"
        else -> "Diamante"
    }
    println("$valor seguidores = Nível $nivel")
}
