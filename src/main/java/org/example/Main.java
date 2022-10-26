package org.example;

import org.apache.commons.cli.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String... args) {
        Options options = makeOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("rectangles", options);
            exit(1);
        }
        String[] rectangle1Args = cmd.getOptionValues("rectangle1");
        String[] rectangle2Args = cmd.getOptionValues("rectangle2");
        Option[] options1 = cmd.getOptions();
        Point r1p1 = new Point(Double.parseDouble(rectangle1Args[0]), Double.parseDouble(rectangle1Args[1]));
        Point r1p2 = new Point(Double.parseDouble(rectangle1Args[2]), Double.parseDouble(rectangle1Args[3]));
        Point r2p1 = new Point(Double.parseDouble(rectangle2Args[0]), Double.parseDouble(rectangle2Args[1]));
        Point r2p2 = new Point(Double.parseDouble(rectangle2Args[2]), Double.parseDouble(rectangle2Args[3]));
        Rectangle r1 = new Rectangle(r1p1, r1p2);
        Rectangle r2 = new Rectangle(r2p1, r2p2);
        if (options1.length == 2) {
            System.out.println("Rectangle 1 points: " + r1);
            System.out.println("Rectangle 2 points: " + r2);
        }
        for (Option option : options1) {
            switch (option.getOpt()) {
                case "i" -> System.out.println("intersection points: " + r1.getIntersection(r2));
                case "c" -> System.out.println("containment: " + r1.contains(r2));
                case "a" -> System.out.println("adjacency: " + r1.isAdjacentTo(r2));
            }
        }
    }
    private static Options makeOptions() {
        Options options = new Options();
        Option r1Input = Option.builder("r1")
                .longOpt("rectangle1")
                .numberOfArgs(4)
                .argName("point1 x")
                .argName("point1 y")
                .argName("point2 x")
                .argName("point2 y")
                .desc("")
                .required()
                .build();
        options.addOption(r1Input);

        Option r2Input = Option.builder("r2")
                .longOpt("rectangle2")
                .numberOfArgs(4)
                .argName("point1 x")
                .argName("point1 y")
                .argName("point2 x")
                .argName("point2 y")
                .desc("")
                .required()
                .build();
        options.addOption(r2Input);

        Option iInput = Option.builder("i")
                .longOpt("intersection")
                .desc("")
                .build();
        options.addOption(iInput);

        Option nInput = Option.builder("c")
                .longOpt("containment")
                .desc("")
                .build();
        options.addOption(nInput);

        Option wInput = Option.builder("a")
                .longOpt("adjacency")
                .desc("")
                .build();
        options.addOption(wInput);
        return options;
    }
}