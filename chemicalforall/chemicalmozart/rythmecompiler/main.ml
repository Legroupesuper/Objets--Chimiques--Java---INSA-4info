(*
How to use this compiler ?

1- First of all, you just need to open ocaml :
	-> type 'ocaml' in a terminal
2- Then you need to load the main file
	-> type '#use "main.ml";;' in the ocaml interpreter
3-	You can fill a file with the following syntax to describe your rythmes
	file = expressions
	expressions = expression <EOL> expressions | expression <EOL>
	expression = final <ESCAPE> expression | final
	final = W | H | Q | E | S | HD | QD | ED
4- You just need to call the parser
	-> type parse "nameOfYouFile"
	
This will generate as many .java files as the number of rythmes patterns.
avec : 
W = whle
H = half
Q = quater
E = eight
S = sixteen
HD = half dotted
QD = quater dotted
ED = eight dotted

The patterns MUST be 2 or 4 quaters long. No verification is done so if you don't take care, it will just not work. (Not enough time sorry)

*)

open Lexing;;open Parsing;;
open Pervasives;;

#use "definitions.ml";;
#use "parser.ml";;
#use "lexer.ml";;

let computeScale obj = 	if obj#getCurrentTime = 8 then 2
						else 4;;

let generateFile obj = let out = Pervasives.open_out (String.concat "" [obj#getName ; string_of_int (computeScale obj) ; ".java"]) in let str = "package org.chemicalmozart.model.implementations.rythme;\nimport java.util.ArrayList;\nimport java.util.List;\nimport org.chemicalmozart.model.implementations.Note;\nimport org.chemicalmozart.model.implementations.Rythme;\nimport org.chemicalmozart.model.implementations.Note.Type;\n\npublic class " in
	let str = String.concat "" [str ; obj#getName; string_of_int (computeScale obj); "{\n"] in
	let str = String.concat "" [str ; obj#getHeader ; "\n"] in
	let str = String.concat "" [str ; obj#getInner ] in 
	let str = String.concat "" [str ; obj#getFooter ] in Pervasives.output_string out str ; Pervasives.close_out out;;


let rec generateFactory obj = let out = Pervasives.open_out "MozartSolutionFactoryImpl.java" in 
	let str = "/*Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur\n\n\tThis file is part of ChemicalLibSuper.\n\tChemicalLibSuper is free software: you can redistribute it and/or modify\n\tit under the terms of the GNU Lesser General Public License as published by\n\tthe Free Software Foundation, either version 3 of the License, or \n\t(at your option) any later version.\n\tChemicalLibSuper is distributed in the hope that it will be useful,\n\tbut WITHOUT ANY WARRANTY; without even the implied warranty of\n\tMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n\tGNU Lesser General Public License for more details.\n\tYou should have received a copy of the GNU Lesser General Public License\n\talong with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>\n*/\n\tpackage org.chemicalmozart.model.implementations.factory;\nimport fr.insa.rennes.info.chemical.backend.Solution;import org.chemicalmozart.model.implementations.rythme.RythmPattern;\nimport org.chemicalmozart.model.implementations.solutionindentification.RythmePull;\nimport org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;\nimport fr.insa.rennes.info.chemical.backend.Solution;\n\n" in
	let str = String.concat "" [str ; generateImports obj] in
	let str = String.concat "" [str ; generateHeader] in
	let str = String.concat "" [str ; generateBody obj] in
	let str = String.concat "" [str ; generateFooter] in
	Pervasives.output_string out str ; Pervasives.close_out out
and generateImports = function
		a::[]	->	String.concat "" ["import org.chemicalmozart.model.implementations.rythme." ; a#getName; string_of_int (computeScale a) ; ";\n"]
	|	a::b	->	String.concat ""  ["import org.chemicalmozart.model.implementations.rythme." ; a#getName; string_of_int (computeScale a) ; ";\n" ; generateImports b]
and generateHeader = "public class MozartSolutionFactoryImpl implements MozartSolutionFactory{\n\n\tpublic Solution createRythmicPull() {\n\t\tSolution result = new Solution();\n\t\tresult.add(new RythmePull());\n"
and generateBody = function
		a::[]	->	String.concat "" [	"\t\t" ; a#getName; string_of_int (computeScale a) ; " " ; String.lowercase a#getName ; string_of_int (computeScale a) ; " = new " ; a#getName ; string_of_int (computeScale a) ; "();\n";
										"\t\t" ; "result.add(new RythmPattern(" ; String.lowercase a#getName ;string_of_int (computeScale a) ; ".getDuration(), " ; String.lowercase a#getName ; string_of_int (computeScale a) ; ".getListNotes()));\n\n"]
	|	a::b	->	String.concat "" [	"\t\t" ; a#getName; string_of_int (computeScale a) ; " " ; String.lowercase a#getName ; string_of_int (computeScale a) ; " = new " ; a#getName ; string_of_int (computeScale a) ; "();\n";
										"\t\t" ; "result.add(new RythmPattern(" ; String.lowercase a#getName ; string_of_int (computeScale a) ; ".getDuration(), " ; String.lowercase a#getName ; string_of_int (computeScale a) ; ".getListNotes()));\n\n" ; 										generateBody b]
and generateFooter = "\t\treturn result;\n\t}\n}";;


let rec generateFiles = function
		a::[]	-> generateFile a
	| 	a::b	-> generateFile a ; generateFiles b;;

let parse file = 
		let lexbuff = (Lexing.from_channel (open_in file)) in	try		let out = startparse entrypoint lexbuff in generateFiles out ; generateFactory out	with Eof -> exit 0;;