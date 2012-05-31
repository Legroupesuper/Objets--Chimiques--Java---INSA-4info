#!usr/bin/ruby
# encoding: utf-8
#
# For more info about javadoc options :
# http://docs.oracle.com/javase/1.4.2/docs/tooldocs/windows/javadoc.html#javadocflags
#
# Feel free to modify this script according or not to your behaviour !
#
# Important points are located in the options hash


# prints usage for n00bs
def usage()
	print "\nUsage : ruby #{$0} <name of the library>\n"+
			"\t(and that's all for the moment)\n\n"
end




def former_branch
	print 'Defining current branch : '
	branches = `git branch`.split(' ')
	current = branches[branches.find_index("*")+1]
	puts "#{current}..."
	return current
end

def switch_branch(name)
	puts "Switching branch to #{name}..."
	`git checkout #{name}`
end

def generate_javadoc(params, targets)
	puts 'Generating javadoc...'
	execution = "javadoc"
	params.each_pair do |key, value|
		execution += " -#{key}#{value.length==0 ? '' : ' '+value.inspect}"
	end
	execution += " #{targets.join(' ')}"
	puts execution
	`#{execution}`
end

def push_javadoc(branch_name)
	puts 'Cleaning temporary folder...'
	`mv #{Temp_folder}/* ./ && rmdir #{Temp_folder}`
	puts 'Committing changes...'
	`git add -A && git commit -m "Javadoc export n°#{1 + Time.new.to_i % 1000}"`
	`git push origin #{branch_name}`
end

def quick_save
	puts 'Saving uncommitted changes...'
	`git stash`
end

def quick_load
	puts 'Re-loading previous changes...'
	`git stash apply`
end


# ========= execution begins here ! =========

# explain if usage not respected (exit if not enough parameters)
if(ARGV.size != 1) then
	usage
	exit 12 if ARGV.size == 0
end


# name of the "pages" branch (github requires "gh-pages")
Branch_name = 'gh-pages'

Former_branch = former_branch

Library_name = ARGV[0]

Temp_folder = "../foo_#{Time.new.to_i}"

options = {
	'public' => '',	# Only public classes/methods are exported into the doc
	'author' => '',	# Author names are exported
	'doctitle' => "Welcome to the official #{Library_name} documentation",	# Text displayed upon the index page
	'source' => '1.7',	# Source level the doc is generated for (the higher the better ?)
	'sourcepath' => 'chemicalforall/chemicalthread/src/main/java',	# Path of the source files
	'splitindex' => '',	# Seperate things for better lisibility
#	'stylesheetfile' => 'javadoc_stylesheet.css',	# Use custom CSS
	'use' => '',	# Use tags are exported
	'version' => '',	# Version tags are exported
	'windowtitle' => "#{Library_name}",	# Text displayed in the window title, surrounded by parentheses
	'header' => "Official documentation for #{Library_name}",	# Text displayed at the top of the page
	'd' => '.',	# Directory in which you generate the doc (should be left as is)
	'bottom' => '2011-2012 - INSA of Rennes. Licensed under LGPL.',	# Text displayed at the bottom of the page
	'charset' => 'ISO-8859-1',
	'encoding' => 'ISO-8859-1',
	'docencoding' => 'ISO-8859-1'
}

targets = ['fr.insa.rennes.info.chemical.user',
           'fr.insa.rennes.info.chemical.backend']	# The packages of our source files

# real fun stands below
generate_javadoc(options, targets)

quick_save

switch_branch Branch_name

push_javadoc Branch_name

switch_branch Former_branch

quick_load
