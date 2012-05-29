open String
let parseValue = function
		1	-> "org.chemicalmozart.model.implementations.Rythme.sixteenth"
	|	2	-> "org.chemicalmozart.model.implementations.Rythme.eighth"
	|	3	-> "org.chemicalmozart.model.implementations.Rythme.eightdotted"
	|	4	-> "org.chemicalmozart.model.implementations.Rythme.quater"
	|	6	-> "org.chemicalmozart.model.implementations.Rythme.quaterdotted"
	|	8	-> "org.chemicalmozart.model.implementations.Rythme.half"
	|	12	-> "org.chemicalmozart.model.implementations.Rythme.halfdotted"
	|	16	-> "org.chemicalmozart.model.implementations.Rythme.whole"
	|	a	-> println a; "test";;
	
class attributes = object
	val mutable currentTime = 0
	method addToCurrentTime n = currentTime <- currentTime+n
	method getCurrentTime = currentTime
	
	val mutable name = ""
	method setName n = name <- n
	method getName = name
	method addToName n = name <- concat "" [name ; n]
	
	val mutable header = ""
	method setHeader n = header <- n
	method getHeader = String.concat "" ["\tpublic Rythme getDuration() {\n" ; "\t\treturn " ; parseValue currentTime ; ";\n" ;
											"\t}\n\n" ; "\tpublic List<Note> getListNotes() {\n" ; "\t\tList<Note> l = new ArrayList<Note>();"]
	
	val mutable inner = ""
	method setInner n = inner <- n
	method getInner = inner
	
	val mutable footer = ""
	method setFooter n = footer <- n
	method getFooter = String.concat "" ["\n\t\treturn l;" ; "\n\t}\n}"]
	
	method toString = concat "\n" [header;inner;footer]	
	
	val mutable index = 0
	method increment = index <- index + 1
	method getIndex = index
end;;

class time = object
	val mutable value = 0
	method setValue n = value <- n
	method getValue = value
	
	val mutable name = ""
	method setName n = name <- n
	method getName = name
end;;

let rec print_list = function 
[] -> ()
| e::l -> print_int e ; print_string " " ; print_list l

let rec reverse = function
		[]		-> []
	|	a::b	-> List.append (reverse b) [a];;

let rec generateAttributes l = let revl = (*reverse*) l in
	match revl with
			[]		-> new attributes
		| 	a::b	-> let obj = generateAttributes b in let inner = obj#getInner in
						if obj#getCurrentTime mod 8 = 0 then 
							let line = concat "" ["\t\tNote n";string_of_int obj#getIndex ; " = new Note(" ; string_of_int obj#getIndex ; ", Type.STRONG, " ; 
									parseValue a#getValue ; ");\n" ; "\t\tl.add(n" ; string_of_int obj#getIndex ; ");"] 
							in obj#increment ; obj#addToCurrentTime a#getValue ; obj#addToName a#getName ; obj#setInner (concat "\n" [obj#getInner ; line]); obj
						else
							let line = concat "" ["\t\tNote n";string_of_int obj#getIndex ; " = new Note(" ; string_of_int obj#getIndex ; ", Type.NONE, " ; 
									parseValue a#getValue ; ");\n" ; "\t\tl.add(n" ; string_of_int obj#getIndex ; ");"] 
							in obj#increment ; obj#addToCurrentTime a#getValue ; obj#addToName a#getName ; obj#setInner (concat "\n" [obj#getInner ; line]); obj;;
					
		
		
let createNewTime t n = let o = new time in o#setValue t; o#setName n; o;;
		
		
		
		
		
		