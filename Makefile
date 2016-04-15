

#Build the language by compiling all the java files
default:
	mkdir out/production/rune; find . -name \*.java | xargs javac -d out/production/rune

#Remove all class files
clean:
	rm -r out/production/rune

#Make commands for the error examples
cat-error1:
	cat examples/error1.txt | expand -t4; echo \\n
run-error1:
	cd out/production/rune; java compiler.Interpreter ../../../examples/error1.txt;

cat-error2:
	cat examples/error2.txt | expand -t4; echo \\n
run-error2:
	cd out/production/rune; java compiler.Interpreter ../../../examples/error2.txt;

cat-error3:
	cat examples/error3.txt | expand -t4; echo \\n
run-error3:
	cd out/production/rune; java compiler.Interpreter ../../../examples/error3.txt;

#Make commands for demonstration of language
cat-arrays:
	cat examples/arrays.txt | expand -t4; echo \\n
run-arrays:
	cd out/production/rune; java compiler.Interpreter ../../../examples/arrays.txt;

cat-conditionals:
	cat examples/conditionals.txt | expand -t4; echo \\n
run-conditionals:
	cd out/production/rune; java compiler.Interpreter ../../../examples/conditionals.txt;

cat-recursion:
	cat examples/recursion.txt | expand -t4; echo \\n
run-recursion:
	cd out/production/rune; java compiler.Interpreter ../../../examples/recursion.txt;

cat-iteration:
	cat examples/iteration.txt | expand -t4; echo \\n
run-iteration:
	cd out/production/rune; java compiler.Interpreter ../../../examples/iteration.txt;

cat-functions:
	cat examples/functions.txt | expand -t4; echo \\n
run-functions:
	cd out/production/rune; java compiler.Interpreter ../../../examples/functions.txt;

cat-dictionary:
	cat examples/dictionary.txt | expand -t4; echo \\n
run-dictionary:
	cd out/production/rune; java compiler.Interpreter ../../../examples/dictionary.txt;

#Make commands for the circuit problem
cat-problem:
	cat examples/problem.txt | expand -t4; echo \\n
run-problem:
	cd out/production/rune; java compiler.Interpreter ../../../examples/problem.txt;




