#!/usr/bin/perl -w

use strict;

if($ARGV[0] eq "") {
	$ARGV[0] = "ChLOE";
}

my $license_text = "/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ".$ARGV[0].".

    ".$ARGV[0]." is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ".$ARGV[0]." is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ".$ARGV[0].".  If not, see <http://www.gnu.org/licenses/>
*/";

my $source_files = `find -name "*.java"`;

my @file_list = split(/\s/, $source_files);


foreach my $file (@file_list) {
	
	open(HANDLE_IN, "$file") or die("Erreur lors de l'ouverture de $file");
	open(HANDLE_TMP, ">$file.tmp")  or die("Erreur lors de l'ouverture de $file.tmp");
	
	
	#Print the license in the file
	print HANDLE_TMP $license_text;
	
	
	my $nrLine = 0;
	my $copyLines = 0;
	while(my $line = <HANDLE_IN>) {
		chomp($line);
		if($copyLines == 0) {
			if($nrLine == 0 and $line =~ /^\/\* /gi) {
				$copyLines = 0;
			} elsif($nrLine > 0 and $copyLines == 0 and not ($line =~ /\*\/$/i)) {
				$copyLines = 0;
			} elsif($nrLine > 0 and $copyLines == 0 and ($line =~ /\*\/$/i)) {
				$copyLines = 1;
			} else {
				$copyLines = 1;
				print HANDLE_TMP "\n".$line;
			}
		} else {
			print HANDLE_TMP "\n".$line;
		}
		
		$nrLine++;
	}		

	close(HANDLE_TMP);
	close(HANDLE_IN);
	
	`mv $file.tmp $file`;
}
