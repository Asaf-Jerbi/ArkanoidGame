# 312491715
# jerbias2
compile: bin
	find src -name "*.java" > sources.txt
	javac -cp biuoop-1.4.jar:src -d bin @sources.txt
run:
	java -cp biuoop-1.4.jar:bin:resources game/Ass7Game 
jar:
	jar cfm ass7game.jar Manifest.mf -C bin . -C resources .
bin:
	mkdir bin
