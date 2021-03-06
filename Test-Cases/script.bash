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
#    following
#
#    bash script.bash  2>&1  | tee result.out
#
# 2) To check the tests that failed, search for "*** ERROR" keyword
#    in the output file.
#    or
#    Use -e flag to print only those tests that failed:
#    
#    bash script.bash -e
#
# 3) Every test has a code associated with it, for example
#    
#    S-RETURN-04
#
#    This uniquely identifies a test. This means that test checks the
#    RETURN functionality in Standard session and a number 04 represents
#    a test number.
#
# 4) To check if a test identified by A-SELL-2 failed, you can use grep
#    in list of failed tests, for example:
#    
#    bash script.bash -ae | grep "A-SELL-2"
#
# 5) To perform test on certain functionalities, you can set argument
#    -f as regex matching that test. For example, you test RETURN, BUY and
#    LOGIN during ADMIN session, use following script:
#
#    bash script.bash -a -f "(return|buy|login)"
#
# 6) To output only failed test you can set -e flag.
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
 echo "`cat ./$1/Info/$2.info` [$3]"
 echo ""
 #------------------------------------------------------------------------------
 #-------------------------------------------------------------------------------
 echo "Running java command..."
 echo "java -jar ../dvdrental.jar ../dvds.txt ./$1/Actual-Output/$2.tf"
 java -jar ../dvdrental.jar ../dvds.txt ./$1/Actual-Output/$2.tf < ./$1/Input/$2.in > ./$1/Actual-Output/$2.out 2>&1
 echo ""
 #-------------------------------------------------------------------------------

 NO_ERROR=0
# Find the difference between actual and expected output files. If they are not matching an error is printed.
 #-------------------------------------------------------------------------------
 echo "Checking results..."
 result_output=$((diff ./$1/Actual-Output/$2.out ./$1/Output/$2.out) 2>&1)
 if [ -n "${result_output}" ]; then
  echo "*** ERROR found while comparing output for test [$3]. ***"
  echo "Difference between actual output and expected output is below:"
  echo "$result_output"
  NO_ERROR=1
  echo ""
 fi
#---------------------------------------------------------------------------------
# Find the difference between actual and expected transaction files. If they are not matching an error is printed.
#---------------------------------------------------------------------------------
 if [ -e ./$1/Output-TF/$2.tf ]; then
  result_transc=$((diff ./$1/Actual-Output/$2.tf ./$1/Output-TF/$2.tf) 2>&1)
  if [ -n "${result_transc}" ]; then
   echo "*** ERROR found while comparing transaction file for test [$3]. ***"
   echo "Difference between actual transaction file and expected transaction file is below:"
   echo "$result_transc"
   NO_ERROR=1
   echo ""
  fi
 fi
 #-------------------------------------------------------------------------------
 # Displays all the successful test cases.
 if [ "$NO_ERROR" -eq 0 ]; then
  echo "*** SUCCESS. No error(s) were found in execution of the test [$3]."
  echo ""
 fi
  
  echo "Input: "
  echo "`cat ./$1/Input/$2.in`"
  echo ""
  echo "Actual Output: "
  echo "`cat ./$1/Actual-Output/$2.out`"
  echo ""
  if [ "$NO_ERROR" -eq 0 ]; then
   echo "Expected Output: "
   echo "`cat ./$1/Output/$2.out`"
   echo ""
  fi
  echo "Transaction File: "
  if [ -e ./$1/Output-TF/$2.tf ]; then
   echo "`cat ./$1/Actual-Output/$2.tf`"
  else
   echo "Test should not create any transaction file."
  fi
 return $NO_ERROR
}

# Iterates through all the test cases within a folder that has been specified as an argument.
run_test() {
 cd $1
 for d in *; do
  if [ -d ./$d ]; then
   if [[ $d =~ $REGEX ]]; then
    x=1;
    while [ -f ./$d/Input/$x.in ]; do
     identity=${1:0:1}-${d^^}-$x
     TOTAL_TESTS=$(( $TOTAL_TESTS + 1))
     output=$((Test $d $x $identity) 2>&1)
     if [ "$?" -eq 1 ]; then
      FAILED_TESTS=$(( $FAILED_TESTS + 1))
      fail="yes"
     else
      fail="no"
     fi
     if [ "$fail" == "yes" -o "$ERROR" == "no" ]; then
      echo ""
      echo "------------------------------------"
      echo "Test Identity: $identity"
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
# admin mode only flag
AFLAG="no"
# standard mode only flag
SFLAG="no"
# regex to filter out the functionality
REGEX=".*"
# error flag
ERROR="no"

# total number of test cases failed
FAILED_TESTS=0
# total number of test cases tested with the script
TOTAL_TESTS=0
# Pasrisng all the command line arguments and setting the flags.
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

# if both -a and -s flags are false then set both of them to true
if [ "$AFLAG" != "yes" -a "$SFLAG" != "yes" ]; then
 AFLAG="yes"
 SFLAG="yes"
fi

# regex matching must be non case sensitive
shopt -s nocasematch;

# if -a flag is true then run tests for Admin-Test-Set
if [ "$AFLAG" == "yes" ]; then
 run_test "Admin-Test-Set"
 cd ..
fi

# if -s is true then run tests for Standard-Test-Set
if [ "$SFLAG" = "yes" ]; then
 run_test "Standard-Test-Set"
 cd ..
fi

# print all the results
echo "************************************************************"
echo "Total tests:  $TOTAL_TESTS"
echo "Failed tests: $FAILED_TESTS"
dateTest=`date`
echo "End testing at: $dateTest"