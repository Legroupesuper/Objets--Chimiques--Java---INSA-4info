type token =
  | HD
  | QD
  | ED
  | W
  | H
  | Q
  | E
  | S
  | EOL
  | EOF

val startparse :
  (Lexing.lexbuf  -> token) -> Lexing.lexbuf -> attributes list
