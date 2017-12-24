import java.util.*;

public class ConwaySimulation {
  private static Random r;
  private static Grid conway;
  private static int xsize = 56;
  private static int ysize = 150;
  private static int randoms = 1600;
  private static int timecap = 1000;//milliseconds
  private static int cap = 64;
  public static void main(String[] args) {
    r = new Random();
    xsize = (args.length >= 1) ? Integer.parseInt(args[0]) : xsize;
    conway = new Grid(xsize,ysize);
    randoms = (args.length >= 2) ? Integer.parseInt(args[1])  : randoms;
    cap = (args.length >= 3) ? Integer.parseInt(args[2]) : cap;
    timecap = (args.length >= 4) ? Integer.parseInt(args[3]) : timecap;

    randomAdditions(randoms);
    long time = System.currentTimeMillis();
    for(int i = 0; i < cap; ) {
      if(System.currentTimeMillis() - time >= timecap) {
        time = System.currentTimeMillis();
        conway.nextGen();
        i++;
        System.out.print(conway);
      }
    }
  }
  public static void randomAdditions(int spots) {//Not guaranteed to put #spots into grid, just tries
    Integer[] a = conway.getDimensions();
    int xmax = a[0];
    int ymax = a[1];
    while(spots > 0) {
      conway.modify(r.nextInt(xmax),r.nextInt(ymax), true);
      spots--;
    }
  }
}
