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
#    standard error to a file (such as result.txt) do the
#    following (the -b flag is test both Admin and Standard test)
#
#    bash script.bash -b  2>&1  | tee result.out
# 2) To check the tests that failed, search for "*** ERROR" keyword
#    in the output file.
# 3) Every test has a code associated with it, for example
#    
#    S-RETURN-04
#
#    This uniquely identifies a test. This means that test checks the
#    RETURN functionality in Standard session and a number 04 represents
#    a test number.
# 4) To perform test on certain functionalities, you can set argument
#    -f as regex matching that test. For example, you test RETURN, BUY and
#    LOGIN during ADMIN session, use following script:
#
#    bash script.bash -a -f "(return|buy|login)"
# 5) To output only failed test you can set -e flag.
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
 echo "       -f = To invoke tests on certain functionalities"
 echo "       -e = To output only failed tests"
 echo "EXAMPLES:"
 echo "All admin tests: "
 echo "       bash $CALLER -a"
 echo ""
 echo "All standard tests:"
 echo "       bash $CALLER -s"
 echo ""
 echo "All tests related to buy transaction: "
 echo "       bash $CALLER -f buy"
 echo ""
 echo "All admin tests related to buy and return transaction: "
 echo "       bash $CALLER -a -f \"(buy|return)\""
 echo ""
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
   echo ""
  fi
 fi
 #-------------------------------------------------------------------------------
 
 if [ "$NO_ERROR" -eq 0 ]; then
  echo "*** SUCCESS. No error(s) were found in execution of the test."
  echo ""
  echo "Input: "
  echo `cat ./$1/Input/$2.in`
  echo ""
  echo "Output: "
  echo `cat ./$1/Actual-Output/$2.out`
  echo ""
  echo "Transaction File: "
  if [ -e ./$1/Actual-Output/$2.tf ]; then
   echo `cat ./$1/Actual-Output/$2.tf`
  else
   echo "Test did not create any transaction file."
  fi
  echo ""
 fi
 return $NO_ERROR
}

run_test() {
 cd $1
 for d in *; do
  if [ -d ./$d ]; then
   if [[ $d =~ $REGEX ]]; then
    x=1;
    while [ -f ./$d/Input/$x.in ]; do
     TOTAL_TESTS=$(( $TOTAL_TESTS + 1))
     output=`Test $d $x`
     if [ "$?" -eq 1 ]; then
      FAILED_TESTS=$(( $FAILED_TESTS + 1))
      fail="yes"
     else
      fail="no"
     fi
     if [ "$fail" == "yes" -o "$ERROR" == "no" ]; then
      echo ""
      echo "------------------------------------"
      echo "Test Identity: ${1:0:1}-${d^^}-$x"
      echo "Test Set: $1"
      echo "Functionality: $d"
      echo "Test Number: $x"
      echo "------------------------------------"
      echo "$output"
      echo ""
     fi
     x=$(( $x + 1 ))
    done
   fi
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
AFLAG="no"
SFLAG="no"
REGEX=".*"
ERROR="no"
FAILED_TESTS=0
TOTAL_TESTS=0

while getopts ehasf: opt
 do
  case $opt in
   h) usage "HELP" ;;
   a) AFLAG="yes" ;;
   s) SFLAG="yes" ;;
   e) ERROR="yes" ;;
   f) REGEX="$OPTARG"
      if [ "$REGEX" == "" ]; then
       REGEX=".*"
      fi ;;
   ?) echo "Run 'bash $CALLER -h' to see all the available command line options."
      exit 1;;
  esac
done

echo "Subject: DVD-Rental-System V 1.0, Front-End-Part Testing"
dateTest=`date`
echo "Begin testing at: $dateTest"
echo "************************************************************"

if [ "$AFLAG" != "yes" -a "$SFLAG" != "yes" ]; then
 AFLAG="yes"
 SFLAG="yes"
fi

shopt -s nocasematch;

if [ "$AFLAG" == "yes" ]; then
 run_test "Admin-Test-Set"
 cd ..
fi

if [ "$SFLAG" = "yes" ]; then
 run_test "Standard-Test-Set"
 cd ..
fi

echo "************************************************************"
echo "Total tests:  $TOTAL_TESTS"
echo "Failed tests: $FAILED_TESTS"
dateTest=`date`
echo "End testing at: $dateTest"

