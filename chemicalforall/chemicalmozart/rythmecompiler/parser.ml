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

open Parsing;;
# 2 "parser.mly"
		#use "definitions.ml";;
# 17 "parser.ml"
let yytransl_const = [|
  257 (* HD *);
  258 (* QD *);
  259 (* ED *);
  260 (* W *);
  261 (* H *);
  262 (* Q *);
  263 (* E *);
  264 (* S *);
  265 (* EOL *);
    0 (* EOF *);
    0|]

let yytransl_block = [|
    0|]

let yylhs = "\255\255\
\001\000\002\000\002\000\003\000\003\000\004\000\004\000\004\000\
\004\000\004\000\004\000\004\000\004\000\000\000"

let yylen = "\002\000\
\002\000\003\000\002\000\001\000\002\000\001\000\001\000\001\000\
\001\000\001\000\001\000\001\000\001\000\002\000"

let yydefred = "\000\000\
\000\000\000\000\006\000\007\000\008\000\009\000\010\000\011\000\
\012\000\013\000\014\000\000\000\000\000\000\000\001\000\000\000\
\005\000\002\000"

let yydgoto = "\002\000\
\011\000\012\000\013\000\014\000"

let yysindex = "\007\000\
\255\254\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\009\000\001\255\255\254\000\000\255\254\
\000\000\000\000"

let yyrindex = "\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\002\255\000\000\012\000\
\000\000\000\000"

let yygindex = "\000\000\
\000\000\253\255\001\000\000\000"

let yytablesize = 15
let yytable = "\003\000\
\004\000\005\000\006\000\007\000\008\000\009\000\010\000\001\000\
\015\000\016\000\004\000\003\000\018\000\000\000\017\000"

let yycheck = "\001\001\
\002\001\003\001\004\001\005\001\006\001\007\001\008\001\001\000\
\000\000\009\001\009\001\000\000\016\000\255\255\014\000"

let yynames_const = "\
  HD\000\
  QD\000\
  ED\000\
  W\000\
  H\000\
  Q\000\
  E\000\
  S\000\
  EOL\000\
  EOF\000\
  "

let yynames_block = "\
  "

let yyact = [|
  (fun _ -> failwith "parser")
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : 'expressions) in
    Obj.repr(
# 19 "parser.mly"
                 (_1)
# 95 "parser.ml"
               : attributes list))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'expr) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'expressions) in
    Obj.repr(
# 23 "parser.mly"
                      ((generateAttributes _1)::_3)
# 103 "parser.ml"
               : 'expressions))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : 'expr) in
    Obj.repr(
# 24 "parser.mly"
               ([generateAttributes _1])
# 110 "parser.ml"
               : 'expressions))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : 'final) in
    Obj.repr(
# 28 "parser.mly"
         ( [_1] )
# 117 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : 'final) in
    let _2 = (Parsing.peek_val __caml_parser_env 0 : 'expr) in
    Obj.repr(
# 29 "parser.mly"
              ( List.append _2 [_1] )
# 125 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    Obj.repr(
# 33 "parser.mly"
     (createNewTime 12 "HD")
# 131 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 34 "parser.mly"
      (createNewTime 6 "QD")
# 137 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 35 "parser.mly"
      (createNewTime 3 "ED")
# 143 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 36 "parser.mly"
      (createNewTime 16 "W")
# 149 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 37 "parser.mly"
      (createNewTime 8 "H")
# 155 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 38 "parser.mly"
      (createNewTime 4 "Q")
# 161 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 39 "parser.mly"
      (createNewTime 2 "E")
# 167 "parser.ml"
               : 'final))
; (fun __caml_parser_env ->
    Obj.repr(
# 40 "parser.mly"
      (createNewTime 1 "S")
# 173 "parser.ml"
               : 'final))
(* Entry startparse *)
; (fun __caml_parser_env -> raise (Parsing.YYexit (Parsing.peek_val __caml_parser_env 0)))
|]
let yytables =
  { Parsing.actions=yyact;
    Parsing.transl_const=yytransl_const;
    Parsing.transl_block=yytransl_block;
    Parsing.lhs=yylhs;
    Parsing.len=yylen;
    Parsing.defred=yydefred;
    Parsing.dgoto=yydgoto;
    Parsing.sindex=yysindex;
    Parsing.rindex=yyrindex;
    Parsing.gindex=yygindex;
    Parsing.tablesize=yytablesize;
    Parsing.table=yytable;
    Parsing.check=yycheck;
    Parsing.error_function=parse_error;
    Parsing.names_const=yynames_const;
    Parsing.names_block=yynames_block }
let startparse (lexfun : Lexing.lexbuf -> token) (lexbuf : Lexing.lexbuf) =
   (Parsing.yyparse yytables 1 lexfun lexbuf : attributes list)
