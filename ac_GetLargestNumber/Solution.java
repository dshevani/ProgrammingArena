public class Solution {
    public String largestNumber(int[] num) {
    Integer[] array = new Integer[num.length];
    for (int i=0; i<num.length; i++) {
      array[i] = new Integer(num[i]);
    }
    Comparator<Integer> lexCompare = new Comparator<Integer>(){
      public int compare( Integer x, Integer y ) {
        return (y+""+x).compareTo(x+""+y);
      }
    };
    Arrays.sort(array,lexCompare);
    StringBuffer sb = new StringBuffer();
    for (Integer i : array) {
      sb.append(i);
    }
    if (sb.toString().matches("0*")) {
      return "0";
    }
    return sb.toString(); 
    }
}
