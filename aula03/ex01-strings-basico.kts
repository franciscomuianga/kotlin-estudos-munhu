// ========== CRIANDO STRINGS ==========

val nome = "Francisco"
val sobrenome = "Moçambique"
val empresa = "THE FRA LABS"

println("=== STRINGS BÁSICAS ===")
println("Nome: $nome")
println("Sobrenome: $sobrenome")
println("Empresa: $empresa")

// ========== CONCATENAÇÃO ==========

// Método 1: Operador +
val nomeCompleto1 = nome + " " + sobrenome
println("\nConcatenação com +: $nomeCompleto1")

// Método 2: Interpolação (MELHOR!)
val nomeCompleto2 = "$nome $sobrenome"
println("Interpolação: $nomeCompleto2")

// Método 3: Template com expressões
val apresentacao = "$nome trabalha na $empresa"
println("Template: $apresentacao")

// ========== STRINGS MULTI-LINHA ==========

val bio = """
    Olá! Sou o $nome
    Tenho 17 anos
    Sou de Moçambique 🇲🇿
    Estou desenvolvendo o Munhu
""".trimIndent()

println("\n=== BIO ===")
println(bio)

// ========== CARACTERES ESPECIAIS ==========

val comQuebra = "Linha 1\nLinha 2\nLinha 3"
val comTab = "Nome:\tFrancisco"
val comAspas = "Ele disse: \"Vou criar o Munhu!\""

println("\n=== CARACTERES ESPECIAIS ===")
println(comQuebra)
println(comTab)
println(comAspas)
