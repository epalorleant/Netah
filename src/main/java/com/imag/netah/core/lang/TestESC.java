/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imag.netah.core.lang;


import java.io.FileInputStream;
import java.io.InputStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author epaln
 */
public class TestESC {

    public static void main(String[] args) throws Exception {
        String inputFile = "/Users/epaln/Desktop/esc_epxr.txt";
        if (args.length > 0) {
            inputFile = args[0];
        }
        InputStream is = System.in;
        if (inputFile != null) {
            is = new FileInputStream(inputFile);
        }
        ANTLRInputStream input = new ANTLRInputStream(is);
        ESCExprLexer lexer = new ESCExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ESCExprParser parser = new ESCExprParser(tokens);
        ParseTree tree = parser.expression();
        ParseTreeWalker walker = new ParseTreeWalker();
        ESCBuilder builder = new ESCBuilder();
        walker.walk(builder, tree);
        System.out.println(builder.getId2Operator().toString());
    }
}
