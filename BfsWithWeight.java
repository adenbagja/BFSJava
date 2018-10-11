import java.util.*;
import java.lang.*;
import java.io.*;

class BfsWithWeight
{
    public BfsWithWeight(int vertexCount) {
        this.vertexCount = vertexCount;
        listArray = new LinkedList[vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            listArray[i] = new LinkedList<>();
        }
    }

    static class GraphNode {
        public GraphNode(int tujuan, int jarak) {
            this.to = tujuan;
            this.jarak = jarak;
        }

        int to;
       int jarak;
    }

    public static final int INFINITY = Integer.MAX_VALUE;
    int vertexCount;
    LinkedList<GraphNode> listArray[];

    public static void main (String[] args) //throws java.lang.Exception
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukan Node yang dicari[1-8]: ");
        int tujuan = sc.nextInt();
        if (tujuan > 8 ){
            System.exit(0);
        }
        float startTime = System.nanoTime(); //awal perhitungan waktu program
        BfsWithWeight graph = new BfsWithWeight(9);
        graph.addNode(0, 1, 1); //awalNode, tujuan, jarak
        graph.addNode(0, 7, 3);
        graph.addNode(1, 7, 3);
        graph.addNode(1, 2, 4);
        graph.addNode(2, 3, 50);
        graph.addNode(2, 5, 3);
        graph.addNode(2, 8, 2);
        graph.addNode(3, 4, 50);
        graph.addNode(3, 5, 5);
        graph.addNode(4, 5, 3);
        graph.addNode(5, 6, 2);
        graph.addNode(6, 7, 4);
        graph.addNode(7, 8, 1);
        
        System.out.println("===============================");
        int awalNode = 0;
        
        graph.zeroOneBFS(awalNode, tujuan);
        float endTime   = System.nanoTime();
        float totalTime = (endTime - startTime) / 1000000000;
        System.out.print("Waktu Eksekusi: ");
        System.out.println(totalTime+ " detik"); //akhir perhitungan waktu program
        System.out.println("===============================");
    }

    public void addNode(int awalNode, int tujuan, int jarak) {
        listArray[awalNode].add(new GraphNode(tujuan, jarak));
        listArray[tujuan].add(new GraphNode(awalNode, jarak));
    }

    private void zeroOneBFS(int awalNode, int tujuan) {
        int[] dist = new int[vertexCount];
        int[] parent = new int[vertexCount];
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < dist.length; i++) {
            dist[i] = INFINITY;
            parent[i] = -1;
        }

        dist[awalNode] = 0;

        deque.addFirst(awalNode);

        while (!deque.isEmpty()) {
            Integer vertex = deque.removeFirst();
            Iterator<GraphNode> iterator = listArray[vertex].iterator();

            while (iterator.hasNext()) {
                GraphNode next = iterator.next();
                //here check for infinity because this may go out of bounds from interger value
                if (dist[next.to] == INFINITY) {
                    if (next.jarak == 0) {
                        deque.addFirst(next.to);
                    } else {
                        deque.addLast(next.to);
                    }
                    dist[next.to] = dist[vertex] + next.jarak;
                    parent[next.to] = vertex;
                } else if (dist[next.to] > dist[vertex] + next.jarak) {
                    dist[next.to] = dist[vertex] + next.jarak;
                    parent[next.to] = vertex;
                    if (next.jarak == 0) {
                        deque.addFirst(next.to);
                    } else {
                        deque.addLast(next.to);
                    }
                }
            }
        }

        
        System.out.println("Node yang dicari : " + tujuan);
        System.out.println("Jarak: " +dist[tujuan]);
        System.out.println("Rute: "+getPath(parent, tujuan)); 


    }

    private String getPath(int[] parent, int i) {
        if (i == -1) {
            return " ";
        }

        return getPath(parent, parent[i]) + " " + i;
    }

}