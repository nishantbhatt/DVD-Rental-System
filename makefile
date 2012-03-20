JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	cuidvd/BackEnd.java \
	cuidvd/FrontEnd.java \
	session/frontend/AdminSession.java \
	session/frontend/iSession.java \
	session/frontend/pSession.java \
	session/frontend/SessionErrors.java \
	session/frontend/SessionManager.java \
	session/frontend/SessionType.java \
	session/frontend/sSession.java \
	session/frontend/StandardSession.java \
	session/backend/BackEndEngine.java \
	session/backend/ConstraintFailedException.java \
	session/backend/FatalBackEndException.java \
	session/backend/FileType.java \
	session/backend/iBackEnd.java \
	utilsdvd/GlobalFiles.java \
	utilsdvd/Tools.java \
	iodvd/CurrentDVD.java \
	iodvd/CurrentDVDReader.java \
	iodvd/DVDStatus.java \
	iodvd/DVDTransaction.java \
	iodvd/DVDTransactionReader.java \
	iodvd/iFileReader.java \
	iodvd/MasterDVD.java \
	iodvd/MasterDVDReader.java \
	iodvd/TransactionID.java \
	iodvd/exception/DVDFormatException.java \
	iodvd/exception/TransactionFormatException.java \

default: classes

classes: $(CLASSES:.java=.class)
	jar cvfm dvdrental.jar cuidvd/*.class session/backend/*.class session/frontend/*.class utilsdvd/*.class iodvd/*.class iodvd/exception/*.class
	$(RM) cuidvd/*.class
	$(RM) session/backend/*.class
	$(RM) iodvd/*.class
	$(RM) iodvd/exception/*.class
	$(RM) utilsdvd/*.class

