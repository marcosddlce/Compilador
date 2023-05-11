import re

def agregar_errores(errores):
    with open("errores.txt", "w") as archivo_errores:
        for error in errores:
            archivo_errores.write(error)
            archivo_errores.write("\n")

def agregar_token(diccionario, linea, token, columna):
    if linea in diccionario:
        diccionario[linea].append((token, columna))
    else:
        diccionario[linea] = [(token, columna)]

archivo = open("pruebax.txt")

operadores = {
    '==': 'Equality op',
    '<=': 'Less than or equal to',
    '>=': 'Greater than or equal to',
    '!=': 'Not equal op',
    ':=': 'Assignment op',
    '++': 'Increment op',
    '--': 'Decrement op',
    '=': 'Assignment op',
    '+': 'Addition op',
    '-': 'Subtraction op',
    '/': 'Division op',
    '*': 'Multiplication op',
    '<': 'Lessthan op',
    '>': 'Greaterthan op'
}

simbolo_de_puntuacion = {
    ':': 'colon',
    ';': 'semicolon',
    ',': 'comma',
    '(': 'left parenthesis',
    ')': 'right parenthesis',
    '{': 'left brace',
    '}': 'right brace',
    '[': 'left bracket',
    ']': 'right bracket'
}

palabras_reservadas = {
    'main': 'reserved keyword',
    'if': 'reserved keyword',
    'then': 'reserved keyword',
    'else': 'reserved keyword',
    'end': 'reserved keyword',
    'do': 'reserved keyword',
    'while': 'reserved keyword',
    'repeat': 'reserved keyword',
    'until': 'reserved keyword',
    'cin': 'reserved keyword',
    'cout': 'reserved keyword',
    'real': 'reserved keyword',
    'boolean': 'reserved keyword',
    'int': 'reserved keyword',
    'float': 'reserved keyword',
    'char': 'reserved keyword',
    'long': 'reserved keyword'
}

a = archivo.read()

contador = 0
program = a.split("\n")
tokens_lineas = {}
errores = []
comentario_multilinea = False  # Indicador de comentario multilinea

for line in program:
    contador += 1
    print("->Línea No.", contador, "\n", line)

    if comentario_multilinea:
        if '*/' in line:
            comentario_index = line.index('*/')
            line = line[comentario_index + 2:]
            comentario_multilinea = False
        else:
            continue

    if '/*' in line:
        comentario_index = line.index('/*')
        line = line[:comentario_index]
        comentario_multilinea = True
    elif '//' in line:
        comentario_index = line.index('//')
        line = line[:comentario_index]

    tokens = re.findall(r'[a-zA-Z_]\w*|==|<=|>=|!=|!|:=|/\*|\*/|\+\+|--|=|\+|-|/|\*|<|>|;|\(|\)|\{|\}|\[|\]|\d+\.\d+|\d+|[@&]', line)

    for token in tokens:
        columna = line.index(token) + 1
        if token == '*/':
            comentario_multilinea = False
        elif token == '/*':
            comentario_multilinea = True
        elif token in operadores:
            agregar_token(tokens_lineas, contador, operadores[token] + ": " + token, columna)
            print(operadores[token] + ": " + token)
        elif token in simbolo_de_puntuacion:
            agregar_token(tokens_lineas, contador, simbolo_de_puntuacion[token] + ": " + token, columna)
            print(simbolo_de_puntuacion[token] + ": " + token)
        elif re.match(r'^\d+.\d+$', token):
            agregar_token(tokens_lineas, contador, "Literal decimal: " + token, columna)
            print("Numero Real: " + token)
        elif token.isdigit():
            agregar_token(tokens_lineas, contador, "Literal entero: " + token, columna)
            print("Numero Entero: " + token)
        elif token in palabras_reservadas:
            agregar_token(tokens_lineas, contador, palabras_reservadas[token] + ": " + token, columna)
            print(palabras_reservadas[token] + ": " + token)
        elif token.isalpha():
            agregar_token(tokens_lineas, contador, "Identificador: " + token, columna)
            print("Identificador: " + token)
        else:
            errores.append("Token no reconocido: " + token + " (linea: " + str(contador) + ", columna: " + str(columna) + ")")
            #print("Error: Token no reconocido: " + token)

    if comentario_multilinea:
        continue

# Generar archivo de tokens
with open("tokens.txt", "w") as archivo_tokens:
    for linea, tokens in tokens_lineas.items():
        for token, columna in tokens:
            tipo_token = token.split(":")[0].strip()
            archivo_tokens.write(f"{token}\n")

# Generar archivo de errores
agregar_errores(errores)

