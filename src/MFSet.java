package algat.util;
//MFset with rank euristics and path compression

public class MFSet {
  int[] rep;
  int[] rank;

  public MFSet(int n) {
    rep = new int[n];
    rank = new int[n];

    for (int i = 0; i < n; i++) {
      rep[i] = i;
      rank[i] = 0;
    }
  }

  public int find(int x) {
    if (rep[x] != x) {
      rep[x] = find(rep[x]);
    }
    return rep[x];
  }

  public void merge(int x, int y) {
    int repX = find(x);
    int repY = find(y);

    if (repX != repY) {
      if (rank[repX] > rank[repY]) {
        rep[repY] = repX;
      } else if (rank[repX] < rank[repY]) {
        rep[repX] = repY;
      } else {
        rep[repX] = repY; //or rep[repY] = repX;
        rank[repY] += 1;
      }
    }
  }

}