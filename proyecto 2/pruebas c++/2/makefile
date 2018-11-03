CC = g++
CFLAGS = -lpcap -I.
CF= -I.

ARC1 = lbb

OBJ1 = $(ARC1).o 



_: prog

#-------------------------------------------------

$(OBJ1).o:  $(ARC1).cpp 
	$(CC) $(CFLAGS) -c $(ARC1).cpp

#--------------------------------------------------


prog:  $(OBJ1) 
	$(CC) -o $(ARC1) $(OBJ1) $(CFLAGS)



.PHONY: clean clean_$(ARC1) clean_$(ARC2) clean_$(ARC3)

clean:
	rm $(ARC1) \
	$(ARC1).o 

clean_$(ARC1): 
	rm $(ARC1) \
	$(OBJ1)

