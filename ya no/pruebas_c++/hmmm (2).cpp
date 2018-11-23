
#include <pcap.h>
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
void handler(char *, const struct pcap_pkthdr *, const u_char *);

int main(int argc, char **argv)
{
   int buffsize = 65535;
   int promisc = 1;
   int timeout = 1000;

   char pcap_err[PCAP_ERRBUF_SIZE];
   u_char buffer[255];
   char *dev;
   struct in_addr net, mask;
   pcap_t *pcap_nic;
   struct bpf_program filter;

   ethlen = sizeof(struct ether_header);
   iplen = sizeof(struct iphdr);
   tcplen = sizeof(struct tcphdr);

   printf("ethlen: %i\niplen: %i\ntcplen: %i\n",
          ethlen, iplen, tcplen);

   if (!(dev = pcap_lookupdev(pcap_err)))
   {
      perror(pcap_err);
      exit(-1);
   }

   printf("Dev: %s\n\n", dev);

   if ((pcap_nic = pcap_open_live(dev, buffsize, promisc, timeout, pcap_err)) == NULL)
   {
      perror(pcap_err);
      exit(-1);
   }

   if (pcap_lookupnet(dev, &net.s_addr, &mask.s_addr, pcap_err) == -1)
   {
      perror(pcap_err);
      exit(-1);
   }
   printf("net: %s\tmask: %s\n\n", inet_ntoa(net), inet_ntoa(mask));

   if (pcap_compile(pcap_nic, &filter, "", 0, net.s_addr) == -1)
   {
      perror(pcap_err);
      exit(-1);
   }

   if (pcap_setfilter(pcap_nic, &filter) == -1)
   {
      perror(pcap_err);
      exit(-1);
   }

   pcap_loop(pcap_nic, -1, (pcap_handler)handler, buffer);
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

   if (tcpheader->syn && tcpheader->ack)
   {
      source.s_addr = ipheader->saddr;
      dest.s_addr = ipheader->daddr;

      printf("From: %s \t%i\t", inet_ntoa(source), ntohs(tcpheader->source));
      printf("To: %s \t%i\n", inet_ntoa(dest), ntohs(tcpheader->dest));
      printf("\tLength: %i", ntohs(ipheader->tot_len));
      printf("\n");
      printf("Flags: ");
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

   return;
}
