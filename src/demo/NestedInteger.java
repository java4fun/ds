package demo;

import java.util.ArrayList;
import java.util.List;

class NestedInteger {
    private List<NestedInteger> list;
    private Integer integer;
    
    public NestedInteger(List<NestedInteger> list){
        this.list = list;
    }
    
    public void add(NestedInteger nestedInteger) {
        if(this.list != null){
            this.list.add(nestedInteger);
        } else {
            this.list = new ArrayList();
            this.list.add(nestedInteger);
        }
    }

    public void setInteger(int num) {
        this.integer = num;
    }

    public NestedInteger(Integer integer){
        this.integer = integer;
    }

    public NestedInteger() {
        this.list = new ArrayList();
    }

    public boolean isInteger() {
        return integer != null;
    }

    public Integer getInteger() {
        return integer;
    }

    public List<NestedInteger> getList() {
        return list;
    }
    
    public String printNestedInteger(NestedInteger nestedInteger, StringBuilder sb){
        if(nestedInteger.isInteger()) {
            sb.append(nestedInteger.integer);
            sb.append(",");
        }
        sb.append("[");
        for(NestedInteger ni : nestedInteger.list){
            if(ni.isInteger()) {
                sb.append(ni.integer);
                sb.append(",");
            }
            else {
                printNestedInteger(ni, sb);
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

