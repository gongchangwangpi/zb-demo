package com.books.suanfa4.d1._3;

import com.bedpotato.alg4.Stack;

/**
 * 求表达式的值
 * 简单表达式，每两个操作数之间有括号来提示运算优先级
 *
 * Created by Administrator on 2017/6/28 0028.
 */
public class Evaluate {

    public static void main(String[] args) {

        String s = "(1-(2*(8/4)))";
        double cal = cal(s);

        System.out.println(cal);

    }

    public static double cal(String str) {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        if (str == null || "".equals(str.trim())) {
            throw new IllegalArgumentException("expression must not empty");
        }

        String[] arr = str.split("");
        for (String s: arr) {
            OpsEnum opsEnum = OpsEnum.get(s);
            if (opsEnum == OpsEnum.OP_RIGHT) {
                Double val = vals.pop();
                String op = ops.pop();
                OpsEnum opEnum = OpsEnum.get(op);
                opEnum.cal(val, vals);
            } else {
                opsEnum.op(ops, vals);
            }
        }

        return vals.pop();
    }

    static enum OpsEnum {

        OP_LEFT("("){
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_RIGHT(")") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                String op = ops.pop();
                Double v = vals.pop();
                OpsEnum opsEnum = get(op);
                opsEnum.cal(v, vals);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_ADD("+") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                ops.push(this.op);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
                Double pop = vals.pop();
                vals.push(val + pop);
            }
        },
        OP_SUB("-") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                ops.push(this.op);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
                Double pop = vals.pop();
                vals.push(pop - val);
            }
        },
        OP_MULTI("*") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                ops.push(this.op);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
                Double pop = vals.pop();
                pop = val * pop;
                vals.push(pop);
            }
        },
        OP_DIV("/") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                ops.push(this.op);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
                Double pop = vals.pop();
                vals.push(pop / val);
            }
        },
        OP_0("0") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(0.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_1("1") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(1.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_2("2") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(2.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_3("3") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(3.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_4("4") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(4.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_5("5") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(5.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_6("6") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(6.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_7("7") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(7.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_8("8") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(8.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        },
        OP_9("9") {
            @Override
            public void op(Stack<String> ops, Stack<Double> vals) {
                vals.push(9.0);
            }
            @Override
            public void cal(Double val, Stack<Double> vals) {
            }
        };

        String op;

        OpsEnum(String op) {
            this.op = op;
        }

        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        public static OpsEnum get(String op) {
            for (OpsEnum ops: OpsEnum.values()) {
                if (ops.getOp().equals(op)) {
                    return ops;
                }
            }
            return null;
        }

        /**
         * 抽象方法，由各实例去实现具体的计算规则
         *
         * @param ops
         * @param vals
         */
        public abstract void op(Stack<String> ops, Stack<Double> vals);

        public abstract void cal(Double val, Stack<Double> vals);
    }

    static enum ValueEnum {

    }

}
