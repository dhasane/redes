

#include <stdlib.h>
#include <stdio.h>

#include <pcap.h>

#include <stdio.h>
#include <string.h>

#include <iostream>

#include <stdio.h>
#include <stdlib.h>

#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <netinet/ether.h>
#include <netinet/ip.h>
#include <netinet/tcp.h>
#include <sys/socket.h>

char ethlen, iplen, tcplen;

void agregarPuertoAFiltro(char *filtro, int puerto);

void handler(char *usr, const struct pcap_pkthdr *header, const u_char *pkt);

pcap_t *handle;                // Session handle

int main(int argc, char *argv[])
{
   
   char *dev;                     // The device to sniff on
   char errbuf[PCAP_ERRBUF_SIZE]; // Error string
   struct bpf_program fp;         // The compiled filter

   bpf_u_int32 mask;          // netmask
   bpf_u_int32 net;           // IP
   struct pcap_pkthdr header; // The header that pcap gives us
   const u_char *packet;      // The actual packet
   u_char buffer[255];
   char filter_exp[] = ""; // filtro

   ethlen = sizeof(struct ether_header);
   iplen = sizeof(struct iphdr);
   tcplen = sizeof(struct tcphdr);

   std::cout << filter_exp << std::endl;

   //strcat(filter_exp , "port 80 or port 23"); // estos dos sirven
   //strcat(filter_exp , "port 80"); // estos dos sirven
   //strcat(filter_exp , "portrange 1-1000");

   // esto aun no lo entiendo bien
   //strcat(filter_exp , "icmp[icmptype] != icmp-echo and icmp[icmptype] != icmp-echoreply");

   //agregarPuertoAFiltro(filter_exp, 58218);
   //agregarPuertoAFiltro(filter_exp, 23);

   bool promiscuo = false;

   /* Define the device */
   dev = pcap_lookupdev(errbuf);
   if (dev == NULL)
   {
      fprintf(stderr, "Couldn't find default device: %s\n", errbuf);
      return (2);
   }
   /* Find the properties for the device */
   if (pcap_lookupnet(dev, &net, &mask, errbuf) == -1)
   {
      fprintf(stderr, "Couldn't get netmask for device %s: %s\n", dev, errbuf);
      net = 0;
      mask = 0;
   }

   /* Open the session*/
   handle = pcap_open_live(dev, BUFSIZ, promiscuo, 1000, errbuf);
   if (handle == NULL)
   {
      fprintf(stderr, "Couldn't open device %s: %s\n", dev, errbuf);
      return (2);
   }

   /* Compile and apply the filter */

   if (pcap_compile(handle, &fp, filter_exp, 0, net) == -1)
   {
      fprintf(stderr, "Couldn't parse filter %s: %s\n", filter_exp, pcap_geterr(handle));
      return (2);
   }

   if (pcap_setfilter(handle, &fp) == -1)
   {
      fprintf(stderr, "Couldn't install filter %s: %s\n", filter_exp, pcap_geterr(handle));
      return (2);
   }

   pcap_loop(handle, -1, (pcap_handler)handler, buffer);

   pcap_close(handle);
   return (0);
}

void agregarPuertoAFiltro(char *filtro, int puerto)
{
   char *pp = new char[20];
   strcpy(pp, "port ");
   sprintf(pp, "%s%d ", pp, puerto);
   strcat(filtro, pp);
}

void handler(char *usr, const struct pcap_pkthdr *header, const u_char *pkt)
{

   struct ether_header *ethheader;
   struct iphdr *ipheader;
   struct tcphdr *tcpheader;
   struct in_addr source, dest;

   ethheader = (struct ether_header *)pkt;
   ipheader = (struct iphdr *)(pkt + ethlen);
   tcpheader = (struct tcphdr *)(pkt + ethlen + iplen);

   //*
   //if (tcpheader->syn && tcpheader->ack)
   {
      
      source.s_addr = ipheader->saddr;
      dest.s_addr = ipheader->daddr;


      printf("From: %s \t", inet_ntoa(source));
      printf("To: %s \t ", inet_ntoa(dest));


      // esto cambia la ip :D----------------------------------

      inet_pton(AF_INET, "10.154.23.23", &(ipheader->daddr));

      // ------------------------------------------------------

      source.s_addr = ipheader->saddr;
      dest.s_addr = ipheader->daddr;


      printf("From: %s \t%i\t", inet_ntoa(source), ntohs(tcpheader->source));
      printf("To: %s \t%i  ", inet_ntoa(dest), ntohs(tcpheader->dest));
      printf("\tLength: %i  ", ntohs(ipheader->tot_len));
      //printf("\n");
      printf("\tFlags: ");
      if (tcpheader->urg)
         printf("URG");
      if (tcpheader->ack)
         printf("ACK ");
      if (tcpheader->psh)
         printf("PSH ");
      if (tcpheader->rst)
         printf("RST ");
      if (tcpheader->syn)
         printf("SYN ");
      if (tcpheader->fin)
         printf("FIN ");
      printf("\n\n");
   }

   //*
   if (pcap_sendpacket(handle,pkt,sizeof(pkt)) == 0)
   {
      pcap_perror(handle, 0);
      pcap_close(handle);
      //exit(1);
   }
   //*/

   // despues de recibir cada paquete, aqui seria reenviarlo a la direccion
   // correcta, siempre y cuando encaje con las caracteristicas esperadas
   // jej

}
