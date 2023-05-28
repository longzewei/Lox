package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        // > call-define-ast
        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary    : Token operator, Expr right",
                "Variable : Token name"));
        // > Statements and State stmt-ast

        defineAst(outputDir, "Stmt", Arrays.asList(
                "Expression : Expr expression",
                "Print      : Expr expression",
                "Var        : Token name, Expr initializer"
        ));

    }

    // > define-ast
    private static void defineAst(
            String outputDir, String baseName, List<String> types)
            throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        // > omit
        writer.println("//> Appendix II " + baseName.toLowerCase());
        // < omit
        writer.println("package com.craftinginterpreters.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        // > call-define-visitor
        defineVisitor(writer, baseName, types);

        // < call-define-visitor
        // > omit
        writer.println();
        writer.println("  // Nested " + baseName + " classes here...");
        // < omit
        // > nested-classes
        // The AST classes.
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim(); // [robust]
            defineType(writer, baseName, className, fields);
        }
        // < nested-classes
        // > base-accept-method

        // The base accept() method.
        writer.println();
        writer.println("  abstract <R> R accept(Visitor<R> visitor);");

        // < base-accept-method
        writer.println("}");
        // > omit
        writer.println("//< Appendix II " + baseName.toLowerCase());
        // < omit
        writer.close();
    }

    // < define-ast
    // > define-visitor
    private static void defineVisitor(
            PrintWriter writer, String baseName, List<String> types) {
        writer.println("  interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit" + typeName + baseName + "(" +
                    typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("  }");
    }

    // < define-visitor
    // > define-type
    private static void defineType(
            PrintWriter writer, String baseName,
            String className, String fieldList) {
        // > omit
        writer.println("//> " +
                baseName.toLowerCase() + "-" + className.toLowerCase());
        // < omit
        writer.println("  static class " + className + " extends " +
                baseName + " {");

        // > omit
        // Hack. Stmt.Class has such a long constructor that it overflows
        // the line length on the Appendix II page. Wrap it.
        if (fieldList.length() > 64) {
            fieldList = fieldList.replace(", ", ",\n          ");
        }

        // < omit
        // Constructor.
        writer.println("    " + className + "(" + fieldList + ") {");

        // > omit
        fieldList = fieldList.replace(",\n          ", ", ");
        // < omit
        // Store parameters in fields.
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println("      this." + name + " = " + name + ";");
        }

        writer.println("    }");
        // > accept-method

        // Visitor pattern.
        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visitor.visit" +
                className + baseName + "(this);");
        writer.println("    }");
        // < accept-method

        // Fields.
        writer.println();
        for (String field : fields) {
            writer.println("    final " + field + ";");
        }

        writer.println("  }");
        // > omit
        writer.println("//< " +
                baseName.toLowerCase() + "-" + className.toLowerCase());
        // < omit
    }

    // < define-type
    // > pastry-visitor
    interface PastryVisitor {
        void visitBeignet(Beignet beignet); // [overload]

        void visitCruller(Cruller cruller);
    }

    // < pastry-visitor
    // > pastries
    abstract class Pastry {
        // > pastry-accept
        abstract void accept(PastryVisitor visitor);
        // < pastry-accept
    }

    class Beignet extends Pastry {
        // > beignet-accept
        @Override
        void accept(PastryVisitor visitor) {
            visitor.visitBeignet(this);
        }
        // < beignet-accept
    }

    class Cruller extends Pastry {
        // > cruller-accept
        @Override
        void accept(PastryVisitor visitor) {
            visitor.visitCruller(this);
        }
        // < cruller-accept
    }
    // < pastries
}