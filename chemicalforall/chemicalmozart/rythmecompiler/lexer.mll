{
	exception Eof
}

rule entrypoint = parse
	[' ' '\t']     { entrypoint lexbuf }     (* skip blanks *)
	| ['\n' ]        { EOL }
	| "HD"           { HD }
	| "QD"           { QD }
	| "ED"           { ED }
	| 'W'            { W }
	| 'H'            { H }
	| 'Q'            { Q }
	| 'E'            { E }
	| 'S'            { S }
	| eof            { EOF }