package numbers;

import java.util.*;

public class Main {
    public static void println(String string) {
        System.out.println(string);
    }

    public static boolean testProp(AmazingProperty ap, String type) {
        switch (type) {
            case "EVEN": return ap.isEven();
            case "ODD": return ap.isOdd();
            case "BUZZ": return ap.isBuzz() || ap.isBuzzEnd();
            case "DUCK": return ap.isDuck();
            case "PALINDROMIC": return ap.isPalindromic();
            case "GAPFUL": return ap.isGapful();
            case "SPY": return ap.isSpy();
            case "SUNNY": return ap.isSunny();
            case "SQUARE": return ap.isSquare();
            case "JUMPING": return ap.isJumping();
            case "HAPPY": return ap.isHappy();
            case "SAD": return ap.isSad();
            default: return false;
        }
    }

    private static boolean testRequests(List<String> requestTypes, List<String> minusTypes) {
        boolean out = true;
        List<String> wrongTypes = new ArrayList<>();
        List<String> types = new ArrayList<>(Arrays.asList("EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SUNNY", "SQUARE", "JUMPING", "HAPPY", "SAD"));
        String availProp = "Available properties: " + types.toString();
        for (String s : requestTypes) {
            if (!types.contains(s)) {
                out = out && false;
                wrongTypes.add(s);
            }
        }
        for (String s : minusTypes) {
            if (!types.contains(s.substring(1))) {
                out = out && false;
                wrongTypes.add(s);
            }
        }

        if (wrongTypes.size() > 1) {
            println("The properties " + wrongTypes.toString() + " are wrong.");
            println(availProp);
        } else if (wrongTypes.size() == 1) {
            println("The property " + wrongTypes.toString() + " is wrong.");
            println(availProp);
        }

        out = out && wrongDuet(requestTypes, "");
        out = out && wrongDuet(minusTypes, "-");

        if ((requestTypes.size() > 0) && (minusTypes.size() > 0)) {
            wrongTypes.clear();
            for (String s : requestTypes) {
                if (minusTypes.contains("-" + s)) {
                    out = out && false;
                    wrongTypes.add(s);
                    wrongTypes.add("-" + s);
                }
            }
        }
        if (wrongTypes.size() > 0) { printWrong(wrongTypes); }

        return out;
    }

    private static boolean wrongDuet(List<String> reqTypes, String s) {
        boolean out = true;
        List<String> wrongTypes = new ArrayList<>();
        if (reqTypes.size() > 1) {
            List <String> warningTypes = new ArrayList<>(Arrays.asList(s+"EVEN", s+"DUCK", s+"SUNNY", s+"HAPPY"));
            for (String w : warningTypes) {
                if (reqTypes.contains(w)) {
                    if (w.equals(s+"EVEN") && reqTypes.contains(s+"ODD")) {
                        out = false;
                        wrongTypes.add(w); wrongTypes.add(s+"ODD");
                    } else if (w.equals(s+"DUCK") && reqTypes.contains(s+"SPY")) {
                        out = false;
                        wrongTypes.add(w); wrongTypes.add(s+"SPY");
                    } else if (w.equals(s+"SUNNY") && reqTypes.contains(s+"SQUARE")) {
                        out = false;
                        wrongTypes.add(w); wrongTypes.add(s+"SQUARE");
                    } else if (w.equals(s+"HAPPY") && reqTypes.contains(s+"SAD")) {
                        out = false;
                        wrongTypes.add(w); wrongTypes.add(s+"SAD");
                    }
                }
            }
        }
        if (wrongTypes.size() > 0) { printWrong(wrongTypes); }
        return out;
    }

    private static void printWrong(List<String> wrongTypes) {
        println("The request contains mutually exclusive properties: " + wrongTypes.toString());
        println("There are no numbers with these properties.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        println("Welcome to Amazing Numbers!\n");
        println("Supported requests:");
        println("- enter a natural number to know its properties;");
        println("- enter two natural numbers to obtain the properties of the list:");
        println("\t* the first parameter represents a starting number;");
        println("\t* the second parameter shows how many consecutive numbers are to be processed;");
        println("- two natural numbers and a properties to search for;");
        println("- a property preceded by minus must not be present in numbers;");
        println("- separate the parameters with one space;");
        println("- enter 0 to exit.\n");

        String request = "";
        while (!request.equals("0")) {
            println("Enter a request: ");
            request = scanner.nextLine().toUpperCase(Locale.ROOT);
            if (request.equals("0")) continue;
            else {
                try {
                    String[] arr = request.split(" ");
                    if (arr.length == 1) {
                        AmazingProperty ap = new AmazingProperty(request);
                        println(ap.toString());
                    } else {
                        long first = Long.parseLong(arr[0]);
                        if (first < 0) {
                            println("The first parameter should be a natural number or zero.");
                            continue;
                        }
                        int second = Integer.parseInt(arr[1]);
                        if (second < 0) {
                            println("The second parameter should be a natural number.");
                            continue;
                        }
                        if (arr.length == 2) {
                            for (int i = 0; i < second; i++) {
                                String strRequest = "" + first;
                                AmazingProperty ap = new AmazingProperty(strRequest);
                                println(ap.quickStr());
                                first++;
                            }
                        } else {
                            List <String> requestTypes = new ArrayList<>();
                            List <String> minusTypes = new ArrayList<>();
                            for (int i=2; i < arr.length; i++) {
                                if (arr[i].contains("-") && !minusTypes.contains(arr[i])) minusTypes.add(arr[i]);
                                else if (!requestTypes.contains(arr[i])) requestTypes.add(arr[i]);
                            }
//                          requestTypes.addAll(Arrays.asList(arr).subList(2, arr.length));
                            if (!testRequests(requestTypes, minusTypes)) continue;

                            int step = 0;
                            while (step < second) {
                                String strRequest = "" + first;
                                boolean testType = true;
                                AmazingProperty ap = new AmazingProperty(strRequest);
                                for (String s : requestTypes) {
                                    testType = testProp(ap, s);
                                    if (!testType) break;
                                }

                                if (testType) {
                                    for (String s : minusTypes) {
                                        testType = !testProp(ap, s.substring(1));
                                        if (!testType) break;
                                    }
                                }

                                if (testType) {
                                    step++;
                                    println(ap.quickStr());
                                }
                                first++;
                            }
                        }
                    }
                } catch (Exception e) {
                    println("The first parameter should be a natural number or zero.");
                }
            }
        }
        println("Goodbye!");
    }
}