package org.text_utils;

import org.text_utils.nodes.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TextRandomizer {

    private static final Map<String, Function> functions = new HashMap<String, Function>();
    static {
        functions.put("RND", new Function(){
            @Override
            public String getText() {
                int min = Integer.parseInt(args.get(0));
                int max = Integer.parseInt(args.get(1));
                Random rand = new Random();
                int randomNum = rand.nextInt((max - min) + 1) + min;
                return "" + randomNum;
            }
        });
        functions.put("NOW", new Function(){
            @Override
            public String getText() {
                DateFormat df = new SimpleDateFormat(args.get(0));
                Date today = Calendar.getInstance().getTime();
                return df.format(today);
            }
        });
        functions.put("UUID", new Function(){
            @Override
            public String getText() {
                return UUID.randomUUID().toString();
            }
        });
    }

    private Node tree = null;
    private String template = null;

    public TextRandomizer(String template, boolean parse) {
        this.template = template;

        if(parse == true)
            parse();
    }

    public String getText() throws Exception {
        if(tree != null){
            LinkedList<Pair> list = tree.getIndexes();
            StringBuilder str = new StringBuilder();
            for(Pair p: list){
                if(p != null && p.getText() != null){
                    str.append(p.getText());
                } else if(p == null || p.getStart() == p.getEnd()){
                    str.append(" ");
                } else {
                    str.append(template.substring(p.getStart(), p.getEnd()));
                }
            }

            return str.toString();
        } else {
            throw new Exception("Template not parsed yet");
        }
    }

    public void reset(){
        tree = null;
    }

    public void parse(){
        int i = 0;
        Node p = null;
        tree = new Node(null);
        Node current_node = new SeriesNode(tree);

        while(i < template.length()){
            switch(template.charAt(i)){
                case '\\':
                    current_node = current_node.concat(i+1);
                    i += 2;
                    continue;

                case '[':
                    Tuple2<Pair, Integer> tuple = getSeparator(i);
                    i = tuple.getY();
                    p = closestSeriesNode(current_node);
                    current_node = new MixingNode(p, tuple.getX());
                    current_node = new SeriesNode(current_node);
                    break;

                case '{':
                    p = closestSeriesNode(current_node);
                    current_node = new SynonymsNode(p);
                    current_node = new SeriesNode(current_node);
                    break;

                case '}':
                case ']':
                    p = closestSeriesNode(current_node);
                    current_node = p.getParent().getParent();
                    if(current_node == null){
                        // on the top of tree - 'str1 } {a|b}' or 'str2 ] [a|b]'
                        current_node = current_node.concat(i);
                    }
                    break;

                case '|':
                    p = closestSeriesNode(current_node);
                    if (p.getParent() instanceof MixingNode || p.getParent() instanceof SynonymsNode){
                        current_node = new SeriesNode(p.getParent());
                    } else {
                        //on the top of tree - 'bla1 | bla2'
                        current_node = current_node.concat(i);
                    }
                    break;

                case '$':
                    int start_ind = i;
                    Tuple2<String, Integer> tName = getFunctionName(i);
                    Tuple2<List<String>, Integer> tArgs = getFunctionArgs(tName.getY());
                    i = tArgs.getY();
                    Function func = functions.get(tName.getX());
                    if(func != null){
                        func.setArgs(tArgs.getX());
                        current_node = closestSeriesNode(current_node);
                        new FunctionNode(current_node, func);
                        continue;
                    } else {
                        i = start_ind;
                        current_node = current_node.concat(i);
                    }

                    break;

                default:
                    // getting StringNode here
                    current_node = current_node.concat(i);
            }

            i += 1;
        }
    }

    private Node closestSeriesNode(Node current_node) {
        if (current_node instanceof SeriesNode){
            return current_node;
        } else if (current_node instanceof StringNode){
            return current_node.getParent();
        }

        return null;
    }

    private Tuple2<Pair, Integer> getSeparator(int index) {
        Pair separator = new Pair(0, 0);
        if( (index+1) < template.length() && template.charAt(index+1) == '+'){
            index += 2;
            int start = index;
            while((index+1) < template.length() && template.charAt(index) != '+'){
                index += 1;
            }
            separator = new Pair(start, index);
        }

        return new Tuple2<Pair, Integer>(separator, index);
    }

    private Tuple2<String, Integer> getFunctionName(int index){
        index += 1;
        int start = index;
        while(Character.isLetterOrDigit(template.charAt(index)) || template.charAt(index) == '_'){
            index += 1;
        }

        String func_name = template.substring(start, index);
        return new Tuple2<String, Integer>(func_name, index);
    }

    private Tuple2<List<String>, Integer> getFunctionArgs(int index){
        List<String> args = null;
        if(template.charAt(index) == '('){
            index += 1;
            int start = index;
            while(template.charAt(index) != ')')
                index += 1;

            String strArgs = template.substring(start, index);
            index += 1;
            if(strArgs.trim().length() > 0){
                args = new ArrayList<String>(Arrays.asList(strArgs.split(",")));
            }
        }

        return new Tuple2<List<String>, Integer>(args, index);
    }

    public static void main(String[] args){
        System.out.println("=====================");
//        TextRandomizer rnd = new TextRandomizer("1 {aa|[bb|cc]} [a|b|c] 2", true);
        TextRandomizer rnd = new TextRandomizer("1 $NOW(MM/dd/yyyy HH:mm:ss) [+,+$RND(1,10)|$UUID] 2", true);
        try {
            System.out.println(rnd.getText());
            System.out.println(rnd.getText());
            System.out.println(rnd.getText());
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}