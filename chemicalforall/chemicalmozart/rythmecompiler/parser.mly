%{
		#use "definitions.ml";;
%}
%token HD 
%token QD 
%token ED
%token W 
%token H 
%token Q 
%token E 
%token S
%token EOL
%token EOF

%start startparse             /* the entry point */
%type <attributes list> startparse
%%
startparse:
	expressions EOF {$1}
	;
	
expressions:
	expr EOL expressions	{(generateAttributes $1)::$3}
	| expr EOL				{[generateAttributes $1]}
	;

expr:
	final			{ [$1] }
	| final expr	{ List.append $2 [$1] }
	;

final:
	HD		{createNewTime 12 "HD"}
	| QD	{createNewTime 6 "QD"}
	| ED	{createNewTime 3 "ED"}
	| W		{createNewTime 16 "W"}
	| H		{createNewTime 8 "H"}
	| Q		{createNewTime 4 "Q"}
	| E		{createNewTime 2 "E"}
	| S		{createNewTime 1 "S"}
	;
