JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	cuidvd/DVDRentalSystem.java \
	sessiondvd/AdminSession.java \
	sessiondvd/iSession.java \
	sessiondvd/pSession.java \
	sessiondvd/SessionErrors.java \
	sessiondvd/SessionManager.java \
	sessiondvd/SessionType.java \
	sessiondvd/sSession.java \
	sessiondvd/StandardSession.java \
	sessiondvd/AdminSession.java \
	utilsdvd/GlobalFiles.java \
	utilsdvd/Tools.java \
	iodvd/CurrentDVD.java \
	iodvd/CurrentDVDReader.java \
	iodvd/CurrentDVDWriter.java \
	iodvd/DVDStatus.java \
	iodvd/DVDTransactionWriter.java \
	iodvd/iFileReader.java \
	iodvd/iFileWriter.java \
	iodvd/TransactionID.java \
	iodvd/DVDTransaction.java \

default: classes

classes: $(CLASSES:.java=.class)
	jar cvfm dvdrental.jar MANIFEST.MF cuidvd/*.class sessiondvd/*.class utilsdvd/*.class iodvd/*.class
	$(RM) cuidvd/*.class
	$(RM) sessiondvd/*.class
	$(RM) utilsdvd/*.class

