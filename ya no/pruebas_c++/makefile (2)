CC = g++
ARC1 = firewall
OBJ1 = $(ARC1).o 

NOMBRE = pg










_: 
	echo "win/lin"

#-------------------------------------------------

$(OBJ1).o:  $(ARC1).cpp 
	$(CC) $(LFLAGS) -c $(ARC1).cpp

#--------------------------------------------------


# LINUX
LFLAGS = -lpcap -I.
lin:  $(OBJ1) 
	$(CC) -o $(NOMBRE) $(OBJ1) $(LFLAGS)


# WINDOWS

PCAP_PATH = /lib

LIBS = -L ${PCAP_PATH} -lwpcap
CFLAGS = -g -O -mno-cygwin -I /include


WFLAGS = $(LIBS) -I.

#win:  $(OBJ1) 
#	$(CC) -o $(NOMBRE).exe $(OBJ1) $(WFLAGS)


win: ${OBJ1}
	${CC} ${CFLAGS} -o $(NOMBRE).exe ${OBJ1} ${LIBS}




# limpiar 

.PHONY: clean clean_$(ARC1) clean_$(ARC2) clean_$(ARC3)

clean:
	rm $(NOMBRE) \
	$(ARC1).o 

clean_$(ARC1): 
	rm $(ARC1) \
	$(OBJ1)

# ---------------------------------------- prueba


PCAP_PATH = ../../lib
CFLAGS = -g -O -mno-cygwin -I ../../include

LIBS = -L ${PCAP_PATH} -lwpcap

all: ${OBJ1}
	${CC} ${CFLAGS} -o pf.exe ${OBJ1} ${LIBS}
