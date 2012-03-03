run_test() {
	echo Running Test Cases in "$1"
	chdir $1
	for d in *
	do
		if [ -d ./$d ]; then
			echo Running Test Case For: $d
			x=1
			while [ -f ./$d/Input/$x.in ]; do
				echo Running Test: $x
				java -jar ../dvdrental.jar ../dvds.txt ./$d/Actual-Output/$x.tf < ./$d/Input/$x.in > ./$d/Actual-Output/$x.out
				x=$(( $x + 1 ))
			done
		fi 
	done
}

run_test Standard-Test-Set
