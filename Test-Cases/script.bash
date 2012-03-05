#!/bin/bash
#
# Name: DVD-Rental-System 
#
# Purpose:
#    Performs all the test-cases for the front end part of DVD Rental 
#    System
#
# Notes:
# 1) To invoke this shell script and redirect standard output and
#    standard error to a file (such as Test-Output.txt) do the
#    following (the -s flag is "silent mode" to avoid prompts to the
#    user):
#
#    ./test-bucket-1  -s  2>&1  | tee test-bucket-1.out
#
# Return codes:
#  0 = All commands were successful
#  1 = At least one command failed, see the output file and search
#      for the keyword "ERROR".
#
###############################################################################

# ----------------------------
# Subroutine to echo the usage
# ----------------------------

usage()
{
 echo "USAGE: $CALLER [-h] [-s]"
 echo "WHERE: -h = help       "
 echo "       -a = test admin functionalities"
 echo "       -s = test standard functionalities"
 echo "       -b = test both the functionalities"

 echo "PREREQUISITES:"
 echo "* None."
 echo "$CALLER: exiting now with rc=1."
 exit 1
}

# ----------------------------------
# Subroutine to terminate abnormally
# ----------------------------------
terminate()
{
 echo "The execution of $CALLER was not successful."
 echo "$CALLER terminated, exiting now with rc=1."
 dateTest=`date`
 echo "End of testing at: $dateTest"
 echo ""
 exit 1
}

# ---------------------------------------------------------------------------
# The commands are called in a subroutine so that return code can be
# checked for possible errors.
# ---------------------------------------------------------------------------

Test()
{
 #----------------------------------------------------------------------------
 echo `cat ./$1/Info/$2.info`
 echo ""
 #------------------------------------------------------------------------------

 #-------------------------------------------------------------------------------
 echo "Running java command..."
 echo "java -jar ../dvdrental.jar \"../dvds.txt\" \"./$1/Actual-Output/$2.tf\""
 java -jar ../dvdrental.jar \"../dvds.txt\" \"./$1/Actual-Output/$2.tf\" < ./$1/Input/$2.in > ./$1/Actual-Output/$2.out 2>&1
 echo ""
 #-------------------------------------------------------------------------------

 NO_ERROR=0
 #-------------------------------------------------------------------------------
 echo "Checking results..."
 result_output=$(diff ./$1/Actual-Output/$2.out ./$1/Output/$2.out 2>&1)		
 if [ -n "$result_otuput" ]; then
  echo "*** ERROR found while comparing expected and actual outputs. ***"
  echo "Difference between actual output and expected output is below:"
  echo $result_output
  NO_ERROR=1
  echo ""
 fi
#---------------------------------------------------------------------------------

#---------------------------------------------------------------------------------
 if [ -e ./$1/Actual-Output/$2.tf ]; then
  result_transc=$(diff ./$1/Actual-Output/$2.tf ./$1/Output-TF/$2.tf 2>&1)
  if [ -n "$result_transc" ]; then
   echo "*** ERROR found while comparing expected and actual transaction files. ***"
   echo "Difference between actual transaction file and expected transaction file is below:"
   echo $result_transc
   NO_ERROR=1
  fi
  echo ""
 fi
 #-------------------------------------------------------------------------------
 
 if [ "$NO_ERROR" -eq 0 ]; then
  echo "*** SUCCESS. No error(s) were found in execution of the test."
  echo ""
  echo "Transaction File: "
  echo `cat ./$1/Actual-Output/$2.tf`
  echo ""
  echo "Output: "
  echo `cat ./$1/Actual-Output/$2.out`
  echo ""
 fi
}

run_test() {
 cd $1
 echo ""
 echo ""
 for d in *; do
  if [ -d ./$d ]; then
   echo ""
   x=1;
   while [ -f ./$d/Input/$x.in ]; do
    f
    echo "*-----------------------------------*"
    echo "Test Identity: ${1:0:1}-${d^^}-$x"
    echo "Test Set: $1"
    echo "Functionality: $d"
    echo "Test Number: $x"
    echo "*-----------------------------------*"
    Test $d $x
    echo ""
    x=$(( $x + 1 ))
   done
  fi
 done
}

################################################################################

# --------------------------------------------
# Main routine for performing the test bucket
# --------------------------------------------

CALLER=`basename $0`                    # The Caller name
SILENT="no"	                        # User wants prompts

# ----------------------------------
# Handle keyword parameters (flags).
# ----------------------------------
TEMP=`getopt hbas $*`
if [ $? != 0 ]
then
 echo "$CALLER: Unknown flag(s)"
 usage
fi

eval set -- "$TEMP"

TEST=2;
while true                   
 do
  case "$1" in
   -h) usage "HELP";            shift;; # Help requested
   -a) TEST=0;            shift;;
   -s) TEST=1;            shift;;
   -b) TEST=2;            shift;;
   --) shift ; break ;; 
   *) echo "Internal error!" ; exit 1 ;;
  esac
done


echo "Subject: DVD-Rental-System V 1.0, Front-End-Part Testing"
dateTest=`date`
echo ""
echo "Begin testing at: $dateTest"
echo "..."

case $TEST in
 0) run_test "Admin-Test-Set" ;;
 1) run_test "Standard-Test-Set" ;;
 2) run_test "Admin-Test-Set"
    cd ..
    run_test "Standard-Test-Set";;
 *) echo "Internal error!"; exit 1 ;;
esac

echo "..."
echo "Subject: DVD-Rental-System V 1.0, Front-End-Part Testing"
dateTest=`date`
echo "End testing at: $dateTest"

