#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
20
#include<pcap.h>
//includea libpcap
int main(int argc , char **argv )
{
   char* net;//direccion de red
char*mask;//mascara de subred
char*dev;//nombre del dispositivo de red
int ret;//codigo de retorno
char errbuf[PCAP_ERRBUF_SIZE];//buffer para mensajes de error
bpf
u
int32 netp
;
//
direcion de red en modo raw
30
bpf
u
int32 maskp
;
//
mascara de red en modo raw
struct
in
addr addr
;
if
((
dev
=
pcap
lookupdev
(
errbuf
))==
NULL
) //
conseguimos la primera interfaz libre
{
printf
(
"ERROR %s\n"
,
errbuf
);
exit
(
−
1);
}
printf
(
"Nombre del dispositivo: %s\n"
,
dev
); //
mostramos el nombre del dispositivo
if
((
ret
=
pcap
lookupnet
(
dev
,&
netp
,&
maskp
,
errbuf
))==
−
1) //
consultamos las direccion de red y las mascara
{
printf
(
"ERROR %s\n"
,
errbuf
);
exit
(
−
1);
}
40
addr
.
s
addr
=
netp
;
//
Traducimos la direccion de red a algo legible
if
((
net
=
inet
ntoa
(
addr
))==
NULL
)
{
perror
(
"inet_ntoa"
);
exit
(
−
1);
}
printf
(
"Direccion de Red: %s\n"
,
net
);
addr
.
s
addr
=
maskp
;
//
Idem para la mascara de subred
mask
=
inet
ntoa
(
addr
);
50
if
((
net
=
inet
ntoa
(
addr
))==
NULL
)
{
perror
(
"inet_ntoa"
);
exit
(
−
1);
}
printf
(
"Mascara de Red: %s\n"
,
mask
);
return
0;
}