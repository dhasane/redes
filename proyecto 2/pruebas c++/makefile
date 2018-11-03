CC = g++
CFLAGS = -lpcap -I.
CF= -I.

ARC1 = intento

OBJ1 = $(ARC1).o 

NOMBRE = pg

_: prog

#-------------------------------------------------

$(OBJ1).o:  $(ARC1).cpp 
	$(CC) $(CFLAGS) -c $(ARC1).cpp

#--------------------------------------------------


prog:  $(OBJ1) 
	$(CC) -o $(NOMBRE) $(OBJ1) $(CFLAGS)



.PHONY: clean clean_$(ARC1) clean_$(ARC2) clean_$(ARC3)

clean:
	rm $(NOMBRE) \
	$(ARC1).o 

clean_$(ARC1): 
	rm $(ARC1) \
	$(OBJ1)

